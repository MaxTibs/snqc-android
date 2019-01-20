package com.example.maxtibs.snqc_android.toolkit.Tools.SleepMode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

import com.example.maxtibs.snqc_android.Utilities.Notification;

import static android.content.Intent.ACTION_SCREEN_OFF;

public class SleepModeNotification extends BroadcastReceiver{

    public static final String CHANID = "0";

    public static void notify(Context context) {

        dismiss(context, CHANID);

        String quickMsg = "Fermez l'écran";
        String msg = "Fermez l'écran.\nRappel prévu à " + SleepModeModel.getNextReminderDate(context) + "\n\nCliquez sur la notification pour accéder aux paramètres.";

        //Send notification to user
        Notification notification = new Notification(context, CHANID, "Chan", "desc");
        notification.setDefaultNotification(context, "SNQC - Mode sommeil", quickMsg, msg, new Intent(context, SleepModeActivity.class));

        notification.push(context);
    }

    public static void dismiss(Context context, String channel) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(Integer.valueOf(channel)); //Dismiss notification
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        switch (action) {
            case(ACTION_SCREEN_OFF):
                dismiss(context, SleepModeNotification.CHANID); //TODO: Doesn't work on lollipop API 21. Working api HIGHER?
                break;
        }
    }
}
