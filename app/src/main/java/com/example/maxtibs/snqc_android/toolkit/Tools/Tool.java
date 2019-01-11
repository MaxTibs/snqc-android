package com.example.maxtibs.snqc_android.toolkit.Tools;

import android.content.Context;
import android.view.View;

public abstract class Tool {
    protected String _name;

    protected abstract View getConfigurationView(Context c);

    public abstract void configureHeaderView(View view);

    public String getFonctionnalityName() { return this._name; }
}
