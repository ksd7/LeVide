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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import edu.uwf.ksd7.levide_1.Consts;
import edu.uwf.ksd7.levide_1.R;

/**
 * ActivityPlayerStats displays player statistics for the currently
 * loaded SFIB game.
 */
public class ActivityPlayerStats extends AppCompatActivity {
    // For debug
    //private static final String TAG = "ActivityPlayerStats";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sfib_stats);

        // Get the intent extra for player email to extract stats from DB
        String name, game, cumScore, avgDiff, avgGuess, avgSkip, avgHint;
        Intent intent = getIntent();
        name = intent.getStringExtra(Consts.NAME);
        game = intent.getStringExtra(Consts.GAME_LANG);
        cumScore = intent.getStringExtra(ConstsSFIB.CUMULATIVE_SCORE);
        avgDiff = intent.getStringExtra(ConstsSFIB.AVG_DIFFICULTY);
        avgGuess = intent.getStringExtra(ConstsSFIB.AVG_GUESS_CNT);
        avgSkip = intent.getStringExtra(ConstsSFIB.AVG_NUM_SKIPS);
        avgHint = intent.getStringExtra(ConstsSFIB.AVG_NUM_HINTS);

        //Put the stats into the web page
        TextView tv;
        tv = (TextView) findViewById(R.id.stats_player_label);
        String label = getString(R.string.stats_player_label, game);
        tv.setText(label);

        tv = (TextView) findViewById(R.id.stats_player);
        tv.setText(name);

        tv = (TextView) findViewById(R.id.stats_cum_score);
        tv.setText(cumScore);

        tv = (TextView) findViewById(R.id.stats_avg_diff);
        tv.setText(avgDiff);

        tv = (TextView) findViewById(R.id.stats_avg_guess);
        tv.setText(avgGuess);

        tv = (TextView) findViewById(R.id.stats_avg_skip);
        tv.setText(avgSkip);

        tv = (TextView) findViewById(R.id.stats_avg_hint);
        tv.setText(avgHint);

    }
}
