package com.example.maxtibs.snqc_android.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalStorage {

    /**
     * Return sharedPreference based on filename
     * @param context current content
     * @param filename filename where is stored data
     * @return sharedPreference
     */
    public static SharedPreferences getSharedPreferences(Context context, String filename) {
        return context.getSharedPreferences(filename, Context.MODE_PRIVATE);
    }

    /**
     * Returns SharedPreference editor from contenxt
     * @param context current context
     * @return SP.editor
     */
    public static SharedPreferences.Editor getEditor(Context context, String filename) {
        //Get sharedPreferences to get/edit user's data
        SharedPreferences sharedPreferences = getSharedPreferences(context, filename);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        return editor;
    }

}
