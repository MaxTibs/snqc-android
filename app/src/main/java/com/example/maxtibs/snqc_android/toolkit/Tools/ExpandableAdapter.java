package com.example.maxtibs.snqc_android.toolkit.Tools;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.maxtibs.snqc_android.R;
import java.util.ArrayList;
import java.util.List;


public class ExpandableAdapter extends ArrayAdapter<Tool> {

    private Context _context;
    private List<Tool> _tools = new ArrayList<Tool>();

    public ExpandableAdapter(Context context, List<Tool> tools) {
        super(context, 0, tools);
        this._context = context;
        this._tools = tools;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        Tool t = this._tools.get(position);

        //Create basic layout (basically header)
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.expandable_pannel, null, false);
            TextView tv = convertView.findViewById(R.id.name_fonctionality);
            tv.setText(t._name);
        }

        //Set content view
        ConstraintLayout cl = convertView.findViewById(R.id.content);
        Tool tool = this._tools.get(position);
        View v = tool.getConfigurationView(_context);
        cl.removeAllViews();
        cl.addView(v);
        tool.configureHeaderView(convertView);

        return convertView;
    }
}