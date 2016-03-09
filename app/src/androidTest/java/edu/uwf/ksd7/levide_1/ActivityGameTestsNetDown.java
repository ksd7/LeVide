package edu.uwf.ksd7.levide_1;

import android.content.Intent;
import android.support.test.espresso.NoMatchingViewException;
import android.test.ActivityInstrumentationTestCase2;

import edu.uwf.ksd7.levide_1.games.sfib.ActivityGame;
import edu.uwf.ksd7.levide_1.games.sfib.ActivityGameOptions;
import edu.uwf.ksd7.levide_1.games.sfib.ConstsSFIB;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Instrumented testing for ActivityGame when the network is down.  That causes the game
 * to use default content that is not randomized.  This facilitates testing.
 * Comment most of this out when done with this test
 */
public class ActivityGameTestsNetDown extends
        ActivityInstrumentationTestCase2<ActivityGame> {

    public ActivityGameTestsNetDown () {
        super(ActivityGame.class);
    }

/*
    // Since emulator runs very slowly when you pull ethernet cord on test PC or disable the HW
    // it is easier to set IP's to localhost in ConstSFIB and not enable wamp server
    public void testNetDownFre() {
        Intent intent = new Intent();
        intent.putExtra(Consts.GAME_LANG, "FRE");
        intent.putExtra(Consts.NAME, Consts.TEST_NAME);
        intent.putExtra(Consts.EMAIL, Consts.TEST_EMAIL);
        setActivityIntent(intent);

        ActivityGame activity = getActivity();
        confirmDefaultContentEnglish();
    }

    public void testNetDownSpa() {
        Intent intent = new Intent();
        intent.putExtra(Consts.GAME_LANG, "SPA");
        intent.putExtra(Consts.NAME, Consts.TEST_NAME);
        intent.putExtra(Consts.EMAIL, Consts.TEST_EMAIL);
        setActivityIntent(intent);

        ActivityGame activity = getActivity();
        confirmDefaultContentEnglish();
    }

    // Confirms the correct English sentence loads and exercises
    // the skip and hint buttons
    private void confirmDefaultContentEnglish() {
        // When net is down, default content is loaded, and
        // the sentences are not randomized.  That facilitates testing.
        boolean thrown = false;
        int curIdx = ConstsSFIB.DEFAULT_SIZE - 1;

        // first time
        onView(withId(R.id.game_sfib_hint_button)).perform(click());
        onView(withId(R.id.game_sfib_eng_sent)).check(matches(withText(ConstsSFIB
                .defaultEng[curIdx])));

        if(--curIdx < 0) curIdx = ConstsSFIB.DEFAULT_SIZE - 1;

        // subsequent times, just fetch additional content and see that works
        for(int i = 1; i < ConstsSFIB.DEFAULT_SIZE*2; i++ ) {
            onView(withId(R.id.game_sfib_skip_button)).perform(click());
            thrown = false;
            // Confirm the dialog loads and click yes to skip
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
            onView(withId(R.id.game_sfib_hint_button)).perform(click());
            onView(withId(R.id.game_sfib_eng_sent)).check(matches(withText(ConstsSFIB
                    .defaultEng[curIdx])));
            if(--curIdx < 0) curIdx = ConstsSFIB.DEFAULT_SIZE - 1;
        }

    }
*/

}
