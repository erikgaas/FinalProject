package com.udacity.gradle.androidjokes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {
    private String PUT_JOKE_INTENT = "com.udacity.gradle.androidjokes.JOKE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent jokeIntent = getIntent();
        String joke = jokeIntent.getStringExtra(PUT_JOKE_INTENT);
        setContentView(R.layout.activity_joke);

        TextView textView = (TextView)findViewById(R.id.jokeText);
        textView.setText(joke);
    }
}
