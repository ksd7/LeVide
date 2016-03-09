/**
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * ActivityStart runs when the application is launched and
 * presents the user three buttons -- Login with Google Credentials,
 * Login as Guest, or Help/About Levide.  Clicking on a button launches
 * the particular Activity associated with that button.
 */
public class ActivityStart extends AppCompatActivity  {
    //private static final String TAG = "ActivityStart"; // Debug tag

    /**
     * Used by OS to distinguish the results that are passed back
     * when an Activity is started for results.
     */
    private static final int LOGIN_REQUEST_CODE = 321;

    /**
     * OnCreate runs when the activity is launched.  It sets up the listeners
     * for each button which launch specific Activities (new screens).
     * @param savedInstanceState The Bundle with the Activity state that is passed
     *                           in mostly relevant when the Activity is restored
     *                           due to rotation or back button action.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levide_start);

        Button buttonLogin;
        buttonLogin = (Button) findViewById(R.id.start_but_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // On button click start the Activity for Login
                // Since it will return results, start it using "startActivityForResults"
                // Note that results will be caught in onActivityResult
                Intent startIntent = new Intent(getApplicationContext(), ActivityLogin.class);
                startActivityForResult(startIntent, LOGIN_REQUEST_CODE);
            }
        });

        Button buttonLoginGuest;
        buttonLoginGuest = (Button) findViewById(R.id.start_but_guest);
        buttonLoginGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logging in as a guest simply means load the game menu
                // also, set the email to an established Guest Email constant
                // so it can be detected downstream, and initialize the player
                // name to empty string.
                loadGameMenu(Consts.GUEST_EMAIL, "");
            }
        });

        Button buttonHelpAbout;
        buttonHelpAbout = (Button) findViewById(R.id.start_but_help_about);
        buttonHelpAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Activity for Help/About
                Intent startIntent = new Intent(getApplicationContext(), ActivityHelpAbout.class);
                startActivity(startIntent);
            }
        });
    }

    /**
     * This method is called when an activity that has been started as "startActivityForResult"
     * gets results, sending them back to the activity that started it.
     * @param requestCode A code that was specified by the starting activity to
     *                    identify who is making the callback.
     * @param resultCode The result that was placed by the caller.
     * @param data The intent from the other activity that can contain Extras (passed data to
     *             be extracted).
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // The only expected request code is the login request
        // with a payload of successful login, otherwise ignore
        // User can login as a guest if trouble, and Google API can handle
        // all of the issues.
        if (LOGIN_REQUEST_CODE==requestCode && RESULT_OK==resultCode) {
            if(null!=data) {
                String personEmail = data.getStringExtra(Consts.EMAIL);
                String personName = data.getStringExtra(Consts.NAME);
                loadGameMenu(personEmail, personName);
            }
        }
    }

    // Helper function for identical code called more than once.  Starts
    // a new activity to launch the Game Menu screen and passes data for player
    // email and name derived from the login activities.
    private void loadGameMenu(String email, String nameStr) {
        Intent startIntent = new Intent(getApplicationContext(), ActivityGameMenu.class);
        startIntent.putExtra(Consts.EMAIL, email);
        startIntent.putExtra(Consts.NAME, nameStr);
        startActivity(startIntent);
    }

}
