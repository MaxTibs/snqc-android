package com.example.maxtibs.snqc_android.utilities;

import android.app.ActivityManager;
import android.content.Context;
import android.util.LruCache;

/**
 * Cache class that uses LruCache. Can be used through out the whole app, it will always be the same instance
 */
public class Cache {

    private static Cache instance;
    private LruCache<Object, Object> lru;

    private Cache(Context context) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int maxKb = am.getMemoryClass() * 1024;
        int limitKb = maxKb / 8; // 1/8th of total ram
        lru = new LruCache<>(limitKb);
    }

    public static Cache getInstance(Context context) {
        if (instance == null) {
            instance = new Cache(context);
        }
        return instance;
    }

    public LruCache<Object, Object> getLru() {
        return lru;
    }
}
