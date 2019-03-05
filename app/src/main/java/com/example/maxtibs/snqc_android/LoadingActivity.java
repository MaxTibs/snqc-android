package com.example.maxtibs.snqc_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.maxtibs.snqc_android.Videos.Video;
import com.example.maxtibs.snqc_android.utilities.VideoUtility;

import java.util.ArrayList;

public class LoadingActivity extends Activity {

    private ArrayList<Video> videoArrayList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new Thread(new Runnable() {
            @Override
            public void run() {
                doInBackground();

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("videoList", videoArrayList);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        }).start();
    }

    /**
     * Function executed in another thread - not the UIThread
     */
    private void doInBackground() {
        // Define every video
        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4", "Big Bunny"));
        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4", "Elephant Dream"));
        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4", "For Bigger Blaze"));
        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4", "For Bigger Escape"));
        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4", "For Bigger Fun"));
        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4", "For Bigger Joyrides"));
        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4", "For Bigger Meltdowns"));
        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4", "Sintel"));
        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4", "Subaru Outback On Street And Dirt"));
        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4", "Tears of Steel"));
        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4", "Volkswagen GTI Review"));
        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4", "We Are Going On Bullrun"));
        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4", "What care can you get for a grand?"));

        for (Video video : videoArrayList) {
            video.imageName = video.title.replaceAll(" ", "_");
            VideoUtility.saveToCache(getApplicationContext(), video, VideoUtility.retrieveVideoFrameFromVideo(video.url));
        }
    }
}
