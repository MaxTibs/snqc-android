package com.example.maxtibs.snqc_android.Utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationPublisher extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Notification notification = new Notification(context, "0", "Notification", "none");
        notification.push(context);
    }

}
