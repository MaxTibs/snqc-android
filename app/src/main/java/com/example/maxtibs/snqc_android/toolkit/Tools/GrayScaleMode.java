package com.example.maxtibs.snqc_android.toolkit.Tools;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maxtibs.snqc_android.R;
import com.example.maxtibs.snqc_android.toolkit.GrayScaleStepper.GrayScaleStepperActivity;

public class GrayScaleMode extends Tool {

    public int CONFIGURATION_LAYOUT = R.layout.grayscale_config;
    private Context _context;
    private View _view;

    public GrayScaleMode(Context context) {
        this._name = "Mode ton de gris";
        this._context = context;
        this._view = null;
    }

    @Override
    public View getConfigurationView(Context context) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        return this._view = inflater.inflate(this.CONFIGURATION_LAYOUT, null);
    }

    @Override
    public void configureHeaderView(View v) {
        // Check first if we are in the correct view (Otherwise, it would modify 2 views)
        if (((TextView) v.findViewById(R.id.name_fonctionality)).getText() == this._name) {
            View ll = v.findViewById(R.id.header_view_ll);
            // Remove the switch from the view
            ((ViewGroup) ll).removeViewAt(((ViewGroup) ll).getChildCount() - 1);

            final Context contextRef = this._context;

            // Set the onClick listener for the icon
            this._view.findViewById(R.id.help_icon).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the stepper activity
                    Intent intent = new Intent();
                    intent.setClass(contextRef, GrayScaleStepperActivity.class);
                    contextRef.startActivity(intent);
                }
            });
        }
    }
}
