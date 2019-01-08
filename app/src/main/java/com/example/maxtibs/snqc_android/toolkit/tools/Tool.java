package com.example.maxtibs.snqc_android.toolkit.tools;

import android.content.Context;
import android.view.View;

public abstract class Tool {
    protected String _name;

    public Tool(String name) {
        this._name = name;
    }

    protected abstract View getConfigurationView(Context c);

}
