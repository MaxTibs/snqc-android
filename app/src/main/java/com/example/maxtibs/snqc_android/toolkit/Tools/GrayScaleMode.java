package com.example.maxtibs.snqc_android.toolkit.Tools;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.maxtibs.snqc_android.R;
import com.example.maxtibs.snqc_android.Utilities.GrayScaleUtility;
import com.example.maxtibs.snqc_android.Utilities.OverlayService;


public class GrayScaleMode extends Tool {

    public int CONFIGURATION_LAYOUT = R.layout.grayscale_config;
    private Context _context;

    public GrayScaleMode(Context context) {
        this._name = "Mode ton de gris";
        this._context = context;
        // Enable the access to secure settings on the build of gray scale mode
        if (!GrayScaleUtility.hasPermission(this._context)) {
            GrayScaleUtility.askForPermission(this._context);
        }
    }

    @Override
    public View getConfigurationView(Context context) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(this.CONFIGURATION_LAYOUT, null);
    }

    @Override
    public void configureHeaderView(View v) {
        final Switch switchGrayMode = v.findViewById(R.id.switchButton);
        // Set initial switch's value
        switchGrayMode.setChecked(GrayScaleUtility.isGrayScaleEnable(this._context, OverlayService.class));

        final Context contextRef = this._context;
        switchGrayMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (GrayScaleUtility.hasPermission(contextRef)) {
                    GrayScaleUtility.toggleGrayScale(contextRef);
                }
                else {
                    // Put back the switch to unchecked state
                    switchGrayMode.toggle();
                    // TODO: Show steps to enable developer options
                }
            }
        });
    }
}
