package com.example.maxtibs.snqc_android.toolkit.Tools.Videos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maxtibs.snqc_android.R;
import com.example.maxtibs.snqc_android.utilities.VideoUtility;

import java.util.ArrayList;
import java.util.HashMap;

public class VideoAdapter extends ArrayAdapter<Video> {

    private static class ViewHolder {
        TextView title;
        ImageView imageView;
    }

    public VideoAdapter(Context context, ArrayList<Video> mVideo) {
        super(context, 0, mVideo);
    }

    @Override
    public @NonNull View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Video video = this.getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            // always attach to the parent (https://possiblemobile.com/2013/05/layout-inflation-as-intended/)
            convertView = inflater.inflate(R.layout.video, parent, false);
            viewHolder.title = convertView.findViewById(R.id.video_title);
            viewHolder.imageView = convertView.findViewById(R.id.video_imageView);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);

        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.title.setText(video.title);

        //Set preview image
        Bitmap bitmap = VideoUtility.loadThumbnailFromCache(getContext(), video);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getContext().getResources(), bitmap);
        viewHolder.imageView.setBackground(bitmapDrawable);

        // Return the completed view to render on screen
        return convertView;
    }
}
