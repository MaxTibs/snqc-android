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

public class VideoFragment extends Fragment {

    public VideoFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // Define every video
        ArrayList<Video> videoArrayList = getArguments().getParcelableArrayList("videoList");

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
