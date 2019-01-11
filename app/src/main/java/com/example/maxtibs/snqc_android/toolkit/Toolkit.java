package com.example.maxtibs.snqc_android.toolkit;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.maxtibs.snqc_android.toolkit.tools.BusyMode;
import com.example.maxtibs.snqc_android.toolkit.tools.ExpandableAdapter;
import com.example.maxtibs.snqc_android.toolkit.tools.ITool;
import com.example.maxtibs.snqc_android.toolkit.tools.SleepMode.SleepMode;
import com.example.maxtibs.snqc_android.toolkit.tools.Tool;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines and contains every tool of the application
 * It is used to created and render the Toolkit Fragment
 */
public class Toolkit {

    private List<ITool> _expandablesList = new ArrayList<ITool>();
    private ExpandableAdapter _expendableAdapter; //Custom adapter
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
     * Create ExpandableAdapter and bind it to a ListView
     * @param context: current context
     */
    private void createExpandableListView(final Context context) {

        //Create ExpandableAdapter
        this._expendableAdapter = new ExpandableAdapter(context, this._expandablesList);

        //Create ListView and bind ExpandableAdapter to it
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
     * @return View : The ListView bound to ExpandableAdapter
     */
    public View getExpandableListView() {
        return this._expandableListView;
    }

}
