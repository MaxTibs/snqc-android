package com.example.maxtibs.snqc_android.toolkit.Tools;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.maxtibs.snqc_android.R;


public class GrayScaleMode extends Tool {

    public int CONFIGURATION_LAYOUT = R.layout.grayscale_config;
    private Context _context;

    public GrayScaleMode(Context context) {
        this._name = "Mode ton de gris";
        this._context = context;
    }

    @Override
    public View getConfigurationView(Context c) {
        final LayoutInflater inflater = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(this.CONFIGURATION_LAYOUT, null);
    }

    @Override
    public void configureHeaderView(View v) {
        Switch switchGrayMode = v.findViewById(R.id.switchButton);
        switchGrayMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("SWITCH STATE = " + isChecked);
            }
        });
    }
}
