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
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import edu.uwf.ksd7.levide_1.games.sfib.ActivityGame;
import edu.uwf.ksd7.levide_1.games.sfib.ActivityHelp;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumented testing for ActivityLogin.
 */
public class ActivityGameMenuTests extends ActivityInstrumentationTestCase2<ActivityGameMenu> {

    public ActivityGameMenuTests() {
        super(ActivityGameMenu.class);
    }

    public void testActivityExists() {
        Intent intent = new Intent();
        intent.putExtra(Consts.NAME, "me");
        intent.putExtra(Consts.EMAIL, "dumb@fine.com");
        setActivityIntent(intent);

        ActivityGameMenu activity = getActivity();
        assertNotNull(activity);
    }

    public void testHelp1ButtonPress() {
        Intent intent = new Intent();
        intent.putExtra(Consts.NAME, "me");
        intent.putExtra(Consts.EMAIL, "dumb@fine.com");
        setActivityIntent(intent);

        // get current activity
        ActivityGameMenu gameMenuActivity = getActivity();

        // register next activity that needs to be monitored
        Instrumentation.ActivityMonitor gameHelpMonitor =
                getInstrumentation().addMonitor(ActivityHelp.class.getName(), null, false);

        onView(withId(R.id.game_menu_but_sfib_help_1)).perform(click());

        // determine if the next activity was launched in the timeout specified
        Activity gameHelpActivity = getInstrumentation().waitForMonitorWithTimeout
                (gameHelpMonitor, 5000);
        assertNotNull(gameHelpActivity);
        assertEquals(gameHelpActivity.getClass().getSimpleName(), ActivityHelp.class
                .getSimpleName());
        gameHelpActivity.finish(); // Return from Help/About back to Start screen
    }

    public void testHelp2ButtonPress() {
        Intent sfibIntent = new Intent();
        sfibIntent.putExtra(Consts.NAME, "me");
        sfibIntent.putExtra(Consts.EMAIL, "dumb@fine.com");
        setActivityIntent(sfibIntent);

        // get current activity
        ActivityGameMenu gameMenuActivity = getActivity();

        // register next activity that needs to be monitored
        Instrumentation.ActivityMonitor gameHelpMonitor =
                getInstrumentation().addMonitor(ActivityHelp.class.getName(), null, false);

        onView(withId(R.id.game_menu_but_sfib_help_2)).perform(click());

        // determine if the next activity was launched in the timeout specified
        Activity gameHelpActivity = getInstrumentation().waitForMonitorWithTimeout
                (gameHelpMonitor, 5000);
        assertNotNull(gameHelpActivity);
        assertEquals(gameHelpActivity.getClass().getSimpleName(), ActivityHelp.class
                .getSimpleName());
        gameHelpActivity.finish(); // Return from Help/About back to Start screen
    }

    public void testSfibFreButtonPress() {
        Intent intent = new Intent();
        intent.putExtra(Consts.NAME, "me");
        intent.putExtra(Consts.EMAIL, "dumb@fine.com");
        setActivityIntent(intent);

        // get current activity
        ActivityGameMenu gameMenuActivity = getActivity();

        // register next activity that needs to be monitored
        Instrumentation.ActivityMonitor gameSfibMonitor =
                getInstrumentation().addMonitor(ActivityGame.class.getName(), null, false);

        onView(withId(R.id.game_menu_but_sfib_french)).perform(click());

        // determine if the next activity was launched in the timeout specified
        Activity gameSfibActivity = getInstrumentation().waitForMonitorWithTimeout
                (gameSfibMonitor, 5000);
        assertNotNull(gameSfibActivity);
        assertEquals(gameSfibActivity.getClass().getSimpleName(), ActivityGame.class
                .getSimpleName());
        gameSfibActivity.finish(); // Return from Help/About back to Start screen
    }

    public void testSfibSpaButtonPress() {
        Intent intent = new Intent();
        intent.putExtra(Consts.NAME, "me");
        intent.putExtra(Consts.EMAIL, "dumb@fine.com");
        setActivityIntent(intent);

        // get current activity
        ActivityGameMenu gameMenuActivity = getActivity();

        // register next activity that needs to be monitored
        Instrumentation.ActivityMonitor gameSfibMonitor =
                getInstrumentation().addMonitor(ActivityGame.class.getName(), null, false);

        onView(withId(R.id.game_menu_but_sfib_spanish)).perform(click());

        // determine if the next activity was launched in the timeout specified
        Activity gameSfibActivity = getInstrumentation().waitForMonitorWithTimeout
                (gameSfibMonitor, 5000);
        assertNotNull(gameSfibActivity);
        assertEquals(gameSfibActivity.getClass().getSimpleName(), ActivityGame.class
                .getSimpleName());
        gameSfibActivity.finish(); // Return from Help/About back to Start screen

    }
}
