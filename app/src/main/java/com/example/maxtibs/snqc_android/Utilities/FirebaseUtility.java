package com.example.maxtibs.snqc_android.utilities;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class FirebaseUtility {

    private static FirebaseAnalytics mFirebaseAnalytics;

    /**
     * Call this function in the first activity to initialize Firebase Analytics
     * @param context: current context of the app
     */
    public static void initializeAnalytics(Context context) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    public static void logExemple() {
        Bundle bundle = new Bundle();
       // bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        //bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
}
