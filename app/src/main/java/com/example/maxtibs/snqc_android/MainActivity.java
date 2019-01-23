package com.example.maxtibs.snqc_android;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.maxtibs.snqc_android.Videos.Video;
import com.example.maxtibs.snqc_android.utilities.VideoUtility;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ArrayList<Video> videoArrayList = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    getSupportFragmentManager().beginTransaction().replace(R.id.navigation_frame, new DashboardFragment()).commit();
                    return true;
                case R.id.navigation_video:
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("videoList", videoArrayList);
                    VideoFragment videoFragment = new VideoFragment();
                    videoFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.navigation_frame, videoFragment).commit();
                    return true;
                case R.id.navigation_toolkits:
                    getSupportFragmentManager().beginTransaction().replace(R.id.navigation_frame, new ToolkitFragment()).commit();
                    return true;
                case R.id.navigation_test:
                    //getSupportFragmentManager().beginTransaction().replace(R.id.navigation_frame, new TestFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_dashboard);

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
            VideoUtility.saveToCahche(getApplicationContext(), video, VideoUtility.retrieveVideoFrameFromVideo(video.url));
        }
    }

}