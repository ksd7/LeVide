package edu.uwf.ksd7.levide_1;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import edu.uwf.ksd7.levide_1.games.sfib.ActivityPlayerStats;
import edu.uwf.ksd7.levide_1.games.sfib.ConstsSFIB;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented testing for ActivityPlayerStats.
 */
public class ActivityPlayerStatsTests extends ActivityInstrumentationTestCase2<ActivityPlayerStats> {

    public ActivityPlayerStatsTests() {
        super(ActivityPlayerStats.class);
    }

    public void testActivityExists() {
        // Make intent that is to be passed into the Activity
        String name = "me", game = "FRE", cumScore = "12345", avgDiff = "1.1",
                avgGuess="2.2", avgSkip="3.3", avgHint="4.4";
        Intent intent = new Intent();
        intent.putExtra(Consts.NAME, name);
        intent.putExtra(Consts.GAME_LANG, game);
        intent.putExtra(ConstsSFIB.CUMULATIVE_SCORE, cumScore);
        intent.putExtra(ConstsSFIB.AVG_DIFFICULTY, avgDiff);
        intent.putExtra(ConstsSFIB.AVG_GUESS_CNT, avgGuess);
        intent.putExtra(ConstsSFIB.AVG_NUM_SKIPS, avgSkip);
        intent.putExtra(ConstsSFIB.AVG_NUM_HINTS, avgHint);
        setActivityIntent(intent);
        ActivityPlayerStats activity = getActivity();
        assertNotNull(activity);
    }

    public void testFeatures() {
        // Make intent that is to be passed into the Activity
        String name = "me", game = "FRE", cumScore = "12345", avgDiff = "1.1",
                avgGuess="2.2", avgSkip="3.3", avgHint="4.4";
        Intent intent = new Intent();
        intent.putExtra(Consts.NAME, name);
        intent.putExtra(Consts.GAME_LANG, game);
        intent.putExtra(ConstsSFIB.CUMULATIVE_SCORE, cumScore);
        intent.putExtra(ConstsSFIB.AVG_DIFFICULTY, avgDiff);
        intent.putExtra(ConstsSFIB.AVG_GUESS_CNT, avgGuess);
        intent.putExtra(ConstsSFIB.AVG_NUM_SKIPS, avgSkip);
        intent.putExtra(ConstsSFIB.AVG_NUM_HINTS, avgHint);
        setActivityIntent(intent);

        ActivityPlayerStats activity = getActivity();
        assertNotNull(activity);

        onView(withId(R.id.stats_player)).check(matches(withText(name)));
        onView(withId(R.id.stats_player_label)).check(matches(withText("SFIB " + game)));
        onView(withId(R.id.stats_cum_score)).check(matches(withText(cumScore)));
        onView(withId(R.id.stats_avg_diff)).check(matches(withText(avgDiff)));
        onView(withId(R.id.stats_avg_guess)).check(matches(withText(avgGuess)));
        onView(withId(R.id.stats_avg_skip)).check(matches(withText(avgSkip)));
        onView(withId(R.id.stats_avg_hint)).check(matches(withText(avgHint)));
    }
}
