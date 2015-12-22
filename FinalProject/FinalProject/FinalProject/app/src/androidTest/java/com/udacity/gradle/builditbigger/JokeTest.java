package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;

import com.udacity.gradle.builditbigger.JokeAsyncTask;

/**
 * Created by Erik on 12/20/2015.
 */
public class JokeTest extends AndroidTestCase{
    public void testVerifyJoke() {
        String joke = new JokeAsyncTask(this.getContext()).doInBackground();
        assertNotNull(joke);
    }
}
