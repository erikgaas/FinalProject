package com.udacity.gradle.free;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.androidjokes.JokeActivity;
import com.udacity.gradle.backend.myApi.MyApi;

import java.io.IOException;

/**
 * Created by Erik on 12/20/2015.
 */
class JokeAsyncTask extends AsyncTask<Void, Void, String> {
    private String PUT_JOKE_INTENT = "com.udacity.gradle.androidjokes.JOKE";
    private static MyApi myApiService = null;
    private Context mContext;


    public JokeAsyncTask(Context context) {
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(Void... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    //deployed appspot is here: "https://udacityhellotest.appspot.com/_ah/api/"
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        //context = params[0].first;
        //String name = params[0].second;

        try {
            return myApiService.tellJoke().execute().getData();//sayHi(name).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        //Toast.makeText(mContext, "hellohello", Toast.LENGTH_LONG).show();

        Intent jokeIntent = new Intent(mContext, JokeActivity.class);
        jokeIntent.putExtra(PUT_JOKE_INTENT, result);
        jokeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(jokeIntent);
    }
}
