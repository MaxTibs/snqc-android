package com.example.maxtibs.snqc_android.toolkit.tools;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.example.maxtibs.snqc_android.MainActivity;
import com.example.maxtibs.snqc_android.R;


public class GrayScaleMode{

    public int LAYOUT = R.layout.grayscale_config;

    public GrayScaleMode(Context context) {
        //super("Mode ton de gris");
    }

    public Intent getConfigurationActivity(Context c) {
        /*final LayoutInflater inflater = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(LAYOUT, null);
        return v;*/
        return new Intent(c, MainActivity.class);
    }

    public int getIcon() { return 0;}
    public int getLayout() { return 0; }

}
