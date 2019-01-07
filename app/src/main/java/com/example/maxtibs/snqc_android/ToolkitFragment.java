package com.example.maxtibs.snqc_android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maxtibs.snqc_android.toolkit.Toolkit;

public class ToolkitFragment extends Fragment {

    public ToolkitFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Return toolkit view (list of Tools)
        Toolkit toolkit = new Toolkit(getContext());
        return toolkit.getExpandableListView();
    }
}
