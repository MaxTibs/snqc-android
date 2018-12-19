package com.example.maxtibs.snqc_android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ToolkitFragment extends Fragment {

    public ToolkitFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        TextView tv = new TextView(getActivity());
        tv.setText("TOOLKIT FRAGMENT");
        return tv;
    }
}
