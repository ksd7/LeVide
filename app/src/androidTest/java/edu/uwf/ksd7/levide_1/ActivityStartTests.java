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

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumented testing for ActivityStart.
 */
public class ActivityStartTests extends ActivityInstrumentationTestCase2<ActivityStart> {

    public ActivityStartTests() {
        super(ActivityStart.class);
    }

    public void testActivityExists() {
        ActivityStart activity = getActivity();
        assertNotNull(activity);
    }

    public void testLoginButtonPress() {
        // get current activity
        getActivity();

        // register next activity that needs to be monitored
        Instrumentation.ActivityMonitor loginMonitor =
                getInstrumentation().addMonitor(ActivityLogin.class.getName(), null, false);

        onView(withId(R.id.start_but_login)).perform(click());

        // determine if the next activity was launched in the timeout specified
        Activity loginActivity = getInstrumentation().waitForMonitorWithTimeout
                (loginMonitor, 5000);
        assertNotNull(loginActivity);
        assertEquals(loginActivity.getClass().getSimpleName(), ActivityLogin.class
                .getSimpleName
                        ());
        loginActivity.finish(); // Return back to Start screen
    }

    public void testLoginGuestButtonPress() {
        // get current activity
        getActivity();

        // register next activity that needs to be monitored
        Instrumentation.ActivityMonitor gameMenuMonitor =
                getInstrumentation().addMonitor(ActivityGameMenu.class.getName(), null, false);

        onView(withId(R.id.start_but_guest)).perform(click());

        // determine if the next activity was launched in the timeout specified
        Activity gameMenuActivity = getInstrumentation().waitForMonitorWithTimeout
                (gameMenuMonitor, 5000);
        assertNotNull(gameMenuActivity);
        assertEquals(gameMenuActivity.getClass().getSimpleName(), ActivityGameMenu.class
                .getSimpleName
                ());
        gameMenuActivity.finish(); // Return from Help/About back to Start screen
     }

    public void testHelpAboutButtonPress() {
        // get current activity
        getActivity();

        // register next activity that needs to be monitored
        Instrumentation.ActivityMonitor helpAboutMonitor =
                getInstrumentation().addMonitor(ActivityHelpAbout.class.getName(), null, false);

        onView(withId(R.id.start_but_help_about)).perform(click());

        // determine if the next activity was launched in the timeout specified
        Activity helpAboutActivity = getInstrumentation().waitForMonitorWithTimeout
                (helpAboutMonitor, 5000);
        assertNotNull(helpAboutActivity);
        assertEquals(helpAboutActivity.getClass().getSimpleName(), ActivityHelpAbout.class.getSimpleName
                ());
        helpAboutActivity.finish(); // Return from Help/About back to Start screen
    }

}
