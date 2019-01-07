package com.example.maxtibs.snqc_android.toolkit.Tools.Actions;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.maxtibs.snqc_android.R;
import com.example.maxtibs.snqc_android.utilities.Notification;

public class SleepModeNotification {

    public static void notify(Context context) {
        //Send notification to user
        Notification notification = new Notification(context, "0", "Chan", "desc");
        notification.setDefaultNotification(context, "SNQC", "Fermer l'écran");

        Intent snooze = new Intent(context, SleepModeAction.class);
        snooze.setAction(SleepModeAction.SNOOZE);
        Intent lockScreen = new Intent(context, SleepModeAction.class);
        lockScreen.setAction(SleepModeAction.LOCK_SCREEN);

        PendingIntent snoozePending = PendingIntent.getBroadcast(context, 0, snooze, 0);
        PendingIntent lockScreenPending = PendingIntent.getBroadcast(context, 0, lockScreen, 0);

        notification.builder.addAction(R.drawable.ic_snooze, "Rappel (15 minutes)", snoozePending);
        notification.builder.addAction(R.drawable.ic_snooze, "Fermer l'écran", lockScreenPending);
        //.setPriority(android.app.Notification.PRIORITY_HIGH)
        //.setSmallIcon(R.drawable.ic_notifications_black_24dp)
        //.setContentTitle(title)
        //.setContentText(message)
        //.setDefaults(NotificationCompat.DEFAULT_SOUND | NotificationCompat.DEFAULT_VIBRATE);

        notification.push(context);
    }

}
