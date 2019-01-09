package com.example.maxtibs.snqc_android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private HashMap<String, String> URLs = new HashMap<String, String>();

    public VideoFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Defines URL
        URLs.put("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4", "Big Bunny");
        URLs.put("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4", "Elephant Dream");
        URLs.put("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4", "For Bigger Blazes");

        //Defines every video
        ArrayList<Video> videoArrayList = new ArrayList<>();
        Iterator it = URLs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            videoArrayList.add(new Video((String)pair.getKey(), (String)pair.getValue()));
        }

        //Create adapter
        VideoAdapter videoAdapter = new VideoAdapter(getContext(), videoArrayList);

        //Set video adapter to listview
        View videoList = getLayoutInflater().inflate(R.layout.video_list, null);
        ListView lv = videoList.findViewById(R.id.videoListView);
        lv.setAdapter(videoAdapter);

        return lv;
    }
}
