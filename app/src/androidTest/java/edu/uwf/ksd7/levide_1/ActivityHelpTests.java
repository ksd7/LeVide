package edu.uwf.ksd7.levide_1;

import android.support.test.espresso.web.webdriver.Locator;
import android.test.ActivityInstrumentationTestCase2;

import edu.uwf.ksd7.levide_1.games.sfib.ActivityHelp;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.getText;
import static android.support.test.espresso.web.webdriver.DriverAtoms.webClick;
import static org.hamcrest.Matchers.containsString;

/**
 * Instrumented testing for ActivityHelp.
 */
public class ActivityHelpTests extends ActivityInstrumentationTestCase2<ActivityHelp> {

    public ActivityHelpTests() {
        super(ActivityHelp.class);
    }

    public void testActivityExists() {
        ActivityHelp activity = getActivity();
        assertNotNull(activity);
    }

    public void testFeatures() {
        getActivity();

        // Since the only way to automate WebViews is thru javascript, it must be enabled.
        onWebView(withId(R.id.help_view)).forceJavascriptEnabled();

        onWebView().withElement(findElement(Locator.ID, "Title")).
                check(webMatches(getText(),
                        containsString("SFIB Game Help")));

        onWebView().withElement(findElement(Locator.LINK_TEXT, "Game Rules")).
                perform(webClick()).
                check(webMatches(getText(),
                        containsString("Game Rules")));

        onWebView().withElement(findElement(Locator.LINK_TEXT, "Buttons")).
                perform(webClick()).
                check(webMatches(getText(),
                        containsString("Buttons")));

        onWebView().withElement(findElement(Locator.LINK_TEXT, "Game Options")).
                perform(webClick()).
                check(webMatches(getText(),
                        containsString("Game Options")));
    }
}
