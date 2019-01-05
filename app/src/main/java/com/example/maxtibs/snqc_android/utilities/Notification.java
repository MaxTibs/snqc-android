package com.example.maxtibs.snqc_android.utilities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.maxtibs.snqc_android.R;

public class Notification {

    public NotificationChannel channel;
    public NotificationCompat.Builder builder;

    public int priority = NotificationCompat.PRIORITY_MAX;
    private String _channelId;

    public Notification(Context context, String channelId, String channelName, String channelDesc) {
        this._channelId = channelId;
        createNotificationChannel(context, channelId, channelName, channelDesc);
    }

    public void setDefaultNotification(Context context, String title, String message) {
        this.builder = new NotificationCompat.Builder(context, this._channelId)
                .setPriority(android.app.Notification.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setDefaults(NotificationCompat.DEFAULT_SOUND | NotificationCompat.DEFAULT_VIBRATE);
    }

    public void push(Context context) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(Integer.valueOf(this._channelId), this.builder.build());
    }

    public void createNotificationChannel(Context context, String id, String chanName, String desc) {

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name =  chanName;
            String description = desc;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            this.channel = new NotificationChannel(id, name, importance);
            this.channel.setDescription(description);
            this.channel.setLockscreenVisibility(android.app.Notification.VISIBILITY_PUBLIC);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(this.channel);
        }
    }

}
