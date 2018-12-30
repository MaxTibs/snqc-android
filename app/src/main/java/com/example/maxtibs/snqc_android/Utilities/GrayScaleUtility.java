package com.example.maxtibs.snqc_android.Utilities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

public class GrayScaleUtility {
    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;

    public static boolean hasPermission(Context context) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && Settings.canDrawOverlays(context);
    }

    public static void askForPermission(Context context) {
        // Check if Android M or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Show alert dialog to the user saying a separate permission is needed
            // Launch the settings activity if the user prefers
            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            context.startActivity(myIntent);
        }
    }

    public static boolean isGrayScaleEnable(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void toggleGrayScale(Context context) {
        Intent intent = new Intent(context, OverlayService.class);
        // Try to stop the service if it is already running
        // Otherwise start the service
        if(!context.stopService(intent)){
            context.startService(intent);
        }
    }
}
