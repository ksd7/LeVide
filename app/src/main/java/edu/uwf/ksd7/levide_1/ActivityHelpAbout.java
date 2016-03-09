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
package edu.uwf.ksd7.levide_1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.InputStream;

/**
 * ActivityHelpAbout presents a WebView to display help content via a
 * raw html file.  Links are provided at the top of the screen to
 * facilitate navigation.  Content includes an overall description of the app,
 * licensing information, and contact information for technical support.
 */
public class ActivityHelpAbout extends AppCompatActivity {
    // For debug
    //private static final String TAG = "ActivityHelpAbout";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levide_help_about);

        WebView help = (WebView)findViewById(R.id.help_about_view);
        help.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("mailto:")) {
                    Intent sendIntent =
                        new Intent(Intent.ACTION_SENDTO, Uri.parse(Consts.TECH_SUPPORT_EMAIL));
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, "LeVide feedback");
                    startActivity(Intent.createChooser(sendIntent,"Choose an Email client :"));
                    return true;
                } else {
                    view.loadUrl(url);
                }
                return true;
            }
        });

        InputStream inputStream = getResources().openRawResource(R.raw.help_about);
        String helpText = Utils.readRawTextFile(inputStream);
        help.loadData(helpText, "text/html; charset=utf-8", "utf-8");
    }
}
