package edu.uwf.ksd7.levide_1;

import android.support.test.espresso.web.webdriver.Locator;
import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.getText;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static org.hamcrest.Matchers.containsString;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.web.webdriver.DriverAtoms.webClick;
import static android.support.test.espresso.web.model.Atoms.getCurrentUrl;
import static android.support.test.espresso.web.model.Atoms.getTitle;
import static android.support.test.espresso.Espresso.pressBack;
/**
 * Instrumented testing for ActivityHelpAbout.
 */
public class ActivityHelpAboutTests extends ActivityInstrumentationTestCase2<ActivityHelpAbout> {

    public ActivityHelpAboutTests() {
        super(ActivityHelpAbout.class);
    }

    public void testActivityExists() {
        ActivityHelpAbout activity = getActivity();
        assertNotNull(activity);
    }

    public void testLocalLinks() {
        getActivity();

        // Since the only way to automate WebViews is thru javascript, it must be enabled.
        onWebView(withId(R.id.help_about_view)).forceJavascriptEnabled();

        onWebView().withElement(findElement(Locator.ID, "Title")).
                check(webMatches(getText(),
                        containsString("LeVide Help/About")));

        onWebView().withElement(findElement(Locator.LINK_TEXT, "About LeVide")).
                perform(webClick()).
                check(webMatches(getText(),
                containsString("About LeVide")));

        onWebView().withElement(findElement(Locator.LINK_TEXT, "License")).
                perform(webClick()).
                check(webMatches(getText(),
                        containsString("License")));

        onWebView().withElement(findElement(Locator.LINK_TEXT, "Feedback")).
                perform(webClick()).
                check(webMatches(getText(),
                        containsString("Feedback")));




    }

    public void testRemoteLink() {
        getActivity();

        // Since the only way to automate WebViews is thru javascript, it must be enabled.
        onWebView(withId(R.id.help_about_view)).forceJavascriptEnabled();

        onWebView().withElement(findElement(Locator.ID, "GNU")).
                perform(webClick()).
                // Now we're on a new page, but our WebInteraction doesn't know that, you need to clear
                        // its state or point it at a new Element or you'll get an exception.
                        reset().
                check(webMatches(getTitle(), containsString("GNU")));

    }

    // To test, need to have program on the emulator that supports writing an email.
    public void testEmailLink() {
        getActivity();

        // Since the only way to automate WebViews is thru javascript, it must be enabled.
        onWebView(withId(R.id.help_about_view)).forceJavascriptEnabled();

        onWebView().withElement(findElement(Locator.ID, "email")).
                perform(webClick()).
                // Now we're on a new page, but our WebInteraction doesn't know that, you need to clear
                // its state or point it at a new Element or you'll get an exception.
                        reset();

    }

}