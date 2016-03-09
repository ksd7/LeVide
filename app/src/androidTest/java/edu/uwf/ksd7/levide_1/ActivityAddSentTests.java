package edu.uwf.ksd7.levide_1;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.action.ViewActions;
import android.test.ActivityInstrumentationTestCase2;

import edu.uwf.ksd7.levide_1.games.sfib.ActivityAddSent;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Instrumented testing for ActivityAddSent
 */
public class ActivityAddSentTests extends ActivityInstrumentationTestCase2<ActivityAddSent> {

    String engGood = "This is a test string.";
    String forGood = "Ceci est une chaîne de test.";
    String engEmpty = "";
    String forEmpty = "";
    String longStr = "Early theories on primary language development in infants were proposed" +
            " by pioneers in the field such as B.F. Skinner and Noam Chomsky, espousing " +
            "behaviorism and universal grammar respectively.  Behaviorism explained language " +
            "development as a result of positive or negative reinforcement of words or phrases, " +
            "i.e. baby asks for bottle.  Although this provided an explanation for early " +
            "developmental phases, it did not present a complete picture.  Universal grammar " +
            "went further to suggest that language concepts such as nouns and verbs were " +
            "intrinsically part of human biology.   Modern theories by constructivists " +
            "rejected biological grammar and proposed that language development was via a " +
            "gradual recognition of speech patterns through cognitive processing " +
            "(Lemetyinen, 2012).  Leveraging constructivist research which demonstrated a " +
            "universal cognitive mechanism to guide first language development, SLA postulated" +
            " that processes involved in acquiring a second language were similar, i.e. L1=L2." +
            "  More modern SLA theories adopted a model of a language processor in the brain" +
            " that sorts generalizations derived from L1 (Mansouri, 2007).  A chief SLA theory," +
            " the Input Hypothesis, posits that humans develop second language skills exclusively" +
            " through comprehension of message input within a particular context.  If the L2" +
            " message gets through an affective filter, the brain sorts and retains the information." +
            "  Therefore, an abundance of comprehensible input in L2 is required to achieve" +
            " fluency (Krashen, 1985).";
    String backStr = "\\www.yahoo.com\\this\\test:\u000B\u0000\u0001";

    public ActivityAddSentTests() {
        super(ActivityAddSent.class);
    }

    public void testActivityExists() {
        ActivityAddSent activity = getActivity();
        assertNotNull(activity);
    }

    public void testGoodSentBoth() {
        getActivity();

        //////////////////////////////////////////////
        // Input good sentences and press submit
        //////////////////////////////////////////////
        onView(withId(R.id.add_sent_but_sent_eng)).perform(click());
        onView(withId(R.id.add_sent_eng_sent)).perform(typeText(engGood));
        onView(withId(R.id.add_sent_but_sent_for)).perform(click());
        onView(withId(R.id.add_sent_for_sent)).perform(replaceText(forGood));
        onView(withId(R.id.add_sent_but_submit)).perform(click());

        boolean thrown = false;
        // Confirm the dialog loads and click yes to submit
        try {
            onView(withText("Ready to submit?")).
                    inRoot(withDecorView(not(is(getActivity().getWindow().
                            getDecorView())))).check(matches(isDisplayed()));
            onView(withText("Yes")).perform(click());
        } catch (NoMatchingViewException e) {
            //View is not in hierarchy
            thrown = true;
        }
        assertFalse(thrown);
        assertEquals(engGood, getActivity().getEngResult());
        assertEquals(forGood, getActivity().getForResult());
    }

    public void testShortEng() {
        getActivity();

        //////////////////////////////////////////////
        // Input empty English sentence and press submit
        //////////////////////////////////////////////
        onView(withId(R.id.add_sent_but_sent_eng)).perform(click());
        onView(withId(R.id.add_sent_eng_sent)).perform(typeText(engEmpty));
        onView(withId(R.id.add_sent_but_sent_for)).perform(click());
        onView(withId(R.id.add_sent_for_sent)).perform(replaceText(forGood));
        onView(withId(R.id.add_sent_but_submit)).perform(click());

        boolean thrown = false;
        // Confirm the dialog loads and click yes to submit
        try {
            onView(withText("Ready to submit?")).
                    inRoot(withDecorView(not(is(getActivity().getWindow().
                            getDecorView())))).check(matches(isDisplayed()));
            onView(withText("Yes")).perform(click());

        } catch (NoMatchingViewException e) {
            //View is not in hierarchy
            thrown = true;
        }
        assertFalse(thrown);
        thrown = false;
        try {
            onView(withText(ActivityAddSent.ENG_TOO_SHORT_ERR)).
                    inRoot(withDecorView(not(is(getActivity().getWindow().
                            getDecorView())))).check(matches(isDisplayed()));
        } catch (NoMatchingViewException e) {
            //View is not in hierarchy
            thrown = true;
        }
        assertFalse(thrown);
    }

    public void testShortFor() {
        getActivity();

        //////////////////////////////////////////////
        // Input empty foreign sentence and press submit
        //////////////////////////////////////////////
        onView(withId(R.id.add_sent_but_sent_eng)).perform(click());
        onView(withId(R.id.add_sent_eng_sent)).perform(typeText(engGood));
        onView(withId(R.id.add_sent_but_sent_for)).perform(click());
        onView(withId(R.id.add_sent_for_sent)).perform(replaceText(forEmpty));
        onView(withId(R.id.add_sent_but_submit)).perform(click());
        boolean thrown = false;
        // Confirm the dialog loads and click yes to submit
        try {
            onView(withText("Ready to submit?")).
                    inRoot(withDecorView(not(is(getActivity().getWindow().
                            getDecorView())))).check(matches(isDisplayed()));
            onView(withText("Yes")).perform(click());
        } catch (NoMatchingViewException e) {
            //View is not in hierarchy
            thrown = true;
        }
        assertFalse(thrown);
        thrown = false;
        try {
            onView(withText(ActivityAddSent.FOR_TOO_SHORT_ERR)).
                    inRoot(withDecorView(not(is(getActivity().getWindow().
                            getDecorView())))).check(matches(isDisplayed()));
        } catch (NoMatchingViewException e) {
            //View is not in hierarchy
            thrown = true;
        }
        assertFalse(thrown);
    }

    public void testLongEng() {
        getActivity();

        //////////////////////////////////////////////
        // Input long English and press submit
        //////////////////////////////////////////////
        onView(withId(R.id.add_sent_but_sent_eng)).perform(click());
        onView(withId(R.id.add_sent_eng_sent)).perform(replaceText(longStr));
        onView(withId(R.id.add_sent_but_sent_for)).perform(scrollTo(), click());
        onView(withId(R.id.add_sent_but_sent_for)).perform(click());
        onView(withId(R.id.add_sent_for_sent)).perform(replaceText(forGood));
        onView(withId(R.id.add_sent_but_submit)).perform(click());
        boolean thrown = false;
        // Confirm the dialog loads and click yes to submit
        try {
            onView(withText("Ready to submit?")).
                    inRoot(withDecorView(not(is(getActivity().getWindow().
                            getDecorView())))).check(matches(isDisplayed()));
            onView(withText("Yes")).perform(click());

        } catch (NoMatchingViewException e) {
            //View is not in hierarchy
            thrown = true;
        }
        assertFalse(thrown);
        thrown = false;
        try {
            onView(withText(ActivityAddSent.ENG_TOO_LONG_ERR)).
                    inRoot(withDecorView(not(is(getActivity().getWindow().
                            getDecorView())))).check(matches(isDisplayed()));
        } catch (NoMatchingViewException e) {
            //View is not in hierarchy
            thrown = true;
        }
        assertFalse(thrown);
    }

    public void testLongFor() {
        getActivity();

        //////////////////////////////////////////////
        // Input long foreign and press submit
        //////////////////////////////////////////////
        onView(withId(R.id.add_sent_but_sent_eng)).perform(click());
        onView(withId(R.id.add_sent_eng_sent)).perform(typeText(engGood));
        onView(withId(R.id.add_sent_but_sent_for)).perform(click());
        onView(withId(R.id.add_sent_for_sent)).perform(replaceText(longStr));
        onView(withId(R.id.add_sent_but_submit)).perform(click());
        boolean thrown = false;
        // Confirm the dialog loads and click yes to submit
        try {
            onView(withText("Ready to submit?")).
                    inRoot(withDecorView(not(is(getActivity().getWindow().
                            getDecorView())))).check(matches(isDisplayed()));
            onView(withText("Yes")).perform(click());

        } catch (NoMatchingViewException e) {
            //View is not in hierarchy
            thrown = true;
        }
        assertFalse(thrown);
        thrown = false;
        try {
            onView(withText(ActivityAddSent.FOR_TOO_LONG_ERR)).
                    inRoot(withDecorView(not(is(getActivity().getWindow().
                            getDecorView())))).check(matches(isDisplayed()));
        } catch (NoMatchingViewException e) {
            //View is not in hierarchy
            thrown = true;
        }
        assertFalse(thrown);
    }

    public void testNoBackslash() {
        getActivity();

        //////////////////////////////////////////////
        // Input "binary" data and submit
        //////////////////////////////////////////////
        onView(withId(R.id.add_sent_but_sent_eng)).perform(click());
        onView(withId(R.id.add_sent_eng_sent)).perform(replaceText(backStr));
        onView(withId(R.id.add_sent_but_sent_for)).perform(click());
        onView(withId(R.id.add_sent_for_sent)).perform(replaceText(backStr));
        onView(withId(R.id.add_sent_but_submit)).perform(click());
        boolean thrown = false;
        // Confirm the dialog loads and click yes to submit
        try {
            onView(withText("Ready to submit?")).
                    inRoot(withDecorView(not(is(getActivity().getWindow().
                            getDecorView())))).check(matches(isDisplayed()));
            onView(withText("Yes")).perform(click());
        } catch (NoMatchingViewException e) {
            //View is not in hierarchy
            thrown = true;
        }
        assertFalse(thrown);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thrown = false;
        try {
            onView(withText(ActivityAddSent.BACKSLASH_ERR)).
                    inRoot(withDecorView(not(is(getActivity().getWindow().
                            getDecorView())))).check(matches(isDisplayed()));
        } catch (NoMatchingViewException e) {
            //View is not in hierarchy
            thrown = true;
        }
        assertFalse(thrown);
    }

    public void testConfirmNo() {
        getActivity();

        //////////////////////////////////////////////
        // Input sentences and select no in confirmation dialog
        //////////////////////////////////////////////
        onView(withId(R.id.add_sent_but_sent_eng)).perform(click());
        onView(withId(R.id.add_sent_eng_sent)).perform(replaceText(engGood));
        onView(withId(R.id.add_sent_but_sent_for)).perform(click());
        onView(withId(R.id.add_sent_for_sent)).perform(replaceText(forGood));
        onView(withId(R.id.add_sent_but_submit)).perform(click());
        boolean thrown = false;
        // Confirm the dialog loads and click yes to submit
        try {
            onView(withText("Ready to submit?")).
                    inRoot(withDecorView(not(is(getActivity().getWindow().
                            getDecorView())))).check(matches(isDisplayed()));
            onView(withText("No")).perform(click());
         } catch (NoMatchingViewException e) {
            //View is not in hierarchy
            thrown = true;
        }
        assertFalse(thrown);
    }

    public void testToggleKeyboard() {
        getActivity();

        //////////////////////////////////////////////
        // Input sentences and select no in confirmation dialog
        //////////////////////////////////////////////
        boolean thrown = false;
        try {
            onView(withId(R.id.add_sent_but_sent_eng)).perform(click());
            onView(withId(R.id.add_sent_eng_sent)).perform(typeText(engGood), ViewActions.closeSoftKeyboard());
            onView(withId(R.id.add_sent_but_toggle_keyboard)).perform(click());
            onView(withId(R.id.add_sent_eng_sent)).perform(typeText(engGood), ViewActions.closeSoftKeyboard());
            onView(withId(R.id.add_sent_but_toggle_keyboard)).perform(click());
            onView(withId(R.id.add_sent_eng_sent)).perform(typeText(engGood), ViewActions.closeSoftKeyboard());
            onView(withId(R.id.add_sent_but_toggle_keyboard)).perform(click());
            onView(withId(R.id.add_sent_eng_sent)).perform(typeText(engGood), ViewActions.closeSoftKeyboard());
        } catch (NoMatchingViewException e) {
            //View is not in hierarchy
            thrown = true;
        }
        assertFalse(thrown);
    }

    public void testRotation() {
        ActivityAddSent activity = getActivity();

        onView(withId(R.id.add_sent_but_sent_eng)).perform(click());
        onView(withId(R.id.add_sent_eng_sent)).perform(typeText(engGood));
        onView(withId(R.id.add_sent_but_sent_for)).perform(click());
        onView(withId(R.id.add_sent_for_sent)).perform(replaceText(forGood));

        TestUtils.rotateScreen(activity);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Ensure that it keeps the content across rotation
        onView(withId(R.id.add_sent_eng_sent)).check(matches(withText(engGood)));
        onView(withId(R.id.add_sent_for_sent)).check(matches(withText(forGood)));

        TestUtils.rotateScreen(activity);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Ensure that it keeps the content across rotation
        onView(withId(R.id.add_sent_eng_sent)).check(matches(withText(engGood)));
        onView(withId(R.id.add_sent_for_sent)).check(matches(withText(forGood)));

        onView(withId(R.id.add_sent_but_submit)).perform(click());

        boolean thrown = false;
        // Confirm the dialog loads and click yes to submit
        try {
            onView(withText("Ready to submit?")).
                    inRoot(withDecorView(not(is(getActivity().getWindow().
                            getDecorView())))).check(matches(isDisplayed()));
            onView(withText("Yes")).perform(click());
        } catch (NoMatchingViewException e) {
            //View is not in hierarchy
            thrown = true;
        }
        assertFalse(thrown);
        assertEquals(engGood, getActivity().getEngResult());
        assertEquals(forGood, getActivity().getForResult());
    }
}
