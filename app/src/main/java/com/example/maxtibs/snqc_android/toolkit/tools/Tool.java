package com.example.maxtibs.snqc_android.toolkit.tools;

public class Tool {

    private final String NAME;
    private final int ICON;
    private final int LAYOUT;

    public Tool(String name, int icon, int layout) {
        this.NAME = name;
        this.ICON = icon;
        this.LAYOUT = layout;
    }

    public String getNAME() {
        return this.NAME;
    }

    public int getLAYOUT() {
        return LAYOUT;
    }

    public int getICON() {
        return ICON;
    }
}
