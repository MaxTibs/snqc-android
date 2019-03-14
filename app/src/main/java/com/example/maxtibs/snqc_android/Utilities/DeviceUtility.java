package com.example.maxtibs.snqc_android.utilities;

import android.app.KeyguardManager;
import android.content.Context;

public class DeviceUtility {

    /**
     * Tells if phone is actually unlocked
     * @return Boolean
     */
    public static Boolean phoneIsUnlock(Context context) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return !keyguardManager.isKeyguardLocked(); //Ask android is phone is unlocked
    }

}
