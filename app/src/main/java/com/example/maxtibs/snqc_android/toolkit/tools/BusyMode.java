package com.example.maxtibs.snqc_android.toolkit.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.maxtibs.snqc_android.R;

public class BusyMode extends Tool{

    public int CONFIGURATION_LAYOUT = R.layout.busymode_config;
    //public TimeRange timeRange;

    public BusyMode(Context context) {
        super("Mode occupé");
    }

    @Override
    public View getConfigurationView(Context c) {
        final LayoutInflater inflater = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(this.CONFIGURATION_LAYOUT, null);
        return v;
    }
}
