/*
 * Levide - An educational foreign language learning game mobile application
 * Copyright (C) 2016  K.S. Dieudonné
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.uwf.ksd7.levide_1.games.sfib;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.uwf.ksd7.levide_1.Consts;
import edu.uwf.ksd7.levide_1.R;

/**
 * ActivityGame is responsible for presenting the View objects necessary
 * for game play and taking the appropriate action based on user input.
 * Work is contained in this class and via methods called in GameEngine.
 * The attempt to make this Activity more readable by offloading game
 * logic exclusively to GameEngine, but in some cases it is more
 * appropriate to perform some game logic here, especially when
 * directly updating a View element.
 */
public class ActivityGame extends AppCompatActivity implements AsyncResponse {
    // For debug
    //private static final String TAG = "ActivityGame";
    // Used by OS to distinguish the results that are passed back via Intent
    private static final int GAME_OPTIONS_REQUEST_CODE = 123;
    // String to indicate an error with JSON results from remote DB
    private static final String JSON_MSG_ERR = "Remote DB JSON reply msg error";
    // There need be one and only one model and game engine, thus static.
    private static GameEngine mGameEngine;
    private static ModelGame mModel;
    // used to indicate that game content should be loaded after game content has arrived
    // from the DB (but not before that event). processFinish is called when this has occurred.
    private boolean mFirstLoad = true;
    private boolean mStatsEnabled = false; // if login as guest, stats are disabled
    private boolean mNetworkDown = false;  // if no communication, load default game content
    final Context mContext = this; // Needed by Async task to know details about the caller
    private String mName; // player name passed via Intent
    private String mEmail; // player email passed via Intent
    private String mGameLang; // SFIB game language passed via Intent

    /**
     * onCreate is called as the first method in the Activity life cycle
     * It places the view objects on the screen and initializes game state.
     * @param savedInstanceState A bundle maintained by the OS which includes
     *                           state of View objects.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sfib_game);

        // If this is the first time making this screen
        // versus when it is re-made due to rotation or back action
        if(null==savedInstanceState) {
            // Instantiate a model for the game
            if(null==mModel) mModel = new ModelGame();

            // Get the intent extras
            Intent intent = getIntent();
            mGameLang = intent.getStringExtra(Consts.GAME_LANG);
            mEmail = intent.getStringExtra(Consts.EMAIL);
            mName = intent.getStringExtra(Consts.NAME);
            //Log.d(TAG, "lang = " + mGameLang + " email = " + mEmail + " name = " + mName);

            // Instantiate a game engine for the game
            if(null==mGameEngine) mGameEngine = new GameEngine();

            // indicates that after DB content has been downloaded
            // the game needs to load an initial sentence to start the game
            mFirstLoad = true;

            // Load sentence content before starting the game
            // Accomplish spawning of async task to fetch sentence data from DB,
            // storing data into member variable upon completion
            fetchSentBlock();

            // If email is not the guest email string, user is logged in, fetch the stats
            if(!mEmail.equals(Consts.GUEST_EMAIL)) {
                mStatsEnabled = true;
                if(!mNetworkDown) {
                    // Start a task to run a query to determine if that player
                    // is represented in the DB, and if so get all the stats
                    FetchFromDBTask statTask =
                            new FetchFromDBTask(ActivityGame.this, ConstsSFIB.FETCH_STATS_URL);
                    statTask.delegate = this;
                    statTask.execute(ConstsSFIB.REQ_STATS_TASK, mEmail, mGameLang, mName);
                }
            } else {
                mStatsEnabled = false;
            }
        } else {
            //Log.d(TAG, "onCreate called with non-null savedInstanceState (rotation made)");
            // Restore content to screen in current state
            updateSentView();
            updateScoreView();
            updateHintView();
        }

        ///////////////////////////////////////////////////////////////////////
        // TOGGLE
        ///////////////////////////////////////////////////////////////////////
        Button buttonToggleSoftKeyboard;
        buttonToggleSoftKeyboard = (Button) findViewById(R.id.game_sfib_toggle);
        buttonToggleSoftKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ans = (EditText) findViewById(R.id.game_sfib_ans);
                ans.requestFocus();
                // Toggle the soft keyboard to the on or off state
                // Android OS does not provide a consistent method of determining if
                // soft keyboard is active/inactive, probably due to HW variability,
                // so give the user control of this in the App
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
            }
        });

        ///////////////////////////////////////////////////////////////////////
        // GAME OPTIONS
        ///////////////////////////////////////////////////////////////////////
        Button buttonGameOptions;
        buttonGameOptions = (Button) findViewById(R.id.game_sfib_options_button);
        buttonGameOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Activity for the Game Options Menu, placing relevant data
                // in an Intent message
                Intent startIntent = new Intent(getApplicationContext(), ActivityGameOptions.class);
                // Place the extras into the intent
                mGameEngine.putGameOptExtras(startIntent, mNetworkDown, mStatsEnabled, mName,
                        mGameLang);
                // Start the activity for results which will accomplish that results
                // are sent back from activities downstream of this one being spawned
                // (onActivityResult callback will be fired with the results)
                startActivityForResult(startIntent, GAME_OPTIONS_REQUEST_CODE);
            }
        });

        ///////////////////////////////////////////////////////////////////////
        // SKIP
        ///////////////////////////////////////////////////////////////////////
        Button buttonSkip;
        buttonSkip = (Button) findViewById(R.id.game_sfib_skip_button);
        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Present confirmation dialog, i.e. are you sure?
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle("Are you sure?");
                // Setup alert listener, etc.
                alert.setMessage("Click yes to skip")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Process Skip
                            mGameEngine.processSkip(mStatsEnabled);
                            // Advance to the next sentence, load a new sentence block if necessary
                            if(mGameEngine.advanceSent()) fetchSentBlock();
                            // Set Game State
                            resetGameState();
                            // load new sentence on screen
                            updateSentView();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // do nothing
                            dialog.cancel();
                        }
                    });
                // Activate the alert to the screen
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });

        ///////////////////////////////////////////////////////////////////////
        // HINT
        ///////////////////////////////////////////////////////////////////////
        Button buttonHint;
        buttonHint = (Button) findViewById(R.id.game_sfib_hint_button);
        buttonHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Process hint:
                // If all letters have not been revealed
                if(mModel.getHintIdx() < (mModel.getBlankWord().length()+1)) {
                    // update hint index
                    mModel.setHintIdx(mModel.getHintIdx() + 1);
                }
                // Update the hint to screen
                updateHintView();
            }
        });

        ///////////////////////////////////////////////////////////////////////
        // SUBMIT
        ///////////////////////////////////////////////////////////////////////
        Button buttonSubmit;
        buttonSubmit = (Button) findViewById(R.id.game_sfib_submit_button);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ans = (EditText) findViewById(R.id.game_sfib_ans); // Get answer view
                String userAns = ans.getText().toString(); // Extract user answer from view
                String msg; // Holds toast message
                // Process Submit (determine if answer correct or incorrect)
                int points = mGameEngine.processSubmit(userAns,
                        mModel.getBlankWord(), mModel.getHintIdx(), mStatsEnabled);
                final int WRONG_ANS = -1;
                if(points!=WRONG_ANS) {
                    // update points in model
                    mModel.setPoints(points);
                    //Log.d(TAG, "points = " + Integer.toString(mModel.getPoints()));
                    // update total score in model
                    mModel.setScore(mModel.getScore() + points);
                    // Set msg for "correct" toast
                    msg = "Correct! " + Integer.toString(points) +
                            " points awarded.";
                    //Log.d(TAG, "points = " + Integer.toString(points));
                    // advance the sentence, load new sentence block if necessary
                    if(mGameEngine.advanceSent()) fetchSentBlock();
                    // Set Game State
                    resetGameState();
                    // Update the score
                    updateScoreView();
                    // load new sentence
                    updateSentView();
                    // Push stats to DB
                    if(mStatsEnabled) postStats();
                } else {
                    // Give "incorrect" toast
                    msg = "Incorrect. Sorry.";
                    // Clear answer
                    ans.setText("");
                }
                // Put toast with appropriate msg to screen in a visible location
                putToast(msg);
             }
        });
    } // end onCreate

    /**
     * Called from the FetchFromDBTask (AsyncTask) onPostExecute method with results of
     * remote DB access.  This is the callback function, so it can't return an error to the caller.
     * The data is processed and stored in the model.
     * @param jsonResult The JSON string array in a specified format that needs
     *                    to be parsed and fields extracted to the model.
     */
    public void processFinish(String jsonResult) {
        //Log.d(TAG, "processFinish called");
        // If received no output from the asynchronous task called to connect to game DB
        if (null==jsonResult) {
            //Log.d(TAG, "processFinish empty results");
            // Set boolean to indicate network activity has problems
            mNetworkDown = true;
            // Indicate connectivity problems to the user
            String msg = "Trouble loading game content over network\nLoading default game " +
                    "content";
            putToast(msg);
            // load the default game content
            mGameEngine.loadDefaultContent(mGameLang);
            // First time through, need to update View objects and display game content
            if (mFirstLoad) processFirstLoad();
        }
        else { // Parse the JSON results
            mNetworkDown = false; // network is up
            String type = mGameEngine.checkJSONResponse(jsonResult);
            switch(type) {
                case ConstsSFIB.RESP_ADD_SENT:
                case ConstsSFIB.RESP_FLAG_SENT:
                case ConstsSFIB.RESP_POST_STATS:
                    break;

                case ConstsSFIB.RESP_GET_SENT:
                    if(!mGameEngine.processJSONSent(jsonResult, mGameEngine.getSentList())) {
                        // First time through, need to update View objects and display game content
                        if (mFirstLoad) processFirstLoad();
                    } else putToast(JSON_MSG_ERR + "\n" + type);
                    break;

                case ConstsSFIB.RESP_GET_STATS:
                    if(mGameEngine.processJSONStats(jsonResult, mGameEngine.getSFIBStats()))
                        putToast(JSON_MSG_ERR + "\n" + type);
                    break;

                case ConstsSFIB.RESP_ERROR:
                default:
                    putToast(JSON_MSG_ERR + "\n" + type);
                    break;
            }
        }
    }

    /**
     * This is a callback that gets called by the intent spawned with startActivityForResult
     * when the results have been obtained.
     * @param requestCode A code specified when starting the activity for results to
     *                    be able to distinguish the entity that is returning results.
     * @param resultCode The result of the work, i.e. RESULT_OK or error code.
     * @param data The intent msg with data Extras.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(null==data) return; // intercept back button from game options

        if(requestCode == GAME_OPTIONS_REQUEST_CODE && data.hasExtra(ConstsSFIB.GAME_OPTION)) {
            // Get the particular name of the name/value pair for this game option
            String name = data.getStringExtra(ConstsSFIB.GAME_OPTION);
            // Switch on the name of the name/value pair
            switch (name) {
                case ConstsSFIB.DIFFICULTY:
                    // Extract the value using the name
                    int difficulty = Integer.parseInt(data.getStringExtra(ConstsSFIB.DIFFICULTY));
                    //Log.d(TAG, "difficulty set to " + difficulty);
                    mGameEngine.setDifficulty(difficulty);
                    break;

                case ConstsSFIB.FLAG_SENT:
                    // Extract the value using the name
                    String result = data.getStringExtra(ConstsSFIB.FLAG_SENT);
                    if (result.equals(ConstsSFIB.FLAG_TRUE)) {
                        PostToDBTask postFlagSentTask =
                                new PostToDBTask(ConstsSFIB.POST_SENT_FLAG_URL);
                        postFlagSentTask.delegate = this;
                        postFlagSentTask.execute(
                                ConstsSFIB.POST_FLAG_SENT_TASK,
                            mGameEngine.getKey(),
                            mGameLang);
                    }
                    break;

                case ConstsSFIB.ADD_SENT:
                    // Extract the value using the name
                    String engSent = data.getStringExtra(ConstsSFIB.ADD_ENGLISH);
                    String forSent = data.getStringExtra(ConstsSFIB.ADD_FOREIGN);

                    PostToDBTask postAddSentTask =
                            new PostToDBTask(ConstsSFIB.POST_NEW_SENT_URL);
                    postAddSentTask.delegate = this;
                    postAddSentTask.execute(ConstsSFIB.POST_ADD_SENT_TASK, engSent, forSent,
                            mGameLang);

                    // reset the add sentence count, player must earn it again
                    mGameEngine.setAddCnt(0);
                    break;

                default:
                    throw new IllegalArgumentException();
            }
        }
    } // end onActivityResult

    // Updates the View elements associated with Hints
    // (English sentence and letters in the answer EditText)
    private void updateHintView() {
        int idx = mModel.getHintIdx();
        if(idx > 0) {
            TextView textView = (TextView) findViewById(R.id.game_sfib_eng_sent);
            EditText ans = (EditText) findViewById(R.id.game_sfib_ans);

            // Reveal the English sentence
            textView.setText(mModel.getEnglishSent());

            //Log.d(TAG, "idx-1 = " + Integer.toString(idx-1));
            //Reveal hint letter
            ans.setText(mModel.getBlankWord().substring(0, idx - 1));
            ans.setSelection(idx - 1); // place the cursor at the end of hint
        }
    }

    // Updates the score View object
    private void updateScoreView () {
        TextView textView = (TextView) findViewById(R.id.game_sfib_score);
        String score = getString(R.string.game_sfib_score, mModel.getScore());
        textView.setText(score);
    }

    // Acquires a new sentence and updates the View objects for the
    // foreign sentence, English sentence, and answer.
    private void updateSentView() {
        // Load foreign sentence
        TextView t1 = (TextView) findViewById(R.id.game_sfib_for_sent);
        t1.setText(mModel.getBlankedSent());

        // Clear English sentence
        TextView t2= (TextView) findViewById(R.id.game_sfib_eng_sent);
        t2.setText("");

        // Clear answer
        TextView t3 = (TextView) findViewById(R.id.game_sfib_ans);
        t3.setText("");
    }

    // Spawns a new asynchronous task to post player statistics and passes
    // relevant data via execute method.
    // Placed in ActivityGame because the creation of the task here
    // also involves the ProcessFinish when task is complete.
    private void postStats() {
        PostToDBTask postStatsTask = new PostToDBTask(ConstsSFIB.POST_STATS_URL);
        postStatsTask.delegate = this;
        postStatsTask.execute(
                ConstsSFIB.POST_STATS_TASK,
                mEmail,
                mGameLang,
                mGameEngine.getSigmaDiff(),
                mGameEngine.getSigmaGuess(),
                mGameEngine.getSigmaHint(),
                mGameEngine.getSigmaSkip(),
                mGameEngine.getSubmitCnt(),
                mGameEngine.getCumScore(),
                mGameEngine.getAvgDiff(),
                mGameEngine.getAvgGuess(),
                mGameEngine.getAvgSkip(),
                mGameEngine.getAvgHint(),
                mGameEngine.getAddCnt());
    }

    // Called whenever it is necessary to start a new asynchronous
    // task to fetch sentence content from the game DB.  This occurs
    // at startup and after player has exhausted the loaded content.
    // Placed in ActivityGame because the creation of the task here
    // also involves the ProcessFinish when task is complete.
    private void fetchSentBlock() {
        FetchFromDBTask sentTask =
                new FetchFromDBTask(ActivityGame.this, ConstsSFIB.FETCH_SENT_URL);
        sentTask.delegate = this;
        String sentBlockCnt = mGameEngine.getSentBlockCnt();
        sentTask.execute(ConstsSFIB.REQ_SENT_TASK, mGameLang, sentBlockCnt); // start the task
    }

    // Initializes the game state to ready for processing a new answer
    // submitted by the player.  Placed in ActivityGame because state
    // parameters needing to be reset are part of both ModelGame
    // and GameState.
    private void resetGameState() {
        mModel.setPoints(0); // clear for next use
        // set the hint index back to zero for a new word
        mModel.setHintIdx(0);
        // break up the fetched new sentence into individual words
        //Log.d(TAG, mGameEngine.getForSent());
        String[] words = mGameEngine.getForSent().split(" ");
        // determine the word in the sentence to blank
        int wordNum = UtilsSFIB.determineWordToBlank(words, mGameEngine.getDifficulty(),
                mGameEngine.getWordLenHard(), mGameEngine.getWordLenEasy() );
        // store word to blank number in model (the correct answer)
        mModel.setBlankWord(words[wordNum - 1]);
        // store sentence with word blanked out
        String addBlank = UtilsSFIB.blankWordInSent(words, wordNum);
        mModel.setBlankedSent(addBlank);
        // set the English sentence
        mModel.setEnglishSent(mGameEngine.getEngSent());
    }

    // Helper method for code called more than once
    // Initializes the View objects when the game is first loaded
    // after content has been received from the remote DB
    private void processFirstLoad() {
        // Set game state
        resetGameState();
        //Load content to screen
        updateSentView();
        updateScoreView();
        mFirstLoad = false;
    }

    // Helper method for code called more than once
    // Places toast on screen with message passed.
    private void putToast(String msg) {
        // Put toast with appropriate msg to screen in a visible location
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }
}
