package com.example.samue_074d5tq.videofluxfinalversion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.HashMap;


public class Video {

    public String url;
    public String title;

    private final int TITLE_ID = R.id.title;
    //private final int PLAY_BTN_ID = R.id.btn_play_pause;
    private final int IMAGE_VIEW_ID = R.id.imageView;

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

        //Modify view attributes
        VideoView videoView = view.findViewById(R.id.imageView);
        //ImageButton imageButton = view.findViewById(R.id.btn_play_pause);
        TextView title = view.findViewById(R.id.title);

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
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        this.imageView.setBackground(bitmapDrawable);

    }

    /*private void initImageButton(View view) {
        final ImageButton playBtn = view.findViewById(this.PLAY_BTN_ID);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isPlaying) {

                    videoView.pause();
                    isPlaying = false;
                    playBtn.setImageResource(R.drawable.ic_play);
                } else {
                    videoView.start();
                    isPlaying = true;
                    playBtn.setImageResource(R.drawable.ic_stop);
                }
            }
        });
    }*/

    //https://stackoverflow.com/questions/22954894/is-it-possible-to-generate-a-thumbnail-from-a-video-url-in-android
    private Bitmap retrieveVideoFrameFromVideo(String videoPath)
    {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try
        {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            bitmap = mediaMetadataRetriever.getFrameAtTime(2000000);
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

}
