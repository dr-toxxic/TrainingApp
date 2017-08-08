package com.toxxic.trainingapp;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayerActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {
    public static final String ACTION = "com.toxxic.trainingapp.intent.action.VIDEO_PLAYER";
    public static final String EXTRA_URL = "com.toxxic.trainingapp.extra.URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_player);

        VideoView v = (VideoView) findViewById(R.id.videoView);

        String url = null;
        if (getIntent().getExtras() != null) {
            url = getIntent().getExtras().getString(VideoPlayerActivity.EXTRA_URL);

            if (url != null) {
                MediaController m = new MediaController(this);
                v.setMediaController(m);

                v.setOnCompletionListener(this);
                v.setVideoURI(Uri.parse(url));
                v.start();
            }
        }

        if (url == null) {
            throw new IllegalArgumentException("Must set url extra paremeter in intent.");
        }
    }

    @Override
    public void onCompletion(MediaPlayer v) {
        finish();
    }
}
