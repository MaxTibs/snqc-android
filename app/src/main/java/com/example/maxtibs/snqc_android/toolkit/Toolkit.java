package com.example.maxtibs.snqc_android.toolkit;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.maxtibs.snqc_android.toolkit.Tools.BusyMode.BMActivity;
import com.example.maxtibs.snqc_android.toolkit.Tools.GrayScaleMode.GrayScaleModeActivity;
import com.example.maxtibs.snqc_android.toolkit.Tools.SleepMode.SMActivity;
import com.example.maxtibs.snqc_android.toolkit.Tools.ToolAdapter;
import com.example.maxtibs.snqc_android.toolkit.Tools.ITool;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines and contains every tool of the application
 * It is used to created and render the Toolkit Fragment
 */
public class Toolkit {

    private List<ITool> toolList = new ArrayList<ITool>();
    private ToolAdapter toolAdapter; //Custom adapter
    private View view;

    public Toolkit(Context context) {
        //Create tools : Create tools here
        ITool sleepMode = new SMActivity();
        ITool busyMode = new BMActivity();
        ITool grayscaleMode = new GrayScaleModeActivity();

        //Add tools into list here
        this.addTool(sleepMode);
        this.addTool(busyMode);
        this.addTool(grayscaleMode);

        //Create expandable list of tools
        this.createView(context);
    }

    /**
     * Add tool to _expandableList
     * @param tool: Tool to add
     */
    private void addTool(ITool tool) {
        this.toolList.add(tool);
    }

    /**
     * Create ToolAdapter and bind it to a ListView
     * @param context: current context
     */
    private void createView(final Context context) {

        //Create ToolAdapter
        this.toolAdapter = new ToolAdapter(context, this.toolList);

        //Create ListView and bind ToolAdapter to it
        ListView lv = new ListView(context);
        lv.setAdapter(this.toolAdapter);
        lv.setDivider(null);
        //Return toolView when clicking on list tool
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                ITool t = (ITool) adapter.getItemAtPosition(position);
                context.startActivity(t.getIntent(context));
            }
        });
        view = lv;
    }

    /**
     *
     * @return View : The ListView bound to ToolAdapter
     */
    public View getView() {
        return this.view;
    }

}
