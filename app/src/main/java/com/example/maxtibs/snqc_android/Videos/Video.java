package com.example.maxtibs.snqc_android.Videos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maxtibs.snqc_android.R;

import java.util.HashMap;


public class Video {

    public String url;
    public String title;


    private final int TITLE_ID = R.id.video_title;
    //private final int PLAY_BTN_ID = R.id.btn_play_pause;
    private final int IMAGE_VIEW_ID = R.id.video_imageView;

    private boolean isPlaying = false;
    private ImageView imageView;

    public Video(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public View getView(Context context) {
        //Create view from XML
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.video, null, false);

        setTitle(view);
        initVideoView(context, view);
        //initImageButton(view);

        return view;
    }

    private void setTitle(View view) {
        TextView title = view.findViewById(this.TITLE_ID);
        title.setText(this.title);
    }

    private void initVideoView(Context context, View view) {
        this.imageView = view.findViewById(this.IMAGE_VIEW_ID);

        //Set video url
        //Uri uri = Uri.parse(this.url);
        //this.videoView.setVideoURI(uri);

        //Set preview image
        Bitmap bitmap = this.retrieveVideoFrameFromVideo(this.url);
        //Bitmap bitmap1 = ThumbnailUtils.createVideoThumbnail(this.url, MediaStore.Video.Thumbnails.MINI_KIND);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        this.imageView.setBackground(bitmapDrawable);

    }

    //https://stackoverflow.com/questions/22954894/is-it-possible-to-generate-a-thumbnail-from-a-video-url-in-android
    private Bitmap retrieveVideoFrameFromVideo(String url)
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
            //throw new Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }

    public void start(Context context) {
        Intent intent = new Intent(context, FullscreenVideo.class);
        intent.putExtra("URL", this.url);
        context.startActivity(intent);
    }
}
