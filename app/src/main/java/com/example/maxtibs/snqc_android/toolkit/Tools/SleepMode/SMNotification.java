package com.example.maxtibs.snqc_android.toolkit.Tools.SleepMode;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

import com.example.maxtibs.snqc_android.utilities.Notification;

public class SMNotification {

    public static final String CHANID = "0";

    public static void build(Context context) {

        String quickMsg = "Fermez l'écran";
        String msg = "Fermez l'écran.\nRappel prévu à " + SMModel.getNextReminderDate(context) + "\n\nCliquez sur la notification pour accéder aux paramètres.";

        //Send notification to user
        Notification notification = new Notification(context, CHANID, "Chan", "desc");
        notification.setDefaultNotification(context, "SNQC - Mode sommeil", quickMsg, msg, new Intent(context, SMActivity.class));

        notification.push(context);
    }

    public static void dismiss(Context context) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(Integer.valueOf(CHANID)); //Dismiss notification
    }

}
