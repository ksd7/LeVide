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
package edu.uwf.ksd7.levide_1.games.sfib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.InputStream;

import edu.uwf.ksd7.levide_1.Consts;
import edu.uwf.ksd7.levide_1.R;
import edu.uwf.ksd7.levide_1.Utils;

/**
 * ActivityGrammarHelp presents a WebView to display help content via a
 * raw html file.  Links are provided at the top of the screen to
 * facilitate navigation.  Content includes syntax and grammar help
 * for the specific sfib language that is loaded.
 */
public class ActivityGrammarHelp extends AppCompatActivity {
    // For debug
    //private static final String TAG = "ActivityGrammarHelp";
    String helpText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sfib_grammar_help);

        // Get the intent extra to determine which SFIB game version is relevant
        Intent intent = getIntent();
        String lang = intent.getStringExtra(Consts.GAME_LANG);
        //Log.d(TAG, lang);

        WebView help = (WebView)findViewById(R.id.help_grammar_view);
        help.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        InputStream inputStream;
        if(lang.equals(UtilsSFIB.SFIBGameLang.FRE.toString())) {
            inputStream = getResources().openRawResource(R.raw.help_grammar_fre);
        } else if (lang.equals(UtilsSFIB.SFIBGameLang.SPA.toString())) {
            inputStream = getResources().openRawResource(R.raw.help_grammar_spa);
        } else { // Throw exception
            throw new IllegalArgumentException();
        }

        helpText = Utils.readRawTextFile(inputStream);
        help.loadData(helpText, "text/html; charset=utf-8", "utf-8");
    }
}
