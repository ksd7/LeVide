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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import edu.uwf.ksd7.levide_1.R;

/**
 * ActivityDifficulty allows the player to set the difficulty level of
 * the SFIB game via a slider.
 */
public class ActivityDifficulty extends AppCompatActivity {
    //private static final String TAG = "ActivityDifficulty"; // for debug
    private static final String KEY_DIFF = "diff"; // for saving state across rotation
    private int mDifficulty = 1; // difficulty level (1-5)
    private static String mDiffResult; // for debug, results from Activity
    /**
     * Used for debug and testing to indicate that the results have been sent from this Activity.
     * @return a string with the difficulty that is returned via Intent
     */
    public String getDiffResult() {return mDiffResult;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sfib_difficulty);

        // Get the intent extra to set the current difficulty on the slider
        Intent intent = getIntent();
        // Extract results in range 0-4, not 1-5 as it is stored
        mDifficulty = intent.getIntExtra(ConstsSFIB.DIFFICULTY, 1) - 1;
        //Log.d(TAG, "difficulty from intent is " + mDifficulty+1);

        if(null!=savedInstanceState) {
            // restore difficulty from rotation
            mDifficulty = savedInstanceState.getInt(KEY_DIFF, 1);
        }

        SeekBar seek = (SeekBar) findViewById(R.id.diffSlider);
        seek.setProgress(mDifficulty);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast toast = Toast.makeText(getBaseContext(),
                        "difficulty = " + String.valueOf(mDifficulty+1), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mDifficulty = progress;
            }
        });

        Button buttonSubmit;
        buttonSubmit = (Button) findViewById(R.id.game_sfib_opt_diff_submit_button);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get this intent to use to add extra to and return a result
                Intent i = getIntent();
                // Encode a name/value pair
                i.putExtra(ConstsSFIB.GAME_OPTION, ConstsSFIB.DIFFICULTY);
                // Send results in range 1-5, not 0-4 like slider
                mDifficulty += 1;
                i.putExtra(ConstsSFIB.DIFFICULTY, Integer.toString(mDifficulty));
                // set the results to propagate to parent activity
                setResult(RESULT_OK, i);
                // Set results for debug/testing
                mDiffResult = Integer.toString(mDifficulty);
                // calling finish takes this activity off the stack
                finish();
            }
        });
    }

    /**
     * Maintains state across rotation
     * @param savedInstanceState a Bundle with the state
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_DIFF, mDifficulty);
    }
}
