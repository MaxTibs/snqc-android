package com.example.samue_074d5tq.videofluxfinalversion;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class VideoAdapter extends ArrayAdapter<Video> {

    public VideoAdapter(Context context, ArrayList<Video> videos) {
        super(context, 0, videos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Video video = this.getItem(position);

        if(convertView == null)
            convertView = video.getView(this.getContext());

        return convertView;
    }

}
