package com.example.maxtibs.snqc_android;

import android.os.Bundle;
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

    private HashMap<String, String> URLs = new HashMap<String, String>();

    public VideoFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // Defines URL
        URLs.put("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4", "Big Bunny");
        URLs.put("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4", "Elephant Dream");
        URLs.put("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4", "For Bigger Blaze");

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
        View layout = getLayoutInflater().inflate(R.layout.video_list, null);
        ListView lv = layout.findViewById(R.id.videos_listview);
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
