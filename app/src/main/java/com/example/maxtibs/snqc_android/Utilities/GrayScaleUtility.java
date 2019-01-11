package com.example.maxtibs.snqc_android.Utilities;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

public class GrayScaleUtility {
//    private static final String DISPLAY_DALTONIZER_ENABLED = "accessibility_display_daltonizer_enabled";
//    private static final String DISPLAY_DALTONIZER         = "accessibility_display_daltonizer";

    public static void openDeviceInfoParameter(Context context) {
        context.startActivity(new Intent(android.provider.Settings./*ACTION_APPLICATION_DEVELOPMENT_SETTINGS*/ACTION_DEVICE_INFO_SETTINGS));
    }

    public static void openDeveloperOptions(Context context) {
        context.startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
    }

    public static boolean isDeveloperOptionsEnabled(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        return Settings.Secure.getInt(contentResolver, Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) == 1;
    }

//    public static boolean isGrayScaleEnable(Context c) {
//        ContentResolver contentResolver = c.getContentResolver();
//        return Settings.Secure.getInt(contentResolver, DISPLAY_DALTONIZER_ENABLED, 0) == 1
//                && Settings.Secure.getInt(contentResolver, DISPLAY_DALTONIZER, 0) == 0;
//    }
}
