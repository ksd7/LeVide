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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * ActivityLogin utilizes the Google SignIn API which is part of the Google
 * Play Service to log a player in using their Google Credentials.
 * Retrieval of the Google user's name and email address is performed
 * given successful login via Google Play services.  A player may also
 * set up a new account with Google.
 */
public class ActivityLogin extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {
    //private static final String TAG = "ActivityLogin"; // For debug
    // Unique code used when spawning Activity for result
    private static final int RC_SIGN_IN = 9001;

    // Google API specific
    private GoogleApiClient mGoogleApiClient;

    // The TextView used to show sign in status
    private TextView mStatusTextView;

    /**
     * Sets up the View objects and listeners when Activity is spawned
     * @param savedInstanceState Bundle that holds state variables used when
     *                           this activity is paused and restored.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levide_login);

        // Views
        mStatusTextView = (TextView) findViewById(R.id.login_status);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Customize sign-in button.
        SignInButton signInButton = (SignInButton) findViewById(R.id.login_but_sign_in);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(R.id.login_but_sign_in==v.getId()) {
                    //Log.d(TAG, "onClick calling signIn");
                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                }
            }
        });
    }

    /**
     * onActivityResult is the callback for Activities started with results
     * When results are available, this method executes
     * @param requestCode Unique code set by the creator of the Activity whose
     *                    results are being solicited in case there are multiple
     *                    Activities whose results are being solicited.
     * @param resultCode The code result set by the spawned activity whose
     *                   results are being solicited.
     * @param data The relevant intent that the callback is referenced to.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.d(TAG, "onActivityResult called");
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    /**
     * GoogleAPIClient setup registers this listener callback which gets called
     * as a result of failure to connect to the network
     * @param connectionResult results of a failed connection to client service
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Put toast with appropriate msg to screen in a visible location
        Toast toast = Toast.makeText(getApplicationContext(), "Connection failed for " +
                "Sign-In/nLogging you in as guest...", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
        // process results
        sendLoginResults(null, null);
    }

    // Called when sign in results are available after callback onActivityResults
    // is made active.  Extracts relevant player info from the sign in results
    // to pass out of this Activity via intent.
    private void handleSignInResult(GoogleSignInResult result) {
        //Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            //Log.d(TAG, "setting status to " +
            //        getString(R.string.signed_in_fmt, acct.getDisplayName()));
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            // process results
            sendLoginResults(personName, personEmail);
            mStatusTextView.setText(getString(R.string.login_signed_in_fmt, personName));
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    // Helper method for code used more than once.
    // Send the login results forward.
    private void sendLoginResults(String pName, String pEmail) {
        // Test for Nulls returned from Google
        if(null==pName)  pName = "";
        if(null==pEmail) pEmail = Consts.GUEST_EMAIL;

        // propagate login results up
        Intent i = getIntent();
        i.putExtra(Consts.EMAIL, pEmail);
        i.putExtra(Consts.NAME, pName);
        setResult(RESULT_OK, i);
        // Called to allow user to login as a different user on subsequent login attempts
        // This is not necessary and can be removed, allowing user to stay logged in
        // on the device.
        revokeAccess();
    }

    // Revokes the credentials of the user for the API variables used here
    // which results in subsequent automatic logins not occurring
    // In other words, Google API will cache the user's credentials, so
    // if they were successfully logged in prior, they will be automatically
    // logged in under the same user's credentials.  This may or may not
    // be the desired behavior, but it gives no opportunity for the user
    // to login under different credentials.  They have to remove their
    // account in device settings to get the "pure" login screen back.  By revoking
    // access at the end of successful login, they always have the option
    // on future login attempts of selecting a different account to login under.
    // // This results in one additional button push, so it isn't deemed a
    // big deal for the sake of ease of use.
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient)
            .setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                try {
                    // Give some time to read the player name. 500 ms.
                    Thread.sleep(500);
                    // Finish this activity and advance to next screen
                    finish();
                } catch (InterruptedException e) {
                    finish();
                }
            }
        });
    }

    // updateUI controls the visibility of the sign in button based on
    // passed boolean
    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.login_but_sign_in).setVisibility(View.GONE);
        } else {
            mStatusTextView.setText(R.string.login_signed_out);
            findViewById(R.id.login_but_sign_in).setVisibility(View.VISIBLE);
        }
    }

}
