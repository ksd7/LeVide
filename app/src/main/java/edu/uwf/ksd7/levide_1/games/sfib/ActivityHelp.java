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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.InputStream;

import edu.uwf.ksd7.levide_1.R;
import edu.uwf.ksd7.levide_1.Utils;

/**
 * ActivityHelp provides helpful instruction on how to play the SFIB game.  Details are
 * presented using a WebView with links at the top for easier navigation, and instruction
 * content is contained in a raw HTML file.
 */
public class ActivityHelp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sfib_help);

        WebView help = (WebView)findViewById(R.id.help_view);
        help.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        InputStream inputStream = getResources().openRawResource(R.raw.help_game_sfib);
        String helpText = Utils.readRawTextFile(inputStream);
        help.loadData(helpText, "text/html; charset=utf-8", "utf-8");
    }
}
