package edu.uwf.ksd7.levide_1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.SeekBar;

/*
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.times;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
*/
import edu.uwf.ksd7.levide_1.games.sfib.ActivityDifficulty;
import edu.uwf.ksd7.levide_1.games.sfib.ConstsSFIB;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.support.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Matcher;

/**
 * Instrumented testing for ActivityDifficulty.
 */
public class ActivityDifficultyTests extends ActivityInstrumentationTestCase2<ActivityDifficulty> {

    public ActivityDifficultyTests() {
        super(ActivityDifficulty.class);
    }

    public void testActivityExists() {
        Intent intent = new Intent();
        intent.putExtra(ConstsSFIB.DIFFICULTY, 3);
        setActivityIntent(intent);

        ActivityDifficulty activity = getActivity();
        assertNotNull(activity);
    }

    public void testDiffHi() {
        Intent intent = new Intent();
        intent.putExtra(ConstsSFIB.DIFFICULTY, 3);
        setActivityIntent(intent);

        ActivityDifficulty activity = getActivity();
        onView(withId( R.id.diffSlider)).perform(setProgress(4)); // 0 to 4 is range
        onView(withId(R.id.game_sfib_opt_diff_submit_button)).perform(click());
        assertEquals("5", activity.getDiffResult());
    }

    public void testDiffMedHi() {
        Intent intent = new Intent();
        intent.putExtra(ConstsSFIB.DIFFICULTY, 3);
        setActivityIntent(intent);

        ActivityDifficulty activity = getActivity();
        onView(withId( R.id.diffSlider)).perform(setProgress(3)); // 0 to 4 is range
        onView(withId(R.id.game_sfib_opt_diff_submit_button)).perform(click());
        assertEquals("4", activity.getDiffResult());
    }

    public void testDiffMed() {
        Intent intent = new Intent();
        intent.putExtra(ConstsSFIB.DIFFICULTY, 3);
        setActivityIntent(intent);

        ActivityDifficulty activity = getActivity();
        onView(withId( R.id.diffSlider)).perform(setProgress(2)); // 0 to 4 is range
        onView(withId(R.id.game_sfib_opt_diff_submit_button)).perform(click());
        assertEquals("3", activity.getDiffResult());
    }

    public void testDiffMedLo() {
        Intent intent = new Intent();
        intent.putExtra(ConstsSFIB.DIFFICULTY, 3);
        setActivityIntent(intent);

        ActivityDifficulty activity = getActivity();
        onView(withId( R.id.diffSlider)).perform(setProgress(1)); // 0 to 4 is range
        onView(withId(R.id.game_sfib_opt_diff_submit_button)).perform(click());
        assertEquals("2", activity.getDiffResult());
    }

    public void testDiffLo() {
        Intent intent = new Intent();
        intent.putExtra(ConstsSFIB.DIFFICULTY, 3);
        setActivityIntent(intent);

        ActivityDifficulty activity = getActivity();
        onView(withId( R.id.diffSlider)).perform(setProgress(0)); // 0 to 4 is range
        onView(withId(R.id.game_sfib_opt_diff_submit_button)).perform(click());
        assertEquals("1", activity.getDiffResult());
    }

    public void testRotate() {
        Intent intent = new Intent();
        intent.putExtra(ConstsSFIB.DIFFICULTY, 3);
        setActivityIntent(intent);
        ActivityDifficulty activity = getActivity();

        onView(withId( R.id.diffSlider)).perform(setProgress(4)); // 0 to 4 is range

        TestUtils.rotateScreen(activity);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.game_sfib_opt_diff_submit_button)).perform(click());
        assertEquals("5", activity.getDiffResult());
    }

    public static ViewAction setProgress(final int progress) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                SeekBar seekBar = (SeekBar) view;
                seekBar.setProgress(progress);
            }
            @Override
            public String getDescription() {
                return "Set a progress on a SeekBar";
            }
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(SeekBar.class);
            }
        };
    }
}
