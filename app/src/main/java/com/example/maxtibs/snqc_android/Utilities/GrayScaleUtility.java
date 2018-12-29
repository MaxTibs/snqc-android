package com.example.maxtibs.snqc_android.Utilities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.widget.Toast;

import com.example.maxtibs.snqc_android.BuildConfig;
import com.scottyab.rootbeer.RootBeer;

import java.io.DataOutputStream;
import java.io.IOException;

public class GrayScaleUtility {
    private static final String PERMISSION = "android.permission.WRITE_SECURE_SETTINGS";

    private static final String DISPLAY_DALTONIZER_ENABLED = "accessibility_display_daltonizer_enabled";
    private static final String DISPLAY_DALTONIZER         = "accessibility_display_daltonizer";

    public static boolean hasPermission(Context c) {
        return c.checkCallingOrSelfPermission(PERMISSION) == PackageManager.PERMISSION_GRANTED;
    }

    public static void enableSecureSettingsAccess(Context c) {
        RootBeer rootBeer = new RootBeer(c);
        if (rootBeer.isRooted()) {
            try {
                Process p = Runtime.getRuntime().exec("su");
                DataOutputStream os = new DataOutputStream(p.getOutputStream());
                os.writeBytes("pm grant " + c.getPackageName() + " android.permission.WRITE_SECURE_SETTINGS \n");
                os.writeBytes("exit\n");
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            // Show toast about device not rooted so unable to set gray scale through developer options
            String msg = "Votre appareil n'est pas en mode 'root'. Il est donc impossible d'accéder aux options de développement.";
            Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
        }
    }

    public static boolean isGrayScaleEnable(Context c) {
        ContentResolver contentResolver = c.getContentResolver();
        return Settings.Secure.getInt(contentResolver, DISPLAY_DALTONIZER_ENABLED, 0) == 1
                && Settings.Secure.getInt(contentResolver, DISPLAY_DALTONIZER, 0) == 0;
    }

    public static void toggleGrayScale(Context c, boolean isGrayScaleEnable) {
        ContentResolver contentResolver = c.getContentResolver();
        Settings.Secure.putInt(contentResolver, DISPLAY_DALTONIZER_ENABLED, isGrayScaleEnable ? 1 : 0);
        Settings.Secure.putInt(contentResolver, DISPLAY_DALTONIZER, isGrayScaleEnable ? 0 : -1);
    }
}
