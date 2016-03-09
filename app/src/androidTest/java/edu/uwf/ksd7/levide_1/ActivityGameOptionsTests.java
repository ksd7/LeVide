package edu.uwf.ksd7.levide_1;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.NoMatchingViewException;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import edu.uwf.ksd7.levide_1.games.sfib.ActivityAddSent;
import edu.uwf.ksd7.levide_1.games.sfib.ActivityDifficulty;
import edu.uwf.ksd7.levide_1.games.sfib.ActivityGameOptions;
import edu.uwf.ksd7.levide_1.games.sfib.ActivityGrammarHelp;
import edu.uwf.ksd7.levide_1.games.sfib.ActivityPlayerStats;
import edu.uwf.ksd7.levide_1.games.sfib.ConstsSFIB;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Instrumented testing for ActivityGameOptions.
 */
public class ActivityGameOptionsTests extends
        ActivityInstrumentationTestCase2<ActivityGameOptions> {

    public ActivityGameOptionsTests() {
        super(ActivityGameOptions.class);
    }

    public void testActivityExists() {
        Intent intent = new Intent();
        intent.putExtra(ConstsSFIB.NET_DOWN_BOOL, false);
        intent.putExtra(ConstsSFIB.ADD_SENT_ENB_BOOL, true);
        intent.putExtra(ConstsSFIB.DIFFICULTY, 5);
        intent.putExtra(ConstsSFIB.STATS_ENB_BOOL, true);
        intent.putExtra(Consts.NAME, "me");
        intent.putExtra(Consts.GAME_LANG, "FRE");
        intent.putExtra(ConstsSFIB.CUMULATIVE_SCORE, "10");
        intent.putExtra(ConstsSFIB.AVG_DIFFICULTY, "2.5");
        intent.putExtra(ConstsSFIB.AVG_GUESS_CNT, "2.5");
        intent.putExtra(ConstsSFIB.AVG_NUM_SKIPS, "2.5");
        intent.putExtra(ConstsSFIB.AVG_NUM_HINTS, "2.5");
        setActivityIntent(intent);

        ActivityGameOptions activity = getActivity();
        assertNotNull(activity);
    }

    public void testChangeDiffButton() {
        Intent intent = new Intent();
        intent.putExtra(ConstsSFIB.NET_DOWN_BOOL, false);
        intent.putExtra(ConstsSFIB.ADD_SENT_ENB_BOOL, true);
        intent.putExtra(ConstsSFIB.DIFFICULTY, 5);
        intent.putExtra(ConstsSFIB.STATS_ENB_BOOL, true);
        intent.putExtra(Consts.NAME, "me");
        intent.putExtra(Consts.GAME_LANG, "FRE");
        intent.putExtra(ConstsSFIB.CUMULATIVE_SCORE, "10");
        intent.putExtra(ConstsSFIB.AVG_DIFFICULTY, "2.5");
        intent.putExtra(ConstsSFIB.AVG_GUESS_CNT, "2.5");
        intent.putExtra(ConstsSFIB.AVG_NUM_SKIPS, "2.5");
        intent.putExtra(ConstsSFIB.AVG_NUM_HINTS, "2.5");
        setActivityIntent(intent);

        // get current activity
        ActivityGameOptions activity = getActivity();

        // register next activity that needs to be monitored
        Instrumentation.ActivityMonitor diffMonitor =
                getInstrumentation().addMonitor(ActivityDifficulty.class.getName(), null, false);

        onView(withId(R.id.game_opt_but_diff)).perform(click());

        // determine if the next activity was launched in the timeout specified
        Activity diffActivity = getInstrumentation().waitForMonitorWithTimeout
                (diffMonitor, 5000);
        assertNotNull(diffActivity);
        assertEquals(diffActivity.getClass().getSimpleName(), ActivityDifficulty.class
                .getSimpleName
                        ());
        diffActivity.finish();

    }

    public void testFlagSentButton() {
        Intent intent = new Intent();
        intent.putExtra(ConstsSFIB.NET_DOWN_BOOL, false);
        intent.putExtra(ConstsSFIB.ADD_SENT_ENB_BOOL, true);
        intent.putExtra(ConstsSFIB.DIFFICULTY, 5);
        intent.putExtra(ConstsSFIB.STATS_ENB_BOOL, true);
        intent.putExtra(Consts.NAME, "me");
        intent.putExtra(Consts.GAME_LANG, "FRE");
        intent.putExtra(ConstsSFIB.CUMULATIVE_SCORE, "10");
        intent.putExtra(ConstsSFIB.AVG_DIFFICULTY, "2.5");
        intent.putExtra(ConstsSFIB.AVG_GUESS_CNT, "2.5");
        intent.putExtra(ConstsSFIB.AVG_NUM_SKIPS, "2.5");
        intent.putExtra(ConstsSFIB.AVG_NUM_HINTS, "2.5");
        setActivityIntent(intent);

        // get current activity
        ActivityGameOptions activity = getActivity();

        onView(withId(R.id.game_opt_but_flag)).perform(click());

        boolean thrown = false;
        // Confirm the dialog loads and click yes to submit
        try {
            onView(withText("Are you sure?")).
                    inRoot(withDecorView(not(is(activity.getWindow().
                            getDecorView())))).check(matches(isDisplayed()));
            onView(withText("No")).perform(click());

        } catch (NoMatchingViewException e) {
            //View is not in hierarchy
            thrown = true;
        }
        assertFalse(thrown);

        onView(withId(R.id.game_opt_but_flag)).perform(click());

        thrown = false;
        // Confirm the dialog loads and click yes to submit
        try {
            onView(withText("Are you sure?")).
                    inRoot(withDecorView(not(is(getActivity().getWindow().
                            getDecorView())))).check(matches(isDisplayed()));
            onView(withText("Yes")).perform(click());

        } catch (NoMatchingViewException e) {
            //View is not in hierarchy
            thrown = true;
        }
        assertFalse(thrown);

    }

    public void testAddSentButtonVisible() {
        Intent intent = new Intent();
        intent.putExtra(ConstsSFIB.NET_DOWN_BOOL, false);
        intent.putExtra(ConstsSFIB.ADD_SENT_ENB_BOOL, true);
        intent.putExtra(ConstsSFIB.DIFFICULTY, 5);
        intent.putExtra(ConstsSFIB.STATS_ENB_BOOL, true);
        intent.putExtra(Consts.NAME, "me");
        intent.putExtra(Consts.GAME_LANG, "FRE");
        intent.putExtra(ConstsSFIB.CUMULATIVE_SCORE, "10");
        intent.putExtra(ConstsSFIB.AVG_DIFFICULTY, "2.5");
        intent.putExtra(ConstsSFIB.AVG_GUESS_CNT, "2.5");
        intent.putExtra(ConstsSFIB.AVG_NUM_SKIPS, "2.5");
        intent.putExtra(ConstsSFIB.AVG_NUM_HINTS, "2.5");
        setActivityIntent(intent);

        // get current activity
        getActivity();

        // register next activity that needs to be monitored
        Instrumentation.ActivityMonitor addMonitor =
                getInstrumentation().addMonitor(ActivityAddSent.class.getName(), null, false);

        onView(withId(R.id.game_opt_but_add_sent)).perform(click());

        // determine if the next activity was launched in the timeout specified
        Activity addActivity = getInstrumentation().waitForMonitorWithTimeout
                (addMonitor, 5000);
        assertNotNull(addActivity);
        assertEquals(addActivity.getClass().getSimpleName(), ActivityAddSent.class
                .getSimpleName());
        addActivity.finish();
    }


    public void testAddSentButtonInvisible() {
        Intent intent = new Intent();
        intent.putExtra(ConstsSFIB.NET_DOWN_BOOL, false);
        intent.putExtra(ConstsSFIB.ADD_SENT_ENB_BOOL, false);
        intent.putExtra(ConstsSFIB.DIFFICULTY, 5);
        intent.putExtra(ConstsSFIB.STATS_ENB_BOOL, true);
        intent.putExtra(Consts.NAME, "me");
        intent.putExtra(Consts.GAME_LANG, "FRE");
        intent.putExtra(ConstsSFIB.CUMULATIVE_SCORE, "10");
        intent.putExtra(ConstsSFIB.AVG_DIFFICULTY, "2.5");
        intent.putExtra(ConstsSFIB.AVG_GUESS_CNT, "2.5");
        intent.putExtra(ConstsSFIB.AVG_NUM_SKIPS, "2.5");
        intent.putExtra(ConstsSFIB.AVG_NUM_HINTS, "2.5");
        setActivityIntent(intent);

        // get current activity
        ActivityGameOptions activity = getActivity();

        // register next activity that needs to be monitored
        Instrumentation.ActivityMonitor addMonitor =
                getInstrumentation().addMonitor(ActivityAddSent.class.getName(), null, false);

        onView(withId(R.id.game_opt_but_add_sent)).perform(click());

        // determine if the next activity was launched in the timeout specified
        Activity addActivity = getInstrumentation().waitForMonitorWithTimeout
                (addMonitor, 5000);

        assertNull(addActivity);

        TestUtils.rotateScreen(activity);

        // determine if the next activity was launched in the timeout specified
        addActivity = getInstrumentation().waitForMonitorWithTimeout
                (addMonitor, 5000);

        onView(withId(R.id.game_opt_but_add_sent)).perform(click());
        assertNull(addActivity);
    }

    public void testGrammarHelpButton() {
        Intent intent = new Intent();
        intent.putExtra(ConstsSFIB.NET_DOWN_BOOL, false);
        intent.putExtra(ConstsSFIB.ADD_SENT_ENB_BOOL, true);
        intent.putExtra(ConstsSFIB.DIFFICULTY, 5);
        intent.putExtra(ConstsSFIB.STATS_ENB_BOOL, true);
        intent.putExtra(Consts.NAME, "me");
        intent.putExtra(Consts.GAME_LANG, "FRE");
        intent.putExtra(ConstsSFIB.CUMULATIVE_SCORE, "10");
        intent.putExtra(ConstsSFIB.AVG_DIFFICULTY, "2.5");
        intent.putExtra(ConstsSFIB.AVG_GUESS_CNT, "2.5");
        intent.putExtra(ConstsSFIB.AVG_NUM_SKIPS, "2.5");
        intent.putExtra(ConstsSFIB.AVG_NUM_HINTS, "2.5");
        setActivityIntent(intent);

        // get current activity
        getActivity();

        // register next activity that needs to be monitored
        Instrumentation.ActivityMonitor helpGramMonitor =
                getInstrumentation().addMonitor(ActivityGrammarHelp.class.getName(), null, false);

        onView(withId(R.id.game_opt_but_grammar_help)).perform(click());

        // determine if the next activity was launched in the timeout specified
        Activity helpGramActivity = getInstrumentation().waitForMonitorWithTimeout
                (helpGramMonitor, 5000);

        assertNotNull(helpGramActivity);
        assertEquals(helpGramActivity.getClass().getSimpleName(), ActivityGrammarHelp.class
                .getSimpleName());
        helpGramActivity.finish();
    }

    public void testViewStatsVisible() {
        Intent intent = new Intent();
        intent.putExtra(ConstsSFIB.NET_DOWN_BOOL, false);
        intent.putExtra(ConstsSFIB.ADD_SENT_ENB_BOOL, true);
        intent.putExtra(ConstsSFIB.DIFFICULTY, 5);
        intent.putExtra(ConstsSFIB.STATS_ENB_BOOL, true);
        intent.putExtra(Consts.NAME, "me");
        intent.putExtra(Consts.GAME_LANG, "FRE");
        intent.putExtra(ConstsSFIB.CUMULATIVE_SCORE, "10");
        intent.putExtra(ConstsSFIB.AVG_DIFFICULTY, "2.5");
        intent.putExtra(ConstsSFIB.AVG_GUESS_CNT, "2.5");
        intent.putExtra(ConstsSFIB.AVG_NUM_SKIPS, "2.5");
        intent.putExtra(ConstsSFIB.AVG_NUM_HINTS, "2.5");
        setActivityIntent(intent);

        ActivityGameOptions activity = getActivity();

        // register next activity that needs to be monitored
        Instrumentation.ActivityMonitor statsMonitor =
                getInstrumentation().addMonitor(ActivityPlayerStats.class.getName(), null, false);

        onView(withId(R.id.game_opt_but_player_stats)).perform(click());

        // determine if the next activity was launched in the timeout specified
        Activity statsActivity = getInstrumentation().waitForMonitorWithTimeout
                (statsMonitor, 5000);

        assertNotNull(statsActivity);
        assertEquals(statsActivity.getClass().getSimpleName(), ActivityPlayerStats.class
                .getSimpleName());
        statsActivity.finish();
    }

    public void testViewStatsInvisible() {
        Intent intent = new Intent();
        intent.putExtra(ConstsSFIB.NET_DOWN_BOOL, false);
        intent.putExtra(ConstsSFIB.ADD_SENT_ENB_BOOL, true);
        intent.putExtra(ConstsSFIB.DIFFICULTY, 5);
        intent.putExtra(ConstsSFIB.STATS_ENB_BOOL, false);
        intent.putExtra(Consts.NAME, "me");
        intent.putExtra(Consts.GAME_LANG, "FRE");
        intent.putExtra(ConstsSFIB.CUMULATIVE_SCORE, "10");
        intent.putExtra(ConstsSFIB.AVG_DIFFICULTY, "2.5");
        intent.putExtra(ConstsSFIB.AVG_GUESS_CNT, "2.5");
        intent.putExtra(ConstsSFIB.AVG_NUM_SKIPS, "2.5");
        intent.putExtra(ConstsSFIB.AVG_NUM_HINTS, "2.5");
        setActivityIntent(intent);

        ActivityGameOptions activity = getActivity();

        // register next activity that needs to be monitored
        Instrumentation.ActivityMonitor statsMonitor =
                getInstrumentation().addMonitor(ActivityPlayerStats.class.getName(), null, false);

        onView(withId(R.id.game_opt_but_player_stats)).perform(click());

        // determine if the next activity was launched in the timeout specified
        Activity statsActivity = getInstrumentation().waitForMonitorWithTimeout
                (statsMonitor, 5000);

        assertNull(statsActivity);

        TestUtils.rotateScreen(activity);

        statsActivity = getInstrumentation().waitForMonitorWithTimeout
                (statsMonitor, 5000);

        onView(withId(R.id.game_opt_but_player_stats)).perform(click());

        assertNull(statsActivity);

    }

    public void testNetDown() {
        Intent intent = new Intent();
        intent.putExtra(ConstsSFIB.NET_DOWN_BOOL, true);
        intent.putExtra(ConstsSFIB.ADD_SENT_ENB_BOOL, true);
        intent.putExtra(ConstsSFIB.DIFFICULTY, 5);
        intent.putExtra(ConstsSFIB.STATS_ENB_BOOL, true);
        intent.putExtra(Consts.NAME, "me");
        intent.putExtra(Consts.GAME_LANG, "FRE");
        intent.putExtra(ConstsSFIB.CUMULATIVE_SCORE, "10");
        intent.putExtra(ConstsSFIB.AVG_DIFFICULTY, "2.5");
        intent.putExtra(ConstsSFIB.AVG_GUESS_CNT, "2.5");
        intent.putExtra(ConstsSFIB.AVG_NUM_SKIPS, "2.5");
        intent.putExtra(ConstsSFIB.AVG_NUM_HINTS, "2.5");
        setActivityIntent(intent);

        ActivityGameOptions activity = getActivity();

        // register next activity that needs to be monitored
        Instrumentation.ActivityMonitor statsMonitor =
                getInstrumentation().addMonitor(ActivityPlayerStats.class.getName(), null, false);

        onView(withId(R.id.game_opt_but_player_stats)).perform(click());

        // determine if the next activity was launched in the timeout specified
        Activity statsActivity = getInstrumentation().waitForMonitorWithTimeout
                (statsMonitor, 5000);

        assertNull(statsActivity);

        TestUtils.rotateScreen(activity);

        statsActivity = getInstrumentation().waitForMonitorWithTimeout
                (statsMonitor, 5000);

        onView(withId(R.id.game_opt_but_player_stats)).perform(click());

        assertNull(statsActivity);

    }


}
