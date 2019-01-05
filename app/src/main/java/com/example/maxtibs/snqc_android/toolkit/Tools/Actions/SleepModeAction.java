package com.example.maxtibs.snqc_android.toolkit.Tools.Actions;

import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.maxtibs.snqc_android.utilities.TimeRange;

import java.util.Calendar;

import static android.content.Intent.ACTION_USER_PRESENT;

public class SleepModeAction extends BroadcastReceiver {

    public static final String TIMEOUT = "SleepModeAction.timeout";
    public static final String REMINDER = "SleepModeAction.reminder";
    public static final String SNOOZE = "SleepModeAction.snooze";
    private static TimeRange timeRange;
    public static PendingIntent phoneUnlockIntent;
    public static PendingIntent timeoutIntent;
    public static PendingIntent reminderIntent;

    @Override
    public void onReceive(Context context, Intent intent) {

        //Error
        if(timeRange == null) {
            Log.e("SleepModeAction", "timeRange has not been set. Return.");
            return;
        }

        //Action handlers
        String intentAction = intent.getAction();
        if(intentAction == null) return;
        switch (intentAction) {
            case ACTION_USER_PRESENT: //Android event: Phone got unlocked
                phoneUnlockAction(context);
                break;
            case TIMEOUT: //Custom event: SleepMode alarm timeout
                timeoutAction(context);
                break;
            case REMINDER: //Custom event: Reminder alarm timeout
                //Check if phone unlock. If so, notify user to close its phone
                if(phoneIsUnlock(context)) {
                    SleepModeNotification.notify(context);
                    setReminder(context);
                }
                break;
            case SNOOZE:
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                notificationManager.cancel(0);
                break;
        }
    }

    private void setReminder(Context context) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(SleepModeAction.reminderIntent);

        //Create new alarm
        Intent intent = new Intent(context, SleepModeAction.class);
        intent.setAction(REMINDER);
        SleepModeAction.reminderIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        //Set next alarm
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            Calendar.getInstance().getTimeInMillis() + 1000*10, // 10 min
            SleepModeAction.reminderIntent
        );
    }

    /**
     * Tells if phone is actually unlocked
     * @return
     */
    private Boolean phoneIsUnlock(Context context) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return !keyguardManager.isKeyguardLocked(); //Ask android is phone is unlocked
    }

    /**
     * Tells if time 'now' is in time range
     * @Return
     */
    private Boolean nowIsInRange() {
        Calendar now = Calendar.getInstance();
        return timeRange.isInRange(now); //Compare now to timeRange
    }

    /**
     * Defines the action to do when receiving phone unlock event
     * @param context
     */
    private void phoneUnlockAction(Context context) { //keygard is gone. No need to check if phone is unlock. It is.
        //Check if now is in time range. If so, notify.
        if(nowIsInRange()) {
            SleepModeNotification.notify(context);
            //Recall in x min
        }
    }

    /**
     * Defines the action to do when timeout happen
     * @param context
     */
    private void timeoutAction(Context context) {
        Calendar now = Calendar.getInstance();

        //Check if phone unlock. If so, notify user to close its phone
        if(phoneIsUnlock(context)) {
            SleepModeNotification.notify(context);
            setReminder(context);
        }

        //Schedule next timeoutAction
        timeRange.incrementDay();

        Log.d("SleepModeAction", now.getTime().toString() + " \n Scheduling next timeout alarm at " + timeRange.getMin().getTime().toString());

        //Cancel alarm if already exists
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(SleepModeAction.timeoutIntent);

        //Create new alarm
        Intent intent = new Intent(context, SleepModeAction.class);
        intent.setAction(TIMEOUT);
        SleepModeAction.timeoutIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        //Set next alarm
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            timeRange.getMin().getTimeInMillis(),
            SleepModeAction.timeoutIntent
        );
    }

    //Getters/Setters
    public static void setTimeRange(TimeRange tr) { timeRange = tr; }

    /**
     * Set min time and re-create alarm
     * @param context
     * @param hour
     * @param minute
     */
    public static void setTimeRangeMin(Context context, int hour, int minute) {

        //Create min value for today. This let TimeRange adjust Max value
        Calendar min = Calendar.getInstance();
        min.set(Calendar.HOUR_OF_DAY, hour);
        min.set(Calendar.MINUTE, minute);
        min.set(Calendar.SECOND, 0);

        timeRange.updateMin(min);
        Log.d("SleepModeAction", "Changing min to " + getRangeMin());

        //Cancel alarm if already exists
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(SleepModeAction.timeoutIntent);
        Log.d("SleepModeAction", "Cancelling old alarm");

        //Re-create alarm
        Intent intent = new Intent(context, SleepModeAction.class);
        intent.setAction(TIMEOUT);
        SleepModeAction.timeoutIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                timeRange.getMin().getTimeInMillis(),
                SleepModeAction.timeoutIntent
        );
        Log.d("SleepModeAction", "Creating new alarm");

    }
    public static void setTimeRangeMax( int hour, int minute) {
        Calendar max = Calendar.getInstance();
        max.set(Calendar.HOUR_OF_DAY, hour);
        max.set(Calendar.MINUTE, minute);

        timeRange.updateMax(max);
    }

    public static TimeRange getTimeRange() {
        return timeRange;
    }

    public static String getRangeMin() {
        if(timeRange == null) return "None";
        else return timeRange.getMin().getTime().toString();
    }
    public static String getRangeMax() {
        if(timeRange == null) return "None";
        else return timeRange.getMax().getTime().toString();
    }
}