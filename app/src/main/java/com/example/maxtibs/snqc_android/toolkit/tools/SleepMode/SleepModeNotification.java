package com.example.maxtibs.snqc_android.toolkit.tools.SleepMode;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

import com.example.maxtibs.snqc_android.R;
import com.example.maxtibs.snqc_android.utilities.Notification;

public class SleepModeNotification extends BroadcastReceiver{

    private static final String SNOOZE = "SleepModeNotification.snooze";
    private static final String LOCK = "SleepModeNotification.lock";

    public static final String CHANID = "0";

    public static void notify(Context context) {

        String msg = "Fermer l'écran pour que les notifications cessent.\nProchain rappel dans 15 minutes.\nVOus pouvez également désactiver la fonctionnalité pour aujourd'hui";

        //Send notification to user
        Notification notification = new Notification(context, CHANID, "Chan", "desc");
        notification.setDefaultNotification(context, "SNQC - Mode sommeil", msg);

        Intent snooze = new Intent(context, SleepModeNotification.class);
        snooze.setAction(SNOOZE);
        /*Intent lockScreen = new Intent(context, SleepModeNotification.class);
        lockScreen.setAction(LOCK);*/

        PendingIntent snoozePending = PendingIntent.getBroadcast(context, 0, snooze, 0);
        PendingIntent lockScreenPending = PendingIntent.getBroadcast(context, 0, snooze, 0);

        notification.builder.addAction(R.drawable.ic_snooze, "Fermer l'écran", snoozePending);
        notification.builder.addAction(R.drawable.ic_snooze, "Désactiver", snoozePending);
        //notification.builder.addAction(R.drawable.ic_snooze, "Fermer l'écran", lockScreenPending);
        //.setPriority(android.app.Notification.PRIORITY_HIGH)
        //.setSmallIcon(R.drawable.ic_notifications_black_24dp)
        //.setContentTitle(title)
        //.setContentText(message)
        //.setDefaults(NotificationCompat.DEFAULT_SOUND | NotificationCompat.DEFAULT_VIBRATE);

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
            case (LOCK):
                break;
        }
    }
}
