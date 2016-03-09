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
package edu.uwf.ksd7.levide_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
import android.view.View;
import android.widget.Button;

import edu.uwf.ksd7.levide_1.games.sfib.ActivityGame;
import edu.uwf.ksd7.levide_1.games.sfib.ActivityHelp;
import edu.uwf.ksd7.levide_1.games.sfib.UtilsSFIB;

/**
 * ActivityGameMenu allows the user to select which game they wish to play and
 * provides the ability to select game help for each game.
 */
public class ActivityGameMenu extends AppCompatActivity {
    //private static final String TAG = "ActivityGameMenu"; // for debug
    private String mLangString; // Game language
    private String mEmail; // player email
    private String mNameStr; // player name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levide_game_menu);

        // Get the intent extra for player email and player name from ActivityStart
        Intent intent = getIntent();
        mEmail = intent.getStringExtra(Consts.EMAIL);
        mNameStr = intent.getStringExtra(Consts.NAME);
        //Log.d(TAG, "Game Menu got email = " + email + " name = " + nameStr);

        Button buttonSFIBHelp1;
        buttonSFIBHelp1 = (Button) findViewById(R.id.game_menu_but_sfib_help_1);
        buttonSFIBHelp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), ActivityHelp.class);
                startActivity(startIntent);
            }
        });

        Button buttonGameSFIBFrench;
        buttonGameSFIBFrench = (Button) findViewById(R.id.game_menu_but_sfib_french);
        buttonGameSFIBFrench.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), ActivityGame.class);
                // Specify the player selected the French SFIB game
                mLangString = UtilsSFIB.SFIBGameLang.FRE.toString();
                setSFIBIntentExtras(startIntent, mLangString);
                startActivity(startIntent);
            }
        });

        Button buttonSFIBHelp2;
        buttonSFIBHelp2 = (Button) findViewById(R.id.game_menu_but_sfib_help_2);
        buttonSFIBHelp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), ActivityHelp.class);
                startActivity(startIntent);
            }
        });

        Button buttonGameSFIBSpanish;
        buttonGameSFIBSpanish = (Button) findViewById(R.id.game_menu_but_sfib_spanish);
        buttonGameSFIBSpanish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), ActivityGame.class);
                // Specify the player selected the Spanish SFIB game
                mLangString = UtilsSFIB.SFIBGameLang.SPA.toString();
                setSFIBIntentExtras(startIntent, mLangString);
                startActivity(startIntent);
            }
        });
    }

    // Helper method for code used more than once
    // Sets up the Intent extras that are to be passed into the SFIB game
    private void setSFIBIntentExtras(Intent startIntent, String lang) {
        startIntent.putExtra(Consts.GAME_LANG, lang);
        startIntent.putExtra(Consts.EMAIL, mEmail);
        startIntent.putExtra(Consts.NAME, mNameStr);
    }
}
