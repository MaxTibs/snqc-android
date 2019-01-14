package com.example.maxtibs.snqc_android.toolkit.Tools.Videos;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class VideoAdapter extends ArrayAdapter<Video> {

    public VideoAdapter(Context context, ArrayList<Video> mVideo) {
        super(context, 0, mVideo);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Video video = this.getItem(position);

        if(convertView == null)
            convertView = video.getView(this.getContext());

        return convertView;
    }
    }
