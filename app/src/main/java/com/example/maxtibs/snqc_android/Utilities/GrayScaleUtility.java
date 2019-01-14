package com.example.maxtibs.snqc_android.Utilities;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

public class GrayScaleUtility {
//    private static final String DISPLAY_DALTONIZER_ENABLED = "accessibility_display_daltonizer_enabled";
//    private static final String DISPLAY_DALTONIZER         = "accessibility_display_daltonizer";

    /**
     * To open the parameters in the device information details
     * @param context: current context of the app
     */
    public static void openDeviceInfoParameter(Context context) {
        context.startActivity(new Intent(android.provider.Settings./*ACTION_APPLICATION_DEVELOPMENT_SETTINGS*/ACTION_DEVICE_INFO_SETTINGS));
    }

    /**
     * To open the parameters in the developer options
     * @param context: current context of the app
     */
    public static void openDeveloperOptions(Context context) {
        context.startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
    }

    /**
     * TO check if the developer options has been enabled
     * @param context: current context of the app
     * @return true if developer options is enabled, false otherwise
     */
    public static boolean isDeveloperOptionsEnabled(Context context) {
        int devOptions = Settings.Global.getInt(context.getContentResolver(),Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,-1);

        ContentResolver contentResolver = context.getContentResolver();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            // Do something for lollipop and above versions
            return Settings.Global.getInt(contentResolver, Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) == 1;
        } else{
            // do something for phones running an SDK before lollipop
            return Settings.Global.getInt(context.getContentResolver(), Settings.Secure.ADB_ENABLED, 0) == 1;
        }
    }

//    public static boolean isGrayScaleEnable(Context c) {
//        ContentResolver contentResolver = c.getContentResolver();
//        return Settings.Secure.getInt(contentResolver, DISPLAY_DALTONIZER_ENABLED, 0) == 1
//                && Settings.Secure.getInt(contentResolver, DISPLAY_DALTONIZER, 0) == 0;
//    }
}
