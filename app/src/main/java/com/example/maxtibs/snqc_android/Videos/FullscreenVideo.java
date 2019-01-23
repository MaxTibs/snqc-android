package com.example.maxtibs.snqc_android.Videos;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.maxtibs.snqc_android.R;

import static android.content.ContentValues.TAG;


public class FullscreenVideo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_fullscreen);
        //TODO: VOIR DOC -> https://www.techotopia.com/index.php/An_Android_Studio_VideoView_and_MediaController_Tutorial
        String url = getIntent().getExtras().getString("URL");

        final VideoView videoView = findViewById(R.id.video_fullscreen_videoView);

        videoView.setVideoPath(url);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);

        videoView.setOnPreparedListener(new
            MediaPlayer.OnPreparedListener()  {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.i(TAG, "Duration = " +
                            videoView.getDuration());
                }
            });

        videoView.start();
    }

}