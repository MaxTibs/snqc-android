package com.example.maxtibs.snqc_android.toolkit;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.maxtibs.snqc_android.toolkit.Tools.BusyMode;
import com.example.maxtibs.snqc_android.toolkit.Tools.ToolAdapter;
import com.example.maxtibs.snqc_android.toolkit.Tools.ITool;
import com.example.maxtibs.snqc_android.toolkit.Tools.SleepMode.SleepMode;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines and contains every tool of the application
 * It is used to created and render the Toolkit Fragment
 */
public class Toolkit {

    private List<ITool> _expandablesList = new ArrayList<ITool>();
    private ToolAdapter _expendableAdapter; //Custom adapter
    private View _expandableListView;

    public Toolkit(Context context) {
        //Create tools : Create tools here
        ITool sleepMode = new SleepMode();
        ITool busyMode = new BusyMode(context);
        //Tool grayscaleMode = new GrayScaleMode(context);

        //Add tools into list here
        this.addTool(sleepMode);
        this.addTool(busyMode);
        //this.addTool(grayscaleMode);

        //Create expandable list of tools
        this.createExpandableListView(context);
    }

    /**
     * Add tool to _expandableList
     * @param tool: Tool to add
     */
    private void addTool(ITool tool) {
        this._expandablesList.add(tool);
    }

    /**
     * Create ToolAdapter and bind it to a ListView
     * @param context: current context
     */
    private void createExpandableListView(final Context context) {

        //Create ToolAdapter
        this._expendableAdapter = new ToolAdapter(context, this._expandablesList);

        //Create ListView and bind ToolAdapter to it
        ListView lv = new ListView(context);
        lv.setAdapter(this._expendableAdapter);
        lv.setDivider(null);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                ITool t = (ITool) adapter.getItemAtPosition(position);
                context.startActivity(t.getIntent(context));
            }
        });

        //Store ListView in local attribute
        this._expandableListView = lv;
    }

    /**
     *
     * @return View : The ListView bound to ToolAdapter
     */
    public View getExpandableListView() {
        return this._expandableListView;
    }

}
