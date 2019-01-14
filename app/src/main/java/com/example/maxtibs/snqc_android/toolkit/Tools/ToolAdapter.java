package com.example.maxtibs.snqc_android.toolkit.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.maxtibs.snqc_android.R;

public class ToolAdapter extends ArrayAdapter<ITool> {

    private Context _context;
    private List<ITool> _tools = new ArrayList<ITool>();

    public ToolAdapter(Context context, List<ITool> tools) {
        super(context, 0, tools);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ITool iTool = getItem(position);
        Tool t = iTool.getTool();

        //Since we're creating list of tools, we just want to display tool_entry
        final LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            View v = inflater.inflate(R.layout.tool_entry, parent, false);
            //Set tool name
            TextView name = v.findViewById(R.id.tool_entry_name);
            name.setText(t.getNAME());
            //setIcon
            ImageView icon = v.findViewById(R.id.tool_entry_icon);
            icon.setImageResource(t.getICON());
            convertView = v;
        }

        return convertView;

    }
}