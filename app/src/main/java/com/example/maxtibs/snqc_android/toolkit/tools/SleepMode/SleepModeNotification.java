package com.example.maxtibs.snqc_android.toolkit.tools.SleepMode;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

import com.example.maxtibs.snqc_android.R;
import com.example.maxtibs.snqc_android.utilities.Notification;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SleepModeNotification extends BroadcastReceiver{

    private static final String SNOOZE = "SleepModeNotification.snooze";
    private static final String DEACTIVATE = "SleepModeNotification.deactivate";
    private static final String REACTIVATE = "SleepModeNotification.activate";

    public static final String CHANID = "0";

    private PendingIntent reactivateIntent;

    public static void notify(Context context) {

        dismiss(context, CHANID);

        Date date = SleepModeLifecycle.reminderDate.getTime();
        String display = new SimpleDateFormat("H'h'mm").format(date);
        String quickMsg = "Fermez l'écran.";
        String msg = "Fermez l'écran.\nRappel prévu à " + display + "\n\nCliquez sur la notification pour accéder aux paramètres.";

        //Send notification to user
        Notification notification = new Notification(context, CHANID, "Chan", "desc");
        notification.setDefaultNotification(context, "SNQC - Mode sommeil", quickMsg, msg);

       /* Intent snooze = new Intent(context, SleepModeNotification.class);
        snooze.setAction(SNOOZE);
        Intent deactivate = new Intent(context, SleepModeNotification.class);
        deactivate.setAction(DEACTIVATE);

        PendingIntent snoozePending = PendingIntent.getBroadcast(context, 0, snooze, 0);
        PendingIntent deactivatePending = PendingIntent.getBroadcast(context, 0, snooze, 0);

        notification.builder.addAction(R.drawable.ic_snooze, "Ok", snoozePending);
        notification.builder.addAction(R.drawable.ic_snooze, "Désactiver", deactivatePending);*/

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
            case(SNOOZE):
                dismiss(context, SleepModeNotification.CHANID);
                break;
            case (DEACTIVATE):
                //Create alarm to reactivate
                SleepModeModel.setSwitchState(context, false);
                Intent activateIntent = new Intent(context, this.getClass());
                intent.setAction(REACTIVATE);
                reactivateIntent = PendingIntent.getBroadcast(context, 0, activateIntent, 0);
                dismiss(context, SleepModeNotification.CHANID);
                break;
            case (REACTIVATE):

                break;
        }
    }
}
