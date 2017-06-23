package com.example.mahmoud.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    Context context;
    @BindView(R.id.tvsplash)
    TextView tvsplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        context = this;
        LongOperation bgTask = new LongOperation();
        bgTask.execute();
//        new Handler().postDelayed(new Runnable() {
//
//
//            @Override
//            public void run() {
//
//
//            }
//        }, 3000);

    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            Intent intent = new Intent(context, MainActivity.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(SplashActivity.this, tvsplash, "appname");
            startActivity(intent, options.toBundle());

            finish();
        }
    }
}
