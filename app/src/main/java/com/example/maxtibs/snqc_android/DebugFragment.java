package com.example.maxtibs.snqc_android;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.maxtibs.snqc_android.toolkit.Tools.SleepMode.SMModel;

public class DebugFragment extends Fragment {

    private final int CONFIGURATION_LAYOUT = R.layout.debug_layout;
    private View parent;

    public DebugFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(this.CONFIGURATION_LAYOUT, null);
        LinearLayout containter = v.findViewById(R.id.debug_fragment);

        try {
            Process p = Runtime.getRuntime().exec("su -c ls .");
        }catch (Exception e) {
            e.printStackTrace();
        }


        return v;
    }


}
