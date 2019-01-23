package com.example.maxtibs.snqc_android.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;

import com.example.maxtibs.snqc_android.Videos.Video;

import java.util.HashMap;

public class VideoUtility {

    //https://stackoverflow.com/questions/22954894/is-it-possible-to-generate-a-thumbnail-from-a-video-url-in-android
    public static Bitmap retrieveVideoFrameFromVideo(String url)
    {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try
        {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(url, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(url);

            bitmap = mediaMetadataRetriever.getFrameAtTime(2000000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }

    public static void saveToCache(Context context, Video video, Bitmap bitmap){
        if(bitmap != null) Cache.getInstance(context).getLru().put(video.imageName, bitmap);
    }

    public static Bitmap loadThumbnailFromCache(Context context, Video video){
        return (Bitmap)Cache.getInstance(context).getLru().get(video.imageName);
    }
}
