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

import android.app.ProgressDialog;
import android.content.Context;
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
 * FetchFromDBTask is responsible for communication with the remote game database
 * via an AsyncTask to extract game content.
 * Android OS requires a separate task to handle network activity.
 * <> arguments are in the form of <params type for onPreExecute, progress type
 * for onProgressUpdate and Progress, result type returned from doInBackground
 * and received by onPostExecute
 */
public class FetchFromDBTask extends AsyncTask<String, Integer, String> {
    //private static final String TAG = "FetchFromDBTask"; // For debug

    private static final String POST_MODE = "POST";
    private static final String CHAR_SET = "UTF-8";
    private static final int TIMEOUT_MS = 5000; // 5 sec

    String mJsonUrl;
    String mJsonStr;
    // The caller sets this variable to indicate where to return results
    public AsyncResponse delegate = null;
    ProgressDialog mProgressDialog;
    Context context;

    /**
     * Constructor
     * @param ctx A context passed from the instantiator of the task, needed to allow
     *            progress updates in the UI thread.
     * @param url Specifies the specific URL upon which HTTP communication will occur.
     */
    public FetchFromDBTask(Context ctx, String url){
        this.context = ctx;
        mJsonUrl = url;
    }

    // called by UI thread when "execute" called on async task
    @Override
    protected void onPreExecute() {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setTitle("Downloading game content...");
        mProgressDialog.setMax(5);
        mProgressDialog.setProgress(0);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.show();
    }

    // only method that runs on a background thread.
    // It executes immediately after onPreExecute completes.
    @Override
    protected String doInBackground(String... params) {
        if(null==params) return null;
        String myTask = params[0];

        switch(myTask) {
            case ConstsSFIB.REQ_SENT_TASK:
                String lang = params[1];
                String limit = params[2];
                try {
                    //Log.d(TAG, "Started doInBackground to get sentences with url = " + json_url);
                    URL url = new URL(mJsonUrl);
                    HttpURLConnection.setFollowRedirects(false);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod(POST_MODE);
                    httpURLConnection.setConnectTimeout(TIMEOUT_MS);
                    httpURLConnection.setDoOutput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter =
                            new BufferedWriter(new OutputStreamWriter(OS, CHAR_SET));
                    String data = URLEncoder.encode(ConstsSFIB.REQ_SENT_LANG,CHAR_SET) + "=" +
                            URLEncoder.encode(lang, CHAR_SET)+ "&" +
                            URLEncoder.encode(ConstsSFIB.REQ_SENT_LIMIT, CHAR_SET) + "=" +
                            URLEncoder.encode(limit, CHAR_SET);
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader =
                            new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();

                    int cnt = 1;
                    while ((mJsonStr = bufferedReader.readLine())!=null) {
                        publishProgress(cnt++);
                        stringBuilder.append(mJsonStr);
                        stringBuilder.append("\n");
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    //Log.d(TAG, "Finished doInBackground and about to return");
                    return stringBuilder.toString().trim(); // trim the white spaces from result
                } catch (IOException e) {
                    //Log.d(TAG, "IO Exception");
                    e.printStackTrace();
                }
                break;

            case ConstsSFIB.REQ_STATS_TASK:
                String email = params[1];
                String game = params[2];
                String nameStr = params[3];
                try {
                    //Log.d(TAG, "Started doInBackground to get stats email = " + email + " game = "
                    //        + game + " name " + nameStr);
                    URL url = new URL(mJsonUrl);
                    HttpURLConnection.setFollowRedirects(false);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod(POST_MODE);
                    httpURLConnection.setConnectTimeout(TIMEOUT_MS);
                    httpURLConnection.setDoOutput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter =
                            new BufferedWriter(new OutputStreamWriter(OS, CHAR_SET));
                    String data = URLEncoder.encode(ConstsSFIB.REQ_STATS_EMAIL, CHAR_SET) + "=" +
                            URLEncoder.encode(email, CHAR_SET)+ "&" +
                            URLEncoder.encode(ConstsSFIB.REQ_STATS_GAME, CHAR_SET) + "=" +
                            URLEncoder.encode(game, CHAR_SET)+ "&" +
                            URLEncoder.encode(ConstsSFIB.REQ_STATS_PNAME, CHAR_SET) + "=" +
                            URLEncoder.encode(nameStr, CHAR_SET);
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader =
                            new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();

                    int cnt = 1;
                    while ((mJsonStr = bufferedReader.readLine())!=null) {
                        publishProgress(cnt++);
                        stringBuilder.append(mJsonStr);
                        stringBuilder.append("\n");
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    //Log.d(TAG, "Finished doInBackground and about to return");
                    return stringBuilder.toString().trim(); // trim the white spaces from result
                 } catch (IOException e) {
                    //Log.d(TAG, "IO Exception");
                    e.printStackTrace();
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
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        mProgressDialog.setProgress(progress);
    }

    // after finishing the background task, the returned result is published back on the UI
    // thread and available in this method
    @Override
    protected void onPostExecute(String result) {
        //Log.d(TAG, "onPostExecute called");
        if(delegate != null) delegate.processFinish(result);
        mProgressDialog.hide();
    }
}
