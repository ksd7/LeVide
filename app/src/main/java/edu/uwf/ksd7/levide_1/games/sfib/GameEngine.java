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

import android.content.Intent;
//import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import edu.uwf.ksd7.levide_1.Consts;

/**
 * GameEngine handles the logic necessary to run the SFIB game
 * which helps make the ActivityGame code more readable.  However, where it
 * makes more sense to simply perform some logic in ActivityGame, then
 * it is kept there.
 */
public class GameEngine {
    // For debug
    //private static final String TAG = "GameEngine";

    private static GameState gs; // state variables needed to run the game
    private DefaultGameContent mDefaultContent; // when network down, default game content

    /**
     * Constructor
     */
    public GameEngine() {
        gs = new GameState();
        mDefaultContent = new DefaultGameContent();
    }

    /**
     * In the case of network failure, default game content
     * is loaded via this method.
     */
    public void loadDefaultContent(String lang) {
        mDefaultContent.setContent(lang, GameState.getSentList());
    }

    /**
     * The work required to collect and compute game statistics.
     * Parameters that are part of ModelGame are passed, parameters
     * that are part of GameState are accessed via member variables.
     * Work is performed in the GameState to facilitate testing.
     * @param points points awarded for current correct answer
     * @param numHints number of hints taken for current answer submittal
     */
    public void processStats(int points, int numHints) {
    	// Collect Stats
    	gs.updateStats(points, numHints);
    	// Compute Stats
    	gs.computeStats();
    }

    /**
     * Advances the current index in the sentence list and checks to ensure
     * sufficient sentences are available in the list.
     * @return returns true if it is necessary to load more game content, i.e.
     * the current content is almost exhausted
     * @throws IllegalArgumentException
     */
    public boolean advanceSent()
        throws IllegalArgumentException {
        boolean loadMoreSent = false;
        if(!GameState.getSentList().isEmpty()) {
            int idx = GameState.getSentList().size() - 1;
            GameState.getSentList().remove(idx);

            // Determine if sentences are running out and need to fetch more from the DB
            if(idx <= gs.getSentBlockThresh()) {
                loadMoreSent = true;
            }

        } else {
            //Log.d(TAG, "advanceSent empty results");
            throw new IllegalArgumentException("Empty sentence list");
        }
        return loadMoreSent;
    }

    /**
     * Parses a string assumed to contain JSON formatted data with
     * a status field as the 0th element.
     * @param jsonStr JSON response sent from remote DB access
     * @return String indicating the particular JSON response mode or error.
     */
    public String checkJSONResponse(String jsonStr) {
        String mode;
        try {
            // initialize json object
            JSONObject jsonObject = new JSONObject(jsonStr);
            if (jsonObject.has(ConstsSFIB.RESP_ADD_SENT)) {
                mode = ConstsSFIB.RESP_ADD_SENT;
            } else if (jsonObject.has(ConstsSFIB.RESP_FLAG_SENT)) {
                mode = ConstsSFIB.RESP_FLAG_SENT;
            } else if (jsonObject.has(ConstsSFIB.RESP_POST_STATS)) {
                mode = ConstsSFIB.RESP_POST_STATS;
            } else if (jsonObject.has(ConstsSFIB.RESP_GET_SENT)) {
                mode = ConstsSFIB.RESP_GET_SENT;
            } else if (jsonObject.has(ConstsSFIB.RESP_GET_STATS)) {
                mode = ConstsSFIB.RESP_GET_STATS;
            } else {
                mode = ConstsSFIB.RESP_ERROR;
            }

            if(!mode.equals(ConstsSFIB.RESP_ERROR)) {
                JSONArray jsonArray = jsonObject.getJSONArray(mode);
                JSONObject JO = jsonArray.getJSONObject(0);
                String status = JO.getString("status");
                String msg = JO.getString("msg");

                // test for error status
                if (!status.equals("success")) {
                    mode = ConstsSFIB.RESP_ERROR;
                    //Log.d(TAG, "error msg = " + msg);
                }
            }
        } catch (JSONException e) {
            mode = ConstsSFIB.RESP_ERROR;
        }
        return mode;
    }

    /**
     * Helper method to parse the results from fetching game content (sentences).
     * Adds these to the specified sentence list.
     * @param jsonStr JSON formatted data containing reply from DB query.
     * @param sentList The sentence list object to update with sentence content.
     * @return true if an error has occurred, false otherwise
     */
    public boolean processJSONSent(String jsonStr, List<Sent> sentList) {
        boolean error = false;
        String mode = ConstsSFIB.RESP_GET_SENT;
        int count = 1; // initialize past the status field
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray(mode);
            // get each row from json data
            while (count < jsonArray.length()){
                JSONObject JO = jsonArray.getJSONObject(count);
                 // Save all this information as a Sent object in the GameState
                sentList.add(new Sent(JO.getString(ConstsSFIB.REQ_SENT_ENG),
                        JO.getString(ConstsSFIB.REQ_SENT_FOR),
                        Integer.parseInt(JO.getString(ConstsSFIB.REQ_SENT_ID))));
                count++;
            }
        } catch (JSONException e) {
            error = true;
        }
        return error;
    }

    /**
     * Helper method to parse the results from fetching player stats.
     * Adds these to the GameState.
     * @param jsonStr JSON formatted data containing reply from DB query.
     * @return true if error occurs, false otherwise
     */
    public boolean processJSONStats(String jsonStr, GameState.SFIBStats stats) {
        boolean error = false;
        String mode = ConstsSFIB.RESP_GET_STATS;
        int count = 1; // initialize past the status field
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray(mode);
            // Get stats
            JSONObject JO = jsonArray.getJSONObject(count);
            // Save all this information
            stats.mSigmaDiff = Integer.parseInt(JO.getString(ConstsSFIB.REQ_STATS_SIG_DIFF));
            stats.mSigmaGuess = Integer.parseInt(JO.getString(ConstsSFIB.REQ_STATS_SIG_GUESS));
            stats.mSigmaHint = Integer.parseInt(JO.getString(ConstsSFIB.REQ_STATS_SIG_HINT));
            stats.mSigmaSkip = Integer.parseInt(JO.getString(ConstsSFIB.REQ_STATS_SIG_SKIP));
            stats.mSubmitCnt = Integer.parseInt(JO.getString(ConstsSFIB.REQ_STATS_NUM_SUBMIT));
            stats.mCumScore = Integer.parseInt(JO.getString(ConstsSFIB.REQ_STATS_CUM_SCORE));
            stats.mAvgDiff = Float.parseFloat(JO.getString(ConstsSFIB.REQ_STATS_AVG_DIFF));
            stats.mAvgGuess = Float.parseFloat(JO.getString(ConstsSFIB.REQ_STATS_AVG_GUESS));
            stats.mAvgSkip = Float.parseFloat(JO.getString(ConstsSFIB.REQ_STATS_AVG_SKIP));
            stats.mAvgHint = Float.parseFloat(JO.getString(ConstsSFIB.REQ_STATS_AVG_HINT));
            stats.mAddCnt = Integer.parseInt(JO.getString(ConstsSFIB.REQ_STATS_ADD_CNT));
        } catch (JSONException e) {
            //e.printStackTrace();
            error = true;
        }
        return error;
    }

    /**
     * Performs the work necessary when player presses the Submit button
     * @param userAns The answer the player has submitted via the View object on the screen.
     * @param ansCorrect The correct answer.
     * @param hintIdx The index indicating how many hints a player has taken for this submission.
     * @return points to award if the answer is correct, -1 if answer wrong
     */
    public int processSubmit(String userAns, String ansCorrect, int hintIdx, boolean statsEnabled) {
        int points = -1;
        //IF CORRECT Process score
        if(UtilsSFIB.checkAnswer(userAns, ansCorrect)) {
            // compute points for current submittal
            points = UtilsSFIB.computeScore(
                    ansCorrect,
                    hintIdx,
                    gs.getGuessCnt(),
                    gs.getNoHintBonus(),
                    gs.getNumGuessesPerPointPenalty());

            // If logged in as user, update player stats
            if(statsEnabled) {
                // Compute stats
                processStats(points, hintIdx);
            }
            // If the user scored at least 1 point, update the count of correct answers
            if(points > 0) {
                gs.setAddCnt(gs.getAddCnt() + 1);
            }
            // Reset number of guesses to set up for next word
            gs.setGuessCnt(0);
        } else {
            // update guess count which affects scoring
            gs.setGuessCnt(gs.getGuessCnt() + 1);
            //Log.d(TAG, "guess cnt = " + Integer.toString(gs.getGuessCnt()));
        }
        return points;
    }

    /**
     * Performs the work required when player presses Skip button
     */
    public void processSkip(boolean statsEnabled) {
        if(statsEnabled) {
            // Increment the skip count
            gs.incSigSkip();
        }
    }

    /**
     * Helper function to make ActivityGame more readable.  Places all
     * the Extras into the intent that will be sent to ActivityGameOptions.
     * The majority of these are held within GameEngine and part of the
     * GameState.
     * @param startIntent The intent to place the Extras into.
     * @param networkDown A flag indicating the network is down, not part
     *                    of the GameState variables, so passed in.
     * @param name player name
     * @param gameLang the language String of the current game
     */
    public void putGameOptExtras(Intent startIntent, boolean networkDown, boolean statsEnabled,
                                 String name, String gameLang) {
        // Pass extra to notify if trouble loading game content or stats
        // to gray out some buttons that won't work properly in that state
        startIntent.putExtra(ConstsSFIB.NET_DOWN_BOOL, networkDown);
        // Pass extra to notify game options about how many correct guesses player
        // has made to determine if the "add sent to DB" will be grayed out or not
        boolean addSentEnabled = false;
        if (gs.getAddCnt() >= gs.getAddThresh()) addSentEnabled = true;
        // Put add sentence count to know whether to enable the add sentence button
        startIntent.putExtra(ConstsSFIB.ADD_SENT_ENB_BOOL, addSentEnabled);
        // provide the current difficulty level to know where to set the slider
        startIntent.putExtra(ConstsSFIB.DIFFICULTY, gs.getDifficulty());
        // provide the current game statistics
        startIntent.putExtra(ConstsSFIB.STATS_ENB_BOOL, statsEnabled);
        startIntent.putExtra(Consts.NAME, name);
        startIntent.putExtra(Consts.GAME_LANG, gameLang);
        startIntent.putExtra(ConstsSFIB.CUMULATIVE_SCORE, Integer.toString(gs.getCumScore()));
        startIntent.putExtra(ConstsSFIB.AVG_DIFFICULTY, String.format("%6.2f", gs.getAvgDiff()));
        startIntent.putExtra(ConstsSFIB.AVG_GUESS_CNT, String.format("%6.2f", gs.getAvgGuess()));
        startIntent.putExtra(ConstsSFIB.AVG_NUM_SKIPS, String.format("%6.2f", gs.getAvgSkip()));
        startIntent.putExtra(ConstsSFIB.AVG_NUM_HINTS, String.format("%6.2f", gs.getAvgHint()));
    }

    // Provide access to game content
    public List<Sent> getSentList() { return GameState.getSentList(); }
    public GameState.SFIBStats getSFIBStats() { return GameState.getSFIBStats(); }
    public String getForSent() { return gs.getForSent(); }
    public String getEngSent() { return gs.getEngSent(); }
    public String getKey()     { return Integer.toString(gs.getKey()); }

    // Getters needed for ActivityGame to send statistics
    public String getSentBlockCnt() { return Integer.toString(gs.getSentBlockCnt()); }
    public String getSigmaDiff()    { return Integer.toString(gs.getSigmaDiff()); }
    public String getSigmaGuess()   { return Integer.toString(gs.getSigmaGuess()); }
    public String getSigmaHint()    { return Integer.toString(gs.getSigmaHint()); }
    public String getSigmaSkip()    { return Integer.toString(gs.getSigmaSkip()); }
    public String getSubmitCnt()    { return Integer.toString(gs.getSubmitCnt()); }
    public String getCumScore()     { return Integer.toString(gs.getCumScore()); }
    public String getAvgDiff()      { return String.valueOf(gs.getAvgDiff()); }
    public String getAvgGuess()     { return String.valueOf(gs.getAvgGuess()); }
    public String getAvgSkip()      { return String.valueOf(gs.getAvgSkip()); }
    public String getAvgHint()      { return String.valueOf(gs.getAvgHint()); }

    // For ActivityGame to get/set the add sentence to DB count after a new
    // sentence has been added to the DB (player must earn the privilege again)
    public void setAddCnt(int cnt) { gs.setAddCnt(cnt); }
    public String getAddCnt() { return Integer.toString(gs.getAddCnt()); }

    // For ActivityGame to update difficulty after GameOptions has responded
    public int getDifficulty() { return gs.getDifficulty(); }
    public void setDifficulty(int difficulty) { gs.setDifficulty(difficulty); }

    // Accessors for word lengths stored in GameState that set the bar
    // for easy or difficult word lengths.
    public int getWordLenEasy() { return gs.getWordLenEasy(); }
    public int getWordLenHard() { return gs.getWordLenHard(); }
    public int getGuessCnt() { return gs.getGuessCnt(); }
    public void setGuessCnt(int num) { gs.setGuessCnt(num); }
}
