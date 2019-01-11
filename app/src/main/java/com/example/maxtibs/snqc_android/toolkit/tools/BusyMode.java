package com.example.maxtibs.snqc_android.toolkit.tools;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.example.maxtibs.snqc_android.MainActivity;
import com.example.maxtibs.snqc_android.R;

public class BusyMode extends AppCompatActivity implements ITool {

    private static final int LAYOUT = R.layout.busymode_config;
    private static final int ICON = R.drawable.ic_busy_icon;
    private static final String NAME = "Mode occupé";

    public BusyMode(Context context) {}

    public Intent getConfigurationActivity(Context c) {
        /*final LayoutInflater inflater = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(this.LAYOUT, null);
        return v;*/
        return new Intent(c, MainActivity.class);
    }

    public int getIcon() { return this.ICON; }
    public int getLayout() { return this.LAYOUT; }

    public Tool getTool() {
        return new Tool(NAME, ICON, LAYOUT);
    }

    public Intent getIntent(Context context) {
        return new Intent(context, getClass());
    }

}
