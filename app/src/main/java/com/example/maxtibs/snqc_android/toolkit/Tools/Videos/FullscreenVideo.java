package com.example.maxtibs.snqc_android.toolkit.Tools.Videos;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.VideoView;

import com.example.maxtibs.snqc_android.R;


public class FullscreenVideo extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(this);
        String url = getIntent().getExtras().getString("URL");

        View fullScreenVideoLayout = inflater.inflate(R.layout.video_fullscreen, null);
        VideoView videoView = fullScreenVideoLayout.findViewById(R.id.video_videoview);

        videoView.setVideoURI(Uri.parse(url));
        videoView.start();

        setContentView(fullScreenVideoLayout);

    }

}