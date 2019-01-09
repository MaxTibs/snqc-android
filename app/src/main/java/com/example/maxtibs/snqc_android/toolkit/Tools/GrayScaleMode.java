package com.example.maxtibs.snqc_android.toolkit.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.maxtibs.snqc_android.R;


public class GrayScaleMode extends Tool {

    public int CONFIGURATION_LAYOUT = R.layout.grayscale_config;
    private Context _context;

    public GrayScaleMode(Context context) {
        this._name = "Mode ton de gris";
        this._context = context;
    }

    @Override
    public View getConfigurationView(Context context) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(this.CONFIGURATION_LAYOUT, null);
    }

    @Override
    public void configureHeaderView(View v) {
        // Check first if we are in the correct view (Otherwise, it would modify 2 views)
        if (((TextView) v.findViewById(R.id.name_fonctionality)).getText() == this._name) {
            View ll = v.findViewById(R.id.header_view_ll);
            // Remove the switch from the view
            ((ViewGroup) ll).removeViewAt(((ViewGroup) ll).getChildCount() - 1);

            // Create and set the button
            Button btn = new Button(this._context);
            btn.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            btn.setBackground(this._context.getResources().getDrawable(R.drawable.ic_help));
            btn.setTag("grayScaleHelp");

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("BOO");
                }
            });

            ((ViewGroup) ll).addView(btn);
        }
    }

//    public void activateGrayScaleStepper() {
//        System.out.println("BOO");
//    }
}
