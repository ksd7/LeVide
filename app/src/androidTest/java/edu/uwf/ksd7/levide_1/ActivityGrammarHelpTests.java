package edu.uwf.ksd7.levide_1;

import android.content.Intent;
import android.support.test.espresso.web.webdriver.Locator;
import android.test.ActivityInstrumentationTestCase2;

import edu.uwf.ksd7.levide_1.games.sfib.ActivityGrammarHelp;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.getText;
import static android.support.test.espresso.web.webdriver.DriverAtoms.webClick;
import static org.hamcrest.Matchers.containsString;

/**
 * Instrumented testing for ActivityGrammarHelp.
 */
public class ActivityGrammarHelpTests extends
        ActivityInstrumentationTestCase2<ActivityGrammarHelp> {

    public ActivityGrammarHelpTests() {
        super(ActivityGrammarHelp.class);
    }

    public void testActivityExists() {
        Intent intent = new Intent();
        intent.putExtra(Consts.GAME_LANG, "FRE");
        setActivityIntent(intent);

        ActivityGrammarHelp activity = getActivity();
        assertNotNull(activity);
    }

    public void testFrenchHelp() {
        Intent intent = new Intent();
        intent.putExtra(Consts.GAME_LANG, "FRE");
        setActivityIntent(intent);

        getActivity();

        // Since the only way to automate WebViews is thru javascript, it must be enabled.
        onWebView(withId(R.id.help_grammar_view)).forceJavascriptEnabled();

        onWebView().withElement(findElement(Locator.ID, "Title")).
                check(webMatches(getText(),
                        containsString("French")));

        onWebView().withElement(findElement(Locator.LINK_TEXT, "Nouns")).
                perform(webClick()).
                check(webMatches(getText(),
                        containsString("Nouns")));

        onWebView().withElement(findElement(Locator.LINK_TEXT, "Verbs")).
                perform(webClick()).
                check(webMatches(getText(),
                        containsString("Verbs")));

        onWebView().withElement(findElement(Locator.LINK_TEXT, "Adverbs")).
                perform(webClick()).
                check(webMatches(getText(),
                        containsString("Adverbs")));

        onWebView().withElement(findElement(Locator.LINK_TEXT, "Adjectives")).
                perform(webClick()).
                check(webMatches(getText(),
                        containsString("Adjectives")));

        onWebView().withElement(findElement(Locator.LINK_TEXT, "Articles")).
                perform(webClick()).
                check(webMatches(getText(),
                        containsString("Articles")));
    }

    public void testSpanishHelp() {
        Intent intent = new Intent();
        intent.putExtra(Consts.GAME_LANG, "SPA");
        setActivityIntent(intent);

        getActivity();

        // Since the only way to automate WebViews is thru javascript, it must be enabled.
        onWebView(withId(R.id.help_grammar_view)).forceJavascriptEnabled();

        onWebView().withElement(findElement(Locator.ID, "Title")).
                check(webMatches(getText(),
                        containsString("Spanish")));

        onWebView().withElement(findElement(Locator.LINK_TEXT, "Nouns")).
                perform(webClick()).
                check(webMatches(getText(),
                        containsString("Nouns")));

        onWebView().withElement(findElement(Locator.LINK_TEXT, "Verbs")).
                perform(webClick()).
                check(webMatches(getText(),
                        containsString("Verbs")));

        onWebView().withElement(findElement(Locator.LINK_TEXT, "Adverbs")).
                perform(webClick()).
                check(webMatches(getText(),
                        containsString("Adverbs")));

        onWebView().withElement(findElement(Locator.LINK_TEXT, "Adjectives")).
                perform(webClick()).
                check(webMatches(getText(),
                        containsString("Adjectives")));

        onWebView().withElement(findElement(Locator.LINK_TEXT, "Articles")).
                perform(webClick()).
                check(webMatches(getText(),
                        containsString("Articles")));

    }


}
