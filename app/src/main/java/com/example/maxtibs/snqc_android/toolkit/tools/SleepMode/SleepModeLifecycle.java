package com.example.maxtibs.snqc_android.toolkit.tools.SleepMode;

import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.maxtibs.snqc_android.utilities.DayTime;
import com.example.maxtibs.snqc_android.utilities.TimeRange;

import java.util.Calendar;

import static android.content.Intent.ACTION_USER_PRESENT;

public class SleepModeLifecycle extends BroadcastReceiver {

    private static final String DTAG = "SleepModeLifeCycle";

    //Actions
    public static final String TIMEOUT = "SleepModeLifecycle.timeout";
    public static final String REMINDER = "SleepModeLifecycle.reminder";
    public static final String SNOOZE = "SleepModeLifecycle.snooze";
    public static final String LOCK_SCREEN = "SleepModeLifecycle.lock";

    //Intents
    public static PendingIntent phoneUnlockIntent;
    public static PendingIntent timeoutIntent;
    public static PendingIntent reminderIntent;

    public static final int REMINDER_DELAY = 1000*60*15; //15 min

    //State
    @Override
    public void onReceive(Context context, Intent intent) {

        //Error
        if(SleepModeModel.getTimeRange(context) == null) {
            Log.e(DTAG, "No timeRange has been set. Return.");
            return;
        }

        //First, we need to check if we're in range
        TimeRange tr = SleepModeModel.getTimeRange(context);
        Calendar now = Calendar.getInstance();
        Calendar min = dayTimeNow(tr.getMin());
        Calendar max = dayTimeNow(tr.getMax());
        if(max.compareTo(min) < 0) max.add(Calendar.DAY_OF_YEAR, 1); //Fix range
        if(now.compareTo(min) >= 0 && now.compareTo(max) < 0) {
            //Action handlers
            String intentAction = intent.getAction();
            if (intentAction == null) return;
            switch (intentAction) {
                case ACTION_USER_PRESENT: //Android event: Phone got unlocked
                    //phoneUnlockAction(context);
                    break;
                case TIMEOUT: //Custom event: SleepModeController alarm timeout
                    timeoutAction(context);
                    break;
                case REMINDER: //Custom event: Reminder alarm timeout
                    //Check if phone unlock. If so, notify user to close its phone
                    if (phoneIsUnlock(context)) {
                        SleepModeNotification.notify(context);
                        setReminder(context);
                    }
                    break;
                case SNOOZE:
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                    notificationManager.cancel(0);
                    break;
                case LOCK_SCREEN:
                    break;
            }
        } else { //Blackhole. Every event that enters here will never schedule anything next
            //Debug
            Log.d(DTAG, "Now not in range");
        }
    }

    /**
     * Creates a Notification reminder to triggers 15 minutes later
     * @param context
     */
    private void setReminder(Context context) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(SleepModeLifecycle.reminderIntent); //Cancel if already exists

        //Create new alarm
        Intent intent = new Intent(context, this.getClass());
        intent.setAction(REMINDER);
        SleepModeLifecycle.reminderIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        //Next reminder is now + 15 minutes
        Calendar nextReminder = Calendar.getInstance();
        nextReminder.setTimeInMillis(Calendar.getInstance().getTimeInMillis() + REMINDER_DELAY);
        Log.d(DTAG, "Next reminder at " + nextReminder.getTime().toString());

        //Set next alarm
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            nextReminder.getTimeInMillis(), // 15 min
            SleepModeLifecycle.reminderIntent
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

        //Schedule next timeoutAction. Before, increment 1 day
        Calendar startTime = dayTimeNow(SleepModeModel.getTimeRange(context).getMin());
        startTime.add(Calendar.DAY_OF_YEAR, 1); //INCREMENT

        Log.d(DTAG, now.getTime().toString() + " \n Scheduling next timeout alarm at " + startTime.getTime().toString());

        //Cancel alarm if already exists
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(SleepModeLifecycle.timeoutIntent);

        //Create new alarm
        Intent intent = new Intent(context, SleepModeLifecycle.class);
        intent.setAction(TIMEOUT);
        SleepModeLifecycle.timeoutIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        //Set next alarm
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
                startTime.getTimeInMillis(),
            SleepModeLifecycle.timeoutIntent
        );
    }

    /**
     * Cancel and rebuild alarms: TimeoutIntent
     * @param context current context
     */
    public static void cancelAndRebuild(Context context) {

        //TODO: cancel every alarms?

        //Cancel alarm if already exists
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(SleepModeLifecycle.timeoutIntent);
        Log.d(DTAG, "Cancelling old alarm");

        //Get time now from DayTime
        Calendar startTime = dayTimeNow(SleepModeModel.getTimeRange(context).getMin());

        //Re-create alarm
        Intent intent = new Intent(context, SleepModeLifecycle.class);
        intent.setAction(TIMEOUT);
        SleepModeLifecycle.timeoutIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                startTime.getTimeInMillis(),
                SleepModeLifecycle.timeoutIntent
        );
        Log.d(DTAG, "Creating new alarm");
    }

    private static Calendar dayTimeNow(DayTime dayTime) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, dayTime.getHour());
        now.set(Calendar.MINUTE, dayTime.getMinute());
        now.set(Calendar.SECOND, 0);
        return now;
    }
}