package com.example.maxtibs.snqc_android.toolkit.Tools.Videos;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.TextView;
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

        final VideoView videoView = (VideoView) findViewById(R.id.video_fullscreen_videoView);

        videoView.setVideoPath("http://www.ebookfrenzy.com/android_book/movie.mp4");

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


       /*try {
            MediaPlayer mp = new MediaPlayer();
            mp.setDataSource(url);
            mp.prepare();
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}