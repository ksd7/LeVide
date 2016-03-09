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
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.uwf.ksd7.levide_1.R;

/**
 * ActivityAddSent allows the user to add content to the game database, i.e.
 * a foreign sentence with its associated English equivalent.  The player
 * must earn the privilege by answering a certain number of questions correctly,
 * or the button remains grayed out.  This is to promote player interaction with
 * the game.
 */
public class ActivityAddSent extends AppCompatActivity {
    //private static final String TAG = "ActivityAddSent"; // for debug
    final Context context = this;

    /**
     * Message strings for toast messages when user submits erroneous input.
     * Made public to facilitate testing.
     */
    public static final String ENG_TOO_LONG_ERR  = "English too long.";
    public static final String FOR_TOO_LONG_ERR  = "Foreign too long.";
    public static final String ENG_TOO_SHORT_ERR = "Input English sentence.";
    public static final String FOR_TOO_SHORT_ERR = "Input foreign sentence.";
    public static final String BACKSLASH_ERR     = "No backslashes.";

    // for debug, results from Activity
    private static String mResultEng;
    private static String mResultFor;

    /**
     * Used for debug and testing to indicate that the results have been sent from this Activity.
     * @return strings that are the results to be sent back from this Activity via Intent
     */
    public String getEngResult() {return mResultEng;}
    public String getForResult() {return mResultFor;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sfib_add_sent);

        // Clear the English sentence edit text view
        EditText eng_et = (EditText) findViewById(R.id.add_sent_eng_sent);
        eng_et.setText("");
        // Clear the foreign sentence edit text view
        EditText for_et = (EditText) findViewById(R.id.add_sent_for_sent);
        for_et.setText("");

        Button buttonSubmit;
        buttonSubmit = (Button) findViewById(R.id.add_sent_but_submit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Present confirmation to confirm user is ready to submit
                //final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                AlertDialog.Builder alert = new AlertDialog.Builder(context);

                alert.setTitle("Ready to submit?");

                alert.setMessage("Confirm to proceed")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Extract English sentence from view
                                EditText eng_et = (EditText) findViewById(R.id.add_sent_eng_sent);
                                String engSent = eng_et.getText().toString();
                                // Extract foreign sentence from view
                                EditText for_et = (EditText) findViewById(R.id.add_sent_for_sent);
                                String forSent = for_et.getText().toString();

                                if(validateInput(engSent, forSent)) {
                                    // Get this intent to use to add extra to and return a result
                                    Intent i = getIntent();
                                    // Encode a name/value pair
                                    i.putExtra(ConstsSFIB.GAME_OPTION, ConstsSFIB.ADD_SENT);
                                    i.putExtra(ConstsSFIB.ADD_ENGLISH, engSent);
                                    i.putExtra(ConstsSFIB.ADD_FOREIGN, forSent);
                                    // set the results to propagate to parent activity
                                    setResult(RESULT_OK, i);
                                    // Set results for debug/testing
                                    mResultEng = engSent;
                                    mResultFor = forSent;
                                    // calling finish takes this activity off the stack
                                    finish();
                                } else dialog.cancel();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Just close the dialog and do nothing
                                dialog.cancel();
                            }
                        });

                // create the alert dialog
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });

        Button buttonEng;
        buttonEng = (Button) findViewById(R.id.add_sent_but_sent_eng);
        buttonEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText eng_et = (EditText) findViewById(R.id.add_sent_eng_sent);
                eng_et.requestFocus();
            }
        });

        Button buttonFor;
        buttonFor = (Button) findViewById(R.id.add_sent_but_sent_for);
        buttonFor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText for_et = (EditText) findViewById(R.id.add_sent_for_sent);
                for_et.requestFocus();
            }
        });

        Button buttonToggle;
        buttonToggle = (Button) findViewById(R.id.add_sent_but_toggle_keyboard);
        buttonToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the soft keyboard to the on state
                InputMethodManager imm =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        });
    }

    // Validates user input
    // returns true if sentences are valid
    private boolean validateInput(String engSent, String forSent) {
        // validate that English sentence is not over maximum length
        if(engSent.length() >= ConstsSFIB.MAX_SENT_LEN) {
            putToast(ENG_TOO_LONG_ERR);
            return false;
        }
        // validate that foreign sentence is not over maximum length
        if(forSent.length() >= ConstsSFIB.MAX_SENT_LEN) {
            putToast(FOR_TOO_LONG_ERR);
            return false;
        }
        // validate that user has put something into the English field
        if(engSent.length() <= 0) {
            putToast(ENG_TOO_SHORT_ERR);
            return false;
        }
        // validate that user has put something into the foreign field
        if(forSent.length() <= 0) {
            putToast(FOR_TOO_SHORT_ERR);
            return false;
        }
        // validate disallowing of backslashes in the string to prevent
        // any sort of binary insertion
        if(engSent.contains("\\") || forSent.contains("\\")) {
            putToast(BACKSLASH_ERR);
            return false;
        }
        // if made it here, no problems, so return valid
        return true;
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

