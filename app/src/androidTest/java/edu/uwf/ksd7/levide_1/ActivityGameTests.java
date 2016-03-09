package edu.uwf.ksd7.levide_1;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.IBinder;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.Root;
import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.os.IBinder;
import android.support.test.espresso.Root;
import android.view.WindowManager;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import edu.uwf.ksd7.levide_1.games.sfib.ActivityAddSent;
import edu.uwf.ksd7.levide_1.games.sfib.ActivityGame;
import edu.uwf.ksd7.levide_1.games.sfib.ActivityGameOptions;
import edu.uwf.ksd7.levide_1.games.sfib.ActivityPlayerStats;
import edu.uwf.ksd7.levide_1.games.sfib.ConstsSFIB;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Instrumented testing for ActivityGame.
 */
public class ActivityGameTests extends
        ActivityInstrumentationTestCase2<ActivityGame> {

    public ActivityGameTests() {
        super(ActivityGame.class);
    }

    public void testActivityExists() {
        Intent intent = new Intent();
        intent.putExtra(Consts.GAME_LANG, "FRE");
        intent.putExtra(Consts.NAME, Consts.TEST_NAME);
        intent.putExtra(Consts.EMAIL, Consts.TEST_EMAIL);
        setActivityIntent(intent);

        ActivityGame activity = getActivity();
        assertNotNull(activity);
    }

    public void testOptionsButton() {
        Intent intent = new Intent();
        intent.putExtra(Consts.GAME_LANG, "FRE");
        intent.putExtra(Consts.NAME, Consts.TEST_NAME);
        intent.putExtra(Consts.EMAIL, Consts.TEST_EMAIL);
        setActivityIntent(intent);

        ActivityGame activity = getActivity();

        // register next activity that needs to be monitored
        Instrumentation.ActivityMonitor optsMonitor =
                getInstrumentation().addMonitor(ActivityGameOptions.class.getName(), null, false);

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.game_sfib_options_button)).perform(click());

        // determine if the next activity was launched in the timeout specified
        Activity optsActivity = getInstrumentation().waitForMonitorWithTimeout
                (optsMonitor, 5000);

        assertNotNull(activity);
        assertEquals(optsActivity.getClass().getSimpleName(), ActivityGameOptions.class
                .getSimpleName());
        optsActivity.finish();
    }

    public void testHintButton() {
        Intent intent = new Intent();
        intent.putExtra(Consts.GAME_LANG, "FRE");
        intent.putExtra(Consts.NAME, Consts.TEST_NAME);
        intent.putExtra(Consts.EMAIL, Consts.TEST_EMAIL);
        setActivityIntent(intent);

        ActivityGame activity = getActivity();

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.game_sfib_toggle)).perform(click());

        TextView text = (TextView) activity.findViewById(R.id.game_sfib_for_sent);
        String forSent = text.getText().toString();
        String[] words = forSent.split(" ");

        int blankNum = 0;
        for(int i = 0; i < words.length; i++) {
            if(words[i].contains("_.")) {
                blankNum = words[i].length()/2;
            }
        }

        // Press hint to get English sentence
        onView(withId(R.id.game_sfib_hint_button)).perform(click());

        TextView eng = (TextView) activity.findViewById(R.id.game_sfib_eng_sent);
        String engSent = eng.getText().toString();
        assertTrue(engSent.length() > 1);  // test that there is an English sentence

        // Press Hint to reveal all of the letters
        for(int i = 0; i < blankNum; i++) {
            onView(withId(R.id.game_sfib_hint_button)).perform(click());
        }

        EditText ans = (EditText) activity.findViewById(R.id.game_sfib_ans);
        String ansStr = ans.getText().toString();
        assertEquals(ansStr.length(), blankNum); // test that all the letters are revealed

        // Press submit
        onView(withId(R.id.game_sfib_submit_button)).perform(click());

        onView(withId(R.id.game_sfib_toggle)).perform(click());

        TextView score = (TextView) activity.findViewById(R.id.game_sfib_score);
        String scoreStr = score.getText().toString();
        assertEquals(0, Integer.parseInt(scoreStr));

        ///////////////////////////////////

        text = (TextView) activity.findViewById(R.id.game_sfib_for_sent);
        forSent = text.getText().toString();
        words = forSent.split(" ");

        blankNum = 0;
        for(int i = 0; i < words.length; i++) {
            if(words[i].contains("_.")) {
                blankNum = words[i].length()/2;
            }
        }

        // Press hint to get English sentence
        onView(withId(R.id.game_sfib_hint_button)).perform(click());

        eng = (TextView) activity.findViewById(R.id.game_sfib_eng_sent);
        engSent = eng.getText().toString();
        assertTrue(engSent.length() > 1);  // test that there is an English sentence

         // Press Hint to reveal all of the letters
        for(int i = 0; i < blankNum; i++) {
             onView(withId(R.id.game_sfib_hint_button)).perform(click());
        }

        ans = (EditText) activity.findViewById(R.id.game_sfib_ans);
        ansStr = ans.getText().toString();
        assertEquals(ansStr.length(), blankNum); // test that all the letters are revealed

        TestUtils.rotateScreen(activity);
        TestUtils.rotateScreen(activity);

        // Press submit
        onView(withId(R.id.game_sfib_submit_button)).perform(click());

        onView(withId(R.id.game_sfib_toggle)).perform(click());

        score = (TextView) activity.findViewById(R.id.game_sfib_score);
        scoreStr = score.getText().toString();
        assertEquals(0, Integer.parseInt(scoreStr));

    }

}
