package com.example.maxtibs.snqc_android.toolkit.Tools.BusyMode;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

import com.example.maxtibs.snqc_android.utilities.Notification;

public class BusyModeNotification {

    public static final String CHANID = "1";

    public static void notify(Context context) {

        dismiss(context);

        String quickMsg = "Fermez l'écran";
        String msg = "Fermez l'écran.\nRappel prévu à " + BusyModeModel.getNextReminderDate(context) + "\n\nCliquez sur la notification pour accéder aux paramètres.";

        //Send notification to user
        Notification notification = new Notification(context, CHANID, "Chan", "desc");
        notification.setDefaultNotification(context, "SNQC - Mode occupé", quickMsg, msg, new Intent(context, BusyModeActivity.class));

        notification.push(context);
    }

    public static void dismiss(Context context) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(Integer.valueOf(CHANID)); //Dismiss notification
    }

   /* @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        switch (action) {
            case(ACTION_SCREEN_OFF):
                dismiss(context, com.example.maxtibs.snqc_android.toolkit.Tools.BusyMode.BusyModeNotification.CHANID); //TODO: Doesn't work on lollipop API 21. Working api HIGHER?
                break;
        }
    }*/

}
