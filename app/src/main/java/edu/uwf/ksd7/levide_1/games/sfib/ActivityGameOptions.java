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
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
import android.view.View;
import android.widget.Button;

import edu.uwf.ksd7.levide_1.Consts;
import edu.uwf.ksd7.levide_1.R;

/**
 * ActivityGameOptions presents the player with a secondary game screen that
 * compartmentalizes items not necessary for specific game play.  The game
 * difficulty can be set, a sentence can be flagged as inappropriate or
 * incorrect, a sentence can be added to the database if the player has earned
 * the privilege, player statistics can be viewed, and syntax/grammar help
 * may be accessed.
 */
public class ActivityGameOptions extends AppCompatActivity {
    //private static final String TAG = "ActivityGameOptions";
    final Context context = this;

    String mName, mGameLang, mCumScore, mAvgDiff, mAvgGuess, mAvgSkip, mAvgHint;
    int mDiff; // current difficulty setting

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sfib_game_options);

        // Get the intent extras
        Intent intent = getIntent();
        boolean netDown = intent.getBooleanExtra(ConstsSFIB.NET_DOWN_BOOL, true);
        // determine if the add sentence button should be enabled
        boolean addSentEnabled = intent.getBooleanExtra(ConstsSFIB.ADD_SENT_ENB_BOOL, false);
        // get the current difficulty to use to set the slider
        mDiff = intent.getIntExtra(ConstsSFIB.DIFFICULTY,0);
        // get the current stat values
        boolean statsEnabled = intent.getBooleanExtra(ConstsSFIB.STATS_ENB_BOOL, false);
        mName = intent.getStringExtra(Consts.NAME);
        mGameLang = intent.getStringExtra(Consts.GAME_LANG);
        mCumScore = intent.getStringExtra(ConstsSFIB.CUMULATIVE_SCORE);
        mAvgDiff = intent.getStringExtra(ConstsSFIB.AVG_DIFFICULTY);
        mAvgGuess = intent.getStringExtra(ConstsSFIB.AVG_GUESS_CNT);
        mAvgSkip = intent.getStringExtra(ConstsSFIB.AVG_NUM_SKIPS);
        mAvgHint = intent.getStringExtra(ConstsSFIB.AVG_NUM_HINTS);

        Button buttonViewStats;
        buttonViewStats = (Button) findViewById(R.id.game_opt_but_player_stats);
        buttonViewStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(),
                        ActivityPlayerStats.class);
                // provide the current game statistics
                startIntent.putExtra(Consts.NAME, mName);
                startIntent.putExtra(Consts.GAME_LANG, mGameLang);
                startIntent.putExtra(ConstsSFIB.CUMULATIVE_SCORE, mCumScore);
                startIntent.putExtra(ConstsSFIB.AVG_DIFFICULTY, mAvgDiff);
                startIntent.putExtra(ConstsSFIB.AVG_GUESS_CNT, mAvgGuess);
                startIntent.putExtra(ConstsSFIB.AVG_NUM_SKIPS, mAvgSkip);
                startIntent.putExtra(ConstsSFIB.AVG_NUM_HINTS, mAvgHint);
                startActivity(startIntent);
            }
        });

        // If stats are not enabled, disable the stats screen
        if (!statsEnabled || netDown) {
            //Log.d(TAG, "disabling stats screen");
            // disabled
            buttonViewStats.setClickable(false);
            buttonViewStats.getBackground().setColorFilter(Color.GRAY,
                    PorterDuff.Mode.MULTIPLY);

        } else {
            //Log.d(TAG, "enabling stats screen");
            // enabled
            buttonViewStats.setClickable(true);
            buttonViewStats.getBackground().setColorFilter(null);
        }

        Button buttonAddSentence;
        buttonAddSentence = (Button) findViewById(R.id.game_opt_but_add_sent);
        buttonAddSentence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(),
                        ActivityAddSent.class);
                // To send results back from ActivityDifficulty through
                // ActivityGameOptions back to ActivityGame
                startIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(startIntent);
                finish();
            }
        });

        // Determine if the add sentence button should be grayed out or not
        if(!addSentEnabled || netDown) {
            //Log.d(TAG, "disabling add sentence feature");
            // disabled
            buttonAddSentence.setClickable(false);
            buttonAddSentence.getBackground().setColorFilter(Color.GRAY,
                    PorterDuff.Mode.MULTIPLY);

        } else {
            //Log.d(TAG, "enabling add sentence feature");
            // enabled
            buttonAddSentence.setClickable(true);
            buttonAddSentence.getBackground().setColorFilter(null);
        }

        Button buttonChangeDifficulty;
        buttonChangeDifficulty = (Button) findViewById(R.id.game_opt_but_diff);
        buttonChangeDifficulty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(),
                        ActivityDifficulty.class);
                // To send results back from ActivityDifficulty through
                // ActivityGameOptions back to ActivityGame
                startIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startIntent.putExtra(ConstsSFIB.DIFFICULTY, mDiff);
                startActivity(startIntent);
                finish();
            }
        });

        Button buttonFlagSentence;
        buttonFlagSentence = (Button) findViewById(R.id.game_opt_but_flag);
        buttonFlagSentence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Present confirmation dialog
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Are you sure?");

                alert.setMessage("Click yes to flag sentence.")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // If user confirmed flag sentence, propagate that up
                        Intent i = getIntent();
                        i.putExtra(ConstsSFIB.GAME_OPTION, ConstsSFIB.FLAG_SENT);
                        i.putExtra(ConstsSFIB.FLAG_SENT, ConstsSFIB.FLAG_TRUE);
                        setResult(RESULT_OK, i);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //close dialog
                        dialog.cancel();
                        //finish();
                    }
                });

                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });

        // Determine if the add sentence button should be grayed out or not
        if(netDown) {
            //Log.d(TAG, "disabling flag sentence feature");
            // disabled
            buttonFlagSentence.setClickable(false);
            buttonFlagSentence.getBackground().setColorFilter(Color.GRAY,
                    PorterDuff.Mode.MULTIPLY);

        } else {
            //Log.d(TAG, "enabling flag sentence feature");
            // enabled
            buttonFlagSentence.setClickable(true);
            buttonFlagSentence.getBackground().setColorFilter(null);
        }

        Button buttonViewSyntaxHelp;
        buttonViewSyntaxHelp = (Button) findViewById(R.id.game_opt_but_grammar_help);
        buttonViewSyntaxHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(),
                        ActivityGrammarHelp.class);
                startIntent.putExtra(Consts.GAME_LANG, mGameLang);
                startActivity(startIntent);
            }
        });
    } // end onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // pass through results from downstream activities
        // (those with FLAG_ACTIVITY_FORWARD_RESULT specified) to parent
        setResult(resultCode, data);
        finish();
    }
}
