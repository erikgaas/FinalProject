package com.udacity.gradle.builditbigger;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.JokeAsyncTask;


public class MainActivity extends ActionBarActivity {
    InterstitialAd mInterstitialAd;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        spinner = (ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                spinner.setVisibility(View.VISIBLE);
                requestNewInterstitial();
                new JokeAsyncTask(getApplicationContext()).execute();
            }
        });

        requestNewInterstitial();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        spinner.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void tellJoke(View view){
        if (mInterstitialAd.isLoaded() && BuildConfig.FLAVOR.equals("free")){
            mInterstitialAd.show();
        } else {
            spinner.setVisibility(View.VISIBLE);
            new JokeAsyncTask(getApplicationContext()).execute();
        }

    }

    private void requestNewInterstitial() {
        if (BuildConfig.FLAVOR.equals("free")) {
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice("DCA58F894EC2F3D6EDDDE38F0462C7AC")
                    .build();
            mInterstitialAd.loadAd(adRequest);
        }
    }


}
