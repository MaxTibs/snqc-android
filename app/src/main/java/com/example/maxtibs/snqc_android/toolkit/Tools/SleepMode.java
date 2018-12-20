package com.example.maxtibs.snqc_android.toolkit.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.maxtibs.snqc_android.R;


public class SleepMode extends Tool{

    public int CONFIGURATION_LAYOUT = R.layout.sleepmode_config;
    private Context _context;

    public SleepMode(Context context) {
        this._name = "Mode sommeil";
        this._context = context;
    }

    @Override
    public View getConfigurationView(Context c) {
        final LayoutInflater inflater = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(this.CONFIGURATION_LAYOUT, null);
        return v;
    }
}
