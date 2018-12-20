package com.example.maxtibs.snqc_android.toolkit;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.example.maxtibs.snqc_android.toolkit.Tools.BusyMode;
import com.example.maxtibs.snqc_android.toolkit.Tools.ExpandableAdapter;
import com.example.maxtibs.snqc_android.toolkit.Tools.GrayScaleMode;
import com.example.maxtibs.snqc_android.toolkit.Tools.SleepMode;
import com.example.maxtibs.snqc_android.toolkit.Tools.Tool;

import java.util.ArrayList;
import java.util.List;

public class Toolkit {

    private Context _context;
    private List<Tool> _expandablesList = new ArrayList<Tool>();
    private ExpandableAdapter _expendableAdapter;
    private View _expandableListView;

    public Toolkit(Context context) {
        this._context = context;

        //Create tools
        Tool sleepMode = new SleepMode(this._context);
        Tool busyMode = new BusyMode(this._context);
        Tool grayscaleMode = new GrayScaleMode(this._context);

        //Add tools into list here
        this._expandablesList.add(sleepMode);
        this._expandablesList.add(busyMode);
        this._expandablesList.add(grayscaleMode);

        //Create expandable list of tools
        this.createExpandableListView();
    }

    private void createExpandableListView() {
        this._expendableAdapter = new ExpandableAdapter(this._context, this._expandablesList);
        ListView lv = new ListView(this._context);
        lv.setAdapter(this._expendableAdapter);
        this._expandableListView = lv;
    }

    public View getExpandableListView() {
        return this._expandableListView;
    }

}
