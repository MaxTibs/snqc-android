package com.example.maxtibs.snqc_android;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.maxtibs.snqc_android.Videos.Video;
import com.example.maxtibs.snqc_android.Videos.VideoAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class VideoFragment extends Fragment {

    public VideoFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // Define every video
        ArrayList<Video> videoArrayList = new ArrayList<>();
        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4", "Big Bunny"));
        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4", "Elephant Dream"));
        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4", "For Bigger Blaze"));
        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4", "For Bigger Escape"));
        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4", "For Bigger Fun"));
//        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4", "For Bigger Joyrides"));
//        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4", "For Bigger Meltdowns"));
//        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4", "Sintel"));
//        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4", "Subaru Outback On Street And Dirt"));
//        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4", "Tears of Steel"));
//        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4", "Volkswagen GTI Review"));
//        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4", "We Are Going On Bullrun"));
//        videoArrayList.add(new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4", "What care can you get for a grand?"));

        //Create adapter
        VideoAdapter videoAdapter = new VideoAdapter(getContext(), videoArrayList);

        //Set video adapter to listView
        View layout = getLayoutInflater().inflate(R.layout.video_list, null);
        ListView lv = layout.findViewById(R.id.videos_listView);
        lv.setAdapter(videoAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Video video = (Video) adapter.getItemAtPosition(position);
                video.start(getContext());
            }
        });

        return layout;
    }
}
