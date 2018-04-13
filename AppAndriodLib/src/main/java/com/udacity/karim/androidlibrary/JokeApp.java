package com.udacity.karim.androidlibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;



public class JokeApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        Intent intent = this.getIntent();
        if (intent != null) {
            String joke = getIntent().getStringExtra("joke");
            TextView tv = (TextView) findViewById(R.id.textview);
            tv.setText(joke);
        }
    }
}
