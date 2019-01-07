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

/**
 * This class defines and contains every tool of the application
 * It is used to created and render the Toolkit Fragment
 */
public class Toolkit {

    private List<Tool> _expandablesList = new ArrayList<Tool>();
    private ExpandableAdapter _expendableAdapter; //Custom adapter
    private View _expandableListView;

    public Toolkit(Context context) {
        //Create tools : Create tools here
        Tool sleepMode = new SleepMode(context);
        Tool busyMode = new BusyMode(context);
        Tool grayscaleMode = new GrayScaleMode(context);

        //Add tools into list here
        this.addTool(sleepMode);
        this.addTool(busyMode);
        this.addTool(grayscaleMode);

        //Create expandable list of tools
        this.createExpandableListView(context);
    }

    /**
     * Add tool to _expandableList
     * @param tool: Tool to add
     */
    private void addTool(Tool tool) {
        this._expandablesList.add(tool);
    }

    /**
     * Create ExpandableAdapter and bind it to a ListView
     * @param context: current context
     */
    private void createExpandableListView(Context context) {

        //Create ExpandableAdapter
        this._expendableAdapter = new ExpandableAdapter(context, this._expandablesList);

        //Create ListView and bind ExpandableAdapter to it
        ListView lv = new ListView(context);
        lv.setAdapter(this._expendableAdapter);

        //Store ListView in local attribute
        this._expandableListView = lv;
    }

    /**
     *
     * @return View : The ListView bound to ExpandableAdapter
     */
    public View getExpandableListView() {
        return this._expandableListView;
    }

}
