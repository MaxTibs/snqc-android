package com.example.maxtibs.snqc_android.toolkit.Tools.BusyMode;

import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.Intent.ACTION_USER_PRESENT;

public class BusyModeLifeCycle extends BroadcastReceiver {

    private static PendingIntent reminderIntent;

    private static final String DTAG = "BusyModeLifecycle";
    public static final String REMINDER = "BusyModeLifecycle.reminder";

    //State machine
    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();

        if(!BusyModeModel.isActivate(context)) return;
        if(intentAction.equals(ACTION_USER_PRESENT) || intentAction.equals(REMINDER)) {
            if(phoneIsUnlock(context)) notifyAndResetReminder(context);
        }
    }

    /**
     * Check if phone is unlock
     * If so, notify user to close its phone and schedule a next reminder
     * @param context current context
     */
    private static void notifyAndResetReminder(Context context) {
        //Check if phone unlock. If so, notify user to close its phone
            BusyModeNotification.notify(context);
            setReminder(context);
    }

    /**
     * Tells if phone is actually unlocked
     * @return Boolean
     */
    private static Boolean phoneIsUnlock(Context context) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return !keyguardManager.isKeyguardLocked(); //Ask android is phone is unlocked
    }


    /**
     * Creates an alarm to triggers 15 minutes from now
     * @param context current context
     */
    public static void setReminder(Context context) {

        //Cancel any other reminder alarm
        if(BusyModeLifeCycle.reminderIntent != null)
            cancelAlarm(context, BusyModeLifeCycle.reminderIntent);

        //Create new alarm
        Intent intent = new Intent(context, BusyModeLifeCycle.class);
        intent.setAction(REMINDER);
        BusyModeLifeCycle.reminderIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        //Next reminder is now + REMINDER_DELAY
        Calendar nextReminder = Calendar.getInstance();
        nextReminder.setTimeInMillis(Calendar.getInstance().getTimeInMillis() + 1000*60* BusyModeModel.getReminderDelay(context));
        String date = new SimpleDateFormat("H'h'mm").format(nextReminder.getTime());
        BusyModeModel.setNextReminderDate(context, date);
        //Set next alarm
        setAlarm(context, nextReminder.getTimeInMillis(), BusyModeLifeCycle.reminderIntent);
        Log.d(DTAG, "Next reminder set to trigger at " + nextReminder.getTime().toString());
    }

    /**
     * Cancels alarm
     * @param context current context
     * @param pendingIntent alarm to cancel
     */
    private static void cancelAlarm(Context context, PendingIntent pendingIntent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    /**
     * Sets an alarm to trigger at exact ms time
     * @param context current context
     * @param timeInMs time in ms
     * @param pendingIntent alarm intent
     */
    private static void setAlarm(Context context, long timeInMs, PendingIntent pendingIntent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                timeInMs,
                pendingIntent
        );
    }
}
