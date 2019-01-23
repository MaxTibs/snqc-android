package com.example.maxtibs.snqc_android.Videos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maxtibs.snqc_android.R;

import java.util.HashMap;


public class Video {

    public String url;
    public String title;
    public String imageName;

    public Video(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public void start(Context context) {
        Intent intent = new Intent(context, FullscreenVideo.class);
        intent.putExtra("URL", this.url);
        context.startActivity(intent);
    }
}
