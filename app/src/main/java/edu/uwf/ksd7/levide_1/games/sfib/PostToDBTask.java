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

import android.os.AsyncTask;
//import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * PostToDBTask is responsible for communication with a remote database via an AsyncTask
 * to post game content.
 * Android OS requires a separate task to handle network activity.
 * <> arguments are in the form of <params type for onPreExecute, progress type
 * for onProgressUpdate and Progress, result type returned from doInBackground
 * and received by onPostExecute
 */
public class PostToDBTask extends AsyncTask<String, Void, String> {
    //private static final String TAG = "PostToDBTask";
    String mPostUrl;

    /**
     * delegate for which processFinish will be called in onPostExecute
     * to return results of asynchronous task back to creator.  Creator
     * will initialize this as part of setting up this task for their needs.
     */
    public AsyncResponse delegate = null;

    private static final String POST_MODE = "POST";
    private static final String CHAR_SET = "UTF-8";

    /**
     * Constructor
     * @param url The URL to use to connect to for this task to perform the
     *            desired posting work.
     */
    public PostToDBTask(String url){
        mPostUrl = url;
    }

    // only method that runs on a background thread.
    // It executes immediately after onPreExecute completes.
    // The return value here is passed into the onPostExecute method.
    @Override
    protected String doInBackground(String... params) {
        if(null==params) return null;
        String mode = params[0];
        String jsonStr;

        switch (mode) {
            case ConstsSFIB.POST_FLAG_SENT_TASK:
                String engId = params[1];
                String lang  = params[2];
                try {
                    URL url = new URL(mPostUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod(POST_MODE);
                    httpURLConnection.setDoOutput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter =
                            new BufferedWriter(new OutputStreamWriter(OS, CHAR_SET));
                    String data = URLEncoder.encode(ConstsSFIB.POST_FLAG_ENG_ID, CHAR_SET) + "=" +
                            URLEncoder.encode(engId, CHAR_SET)+ "&" +
                            URLEncoder.encode(ConstsSFIB.POST_FLAG_LANG, CHAR_SET) + "=" +
                            URLEncoder.encode(lang, CHAR_SET);
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();
                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((jsonStr = bufferedReader.readLine())!=null) {
                        stringBuilder.append(jsonStr);
                        stringBuilder.append("\n");
                    }
                    bufferedReader.close();
                    IS.close();
                    httpURLConnection.disconnect();
                    return stringBuilder.toString().trim(); // trim the white spaces from result
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case ConstsSFIB.POST_ADD_SENT_TASK:
                String engSent = params[1];
                String forSent = params[2];
                lang = params[3];
                try {
                    URL url = new URL(mPostUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod(POST_MODE);
                    httpURLConnection.setDoOutput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter =
                            new BufferedWriter(new OutputStreamWriter(OS, CHAR_SET));
                    String data = URLEncoder.encode(ConstsSFIB.POST_ADD_ENG_SENT, CHAR_SET) + "=" +
                            URLEncoder.encode(engSent, CHAR_SET)+ "&" +
                            URLEncoder.encode(ConstsSFIB.POST_ADD_FOR_SENT, CHAR_SET) + "=" +
                            URLEncoder.encode(forSent, CHAR_SET) + "&" +
                            URLEncoder.encode(ConstsSFIB.POST_ADD_LANG, CHAR_SET) + "=" +
                            URLEncoder.encode(lang, CHAR_SET);
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();

                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((jsonStr = bufferedReader.readLine())!=null) {
                        stringBuilder.append(jsonStr);
                        stringBuilder.append("\n");
                    }
                    bufferedReader.close();
                    IS.close();
                    httpURLConnection.disconnect();
                    return stringBuilder.toString().trim(); // trim the white spaces from result
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case ConstsSFIB.POST_STATS_TASK:
                // in order of how they are in DB
                String email        = params[1];
                lang                = params[2];
                String sigmaDiff    = params[3];
                String sigmaGuess   = params[4];
                String sigmaHint    = params[5];
                String sigmaSkip    = params[6];
                String numSubmit    = params[7];
                String cumScore     = params[8];
                String avgDiff      = params[9];
                String avgGuess     = params[10];
                String avgSkip      = params[11];
                String avgHint      = params[12];
                String addCnt       = params[13];
                try {
                    //Log.d(TAG, "Start of post_stats with URL = " + postUrl);
                    URL url = new URL(mPostUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod(POST_MODE);
                    httpURLConnection.setDoOutput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter =
                        new BufferedWriter(new OutputStreamWriter(OS, CHAR_SET));
                    String data =
                        URLEncoder.encode(ConstsSFIB.POST_STATS_EMAIL, CHAR_SET) + "=" +
                        URLEncoder.encode(email, CHAR_SET)+ "&" +
                        URLEncoder.encode(ConstsSFIB.POST_STATS_GAME_ID, CHAR_SET) + "=" +
                        URLEncoder.encode(lang, CHAR_SET) + "&" +
                        URLEncoder.encode(ConstsSFIB.POST_STATS_SIG_DIFF, CHAR_SET) + "=" +
                        URLEncoder.encode(sigmaDiff, CHAR_SET) + "&" +
                        URLEncoder.encode(ConstsSFIB.POST_STATS_SIG_GUESS, CHAR_SET) + "=" +
                        URLEncoder.encode(sigmaGuess, CHAR_SET) + "&" +
                        URLEncoder.encode(ConstsSFIB.POST_STATS_SIG_HINT, CHAR_SET) + "=" +
                        URLEncoder.encode(sigmaHint, CHAR_SET) + "&" +
                        URLEncoder.encode(ConstsSFIB.POST_STATS_SIG_SKIP, CHAR_SET) + "=" +
                        URLEncoder.encode(sigmaSkip, CHAR_SET) + "&" +
                        URLEncoder.encode(ConstsSFIB.POST_STATS_NUM_SUBMIT, CHAR_SET) + "=" +
                        URLEncoder.encode(numSubmit, CHAR_SET) + "&" +
                        URLEncoder.encode(ConstsSFIB.POST_STATS_CUM_SCORE, CHAR_SET) + "=" +
                        URLEncoder.encode(cumScore, CHAR_SET) + "&" +
                        URLEncoder.encode(ConstsSFIB.POST_STATS_AVG_DIFF, CHAR_SET) + "=" +
                        URLEncoder.encode(avgDiff, CHAR_SET) + "&" +
                        URLEncoder.encode(ConstsSFIB.POST_STATS_AVG_GUESS, CHAR_SET) + "=" +
                        URLEncoder.encode(avgGuess, CHAR_SET) + "&" +
                        URLEncoder.encode(ConstsSFIB.POST_STATS_AVG_SKIP, CHAR_SET) + "=" +
                        URLEncoder.encode(avgSkip, CHAR_SET) + "&" +
                        URLEncoder.encode(ConstsSFIB.POST_STATS_AVG_HINT, CHAR_SET) + "=" +
                        URLEncoder.encode(avgHint, CHAR_SET) + "&" +
                        URLEncoder.encode(ConstsSFIB.POST_STATS_ADD_CNT, CHAR_SET) + "=" +
                        URLEncoder.encode(addCnt, CHAR_SET);
                    bufferedWriter.write(data);
                    //Log.d(TAG, "data = " + data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();

                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((jsonStr = bufferedReader.readLine())!=null) {
                        stringBuilder.append(jsonStr);
                        stringBuilder.append("\n");
                    }
                    bufferedReader.close();
                    IS.close();
                    httpURLConnection.disconnect();
                    return stringBuilder.toString().trim(); // trim the white spaces from result
                } catch (IOException e) {
                    e.printStackTrace();
                    //Log.d(TAG, "IO Exception");
                }
                break;
            default:
                throw new IllegalArgumentException();
        }
        return null;
    }

    // this method is used to update the progress bar.  This is invoked from the UI thread
    // when publishProgress(Progress...) is called.
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    // after finishing the background task, the returned result is published back on the UI
    // thread and available in this method
    @Override
    protected void onPostExecute(String s) {
        if(delegate != null) delegate.processFinish(s);
    }
}
