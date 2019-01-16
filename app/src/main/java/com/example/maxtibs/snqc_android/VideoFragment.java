package com.example.maxtibs.snqc_android;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.maxtibs.snqc_android.toolkit.Tools.Videos.Video;
import com.example.maxtibs.snqc_android.toolkit.Tools.Videos.VideoAdapter;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class VideoFragment extends Fragment {

    private HashMap<String, String> URLs = new HashMap<>();

    public VideoFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // Defines URL
        URLs.put("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4", "Big Bunny");
        URLs.put("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4", "Elephant Dream");
        URLs.put("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4", "For Bigger Blaze");
        URLs.put("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4", "For Bigger Escape");
        URLs.put("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4", "For Bigger Fun");
        URLs.put("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4", "For Bigger Joyrides");
        URLs.put("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4", "For Bigger Meltdowns");
        URLs.put("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4", "Sintel");
        URLs.put("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4", "Subaru Outback On Street And Dirt");
        URLs.put("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4", "Tears of Steel");
        URLs.put("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4", "Volkswagen GTI Review");
        URLs.put("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4", "We Are Going On Bullrun");
        URLs.put("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4", "What care can you get for a grand?");

        //Defines every video
        ArrayList<Video> videoArrayList = new ArrayList<>();

        for (Map.Entry<String, String> entry : URLs.entrySet()) {
            videoArrayList.add(new Video(entry.getKey(), entry.getValue()));
        }

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
