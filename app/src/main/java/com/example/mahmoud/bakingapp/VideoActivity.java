package com.example.mahmoud.bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mahmoud.bakingapp.models.Step;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoActivity extends AppCompatActivity {


    @BindView(R.id.playerView)
    SimpleExoPlayerView videoView;
    Step step;
    String url;
    private SimpleExoPlayer mExoPlayer;
    @BindView(R.id.video_txt)
    TextView video_txt;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
//    @BindView(R.id.iv_step)
//    ImageView iv_step;

    @BindView(R.id.tv_idtxt)
    TextView tv_idtxt;
    long resumePosition = 0;
    int resumeWindow = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }

        );
        if (savedInstanceState != null) {
            resumePosition = savedInstanceState.getLong("resumePosition");
            step = savedInstanceState.getParcelable("step_here");
            url = step.getVideoURL();
            Uri myUri = Uri.parse(url);
            initializePlayer(myUri);
            video_txt.setText(step.getDescription());

            int f = step.getId().intValue() + 1;
            tv_idtxt.setText("Step " + f);

        } else if (getIntent().hasExtra("choosen_step")) {
            step = getIntent().getExtras().getParcelable("choosen_step");
            url = step.getVideoURL();
            Uri myUri = Uri.parse(url);
            initializePlayer(myUri);
            video_txt.setText(step.getDescription());
            int f = step.getId().intValue() + 1;
            tv_idtxt.setText("Step " + f);

        }
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            videoView.setPlayer(mExoPlayer);

            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this, step.getShortDescription()), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);


            boolean haveResumePosition = resumeWindow != C.INDEX_UNSET;
            if (haveResumePosition) {
                mExoPlayer.seekTo(resumeWindow, resumePosition);
            }
            mExoPlayer.prepare(mediaSource, !haveResumePosition, false);


            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        resumeWindow = mExoPlayer.getCurrentWindowIndex();
        resumePosition = mExoPlayer.isCurrentWindowSeekable() ? Math.max(0, mExoPlayer.getCurrentPosition())
                : C.TIME_UNSET;
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            releasePlayer();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        releasePlayer();

        outState.putParcelable("step_here", step);
        outState.putLong("resumePosition", resumePosition);
        outState.putLong("resumeWindow", resumeWindow);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        releasePlayer();
        resumePosition = 0;
        setIntent(intent);
    }

}

