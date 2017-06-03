package com.example.mahmoud.bakingapp;

import android.content.Context;
import android.content.Intent;
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
    @BindView(R.id.tvsplash) TextView tvsplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        context = this;

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(context, MainActivity.class);
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation(SplashActivity.this,tvsplash,"appname");
                        startActivity(intent,options.toBundle());

                        finish();
                    }


                });
            }
        }, 3000);

    }
}
