package com.example.maxtibs.snqc_android.Videos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maxtibs.snqc_android.R;

import java.util.HashMap;


public class Video implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public String url;
    public String title;
    public String imageName;

    public Video(String url, String title) {
        this.url = url;
        this.title = title;
        this.imageName = "";
    }

    public void start(Context context) {
        Intent intent = new Intent(context, FullscreenVideo.class);
        intent.putExtra("URL", this.url);
        context.startActivity(intent);
    }
    // Parcelling part
    public Video(Parcel in){
        this.url = in.readString();
        this.title = in.readString();
        this.imageName =  in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.title);
        dest.writeString(this.imageName);
    }

    @Override
    public String toString() {
        return "Student{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", imageName='" + imageName + '\'' +
                '}';
    }

}
