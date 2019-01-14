package com.example.maxtibs.snqc_android.Utilities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.maxtibs.snqc_android.R;
import com.example.maxtibs.snqc_android.toolkit.Tools.SleepMode.SleepMode;

public class Notification {

    public NotificationChannel channel;
    public NotificationCompat.Builder builder;

    public int priority = NotificationCompat.PRIORITY_MAX;
    private String _channelId;

    /**
     * Constructor
     * @param context current context
     * @param channelId channelID
     * @param channelName channelName
     * @param channelDesc channel description
     */
    public Notification(Context context, String channelId, String channelName, String channelDesc) {
        this._channelId = channelId;
        createNotificationChannel(context, channelId, channelName, channelDesc);
    }

    /**
     * Default notification template
     * @param context
     * @param title
     * @param message
     */
    public void setDefaultNotification(Context context, String title, String quickMsg, String msg) {

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(context, SleepMode.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        this.builder = new NotificationCompat.Builder(context, this._channelId)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setSmallIcon(R.drawable.ic_sleep_icon)
            .setContentTitle(title)
            .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
            .setContentText(quickMsg)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setVisibility(android.app.Notification.VISIBILITY_SECRET)
            .setOngoing(true);
            //.setAutoCancel(true); //false: notification is persistent
    }

    /**
     * Send notification
     * @param context current context
     */
    public void push(Context context) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(Integer.valueOf(this._channelId), this.builder.build());
    }

    /**
     * Create specific channel for notification
     * @param context
     * @param id id of channel
     * @param chanName name of channel
     * @param desc description of channel
     */
    public void createNotificationChannel(Context context, String id, String chanName, String desc) {

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name =  chanName;
            String description = desc;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            this.channel = new NotificationChannel(id, name, importance);
            this.channel.setDescription(description);
            //this.channel.setLockscreenVisibility(android.app.Notification.VISIBILITY_PUBLIC);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(this.channel);
        }
    }

}
