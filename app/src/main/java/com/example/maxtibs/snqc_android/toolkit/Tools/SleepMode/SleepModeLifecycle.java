package com.example.maxtibs.snqc_android.toolkit.Tools.SleepMode;

import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.maxtibs.snqc_android.Utilities.TimeRange;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.Intent.ACTION_USER_PRESENT;

/**
 * This is the SleepModeActivity Lifecycle.
 * 1.TIMEOUT EVENT: Once a day to check if user is using its phone at the start time. This should always trigger and be handle, no matter what.
 *                  If user is using it's phone, he or she is notify and reminder is set.
 * 2.ACTION_USER_EVENT: Is triggered if phone is unlock. This event is then handle to check if phone has been unlock inside the timerange.
 *                      If so, user is notify and reminder is set.
 * 3.REMINDER EVENT: Is activate from TIMEOUT or ACTION_USER_PRESENT if user is using its phone in the timerange
 *                   this event is set to trigger every 15 minutes until phone is close (lock)
 */
public class SleepModeLifecycle extends BroadcastReceiver {

    private static final String DTAG = "SleepModeLifeCycle";

    //EVENTS
    public static final String TIMEOUT = "SleepModeLifecycle.timeout";
    public static final String REMINDER = "SleepModeLifecycle.reminder";

    //Intents (alarms)
    public static PendingIntent timeoutIntent;
    public static PendingIntent reminderIntent;

    //State machine
    @Override
    public void onReceive(Context context, Intent intent) {

        String intentAction = intent.getAction();
        Calendar now = Calendar.getInstance();
        Log.d(DTAG, "State check at " + now.getTime().toString() + " for " + intentAction + " event");

        //If mode not activate, don't do anything
        if(!SleepModeModel.isActivate(context)) { return; }

        //Min and Max as of today
        TimeRange tr = SleepModeModel.getTimeRange(context);
        Calendar min = tr.getCalendarMin();
        Calendar max = tr.getCalendarMax();

        //First, we need to check if we're in range
        if ((now.compareTo(min) >= 0 && now.compareTo(max) < 0) || intentAction.equals(TIMEOUT)) {
            //Handlers (states)
            switch (intentAction) {
                //LEVEL 1 EVENTS
                case ACTION_USER_PRESENT:
                    notifyAndResetReminder(context);
                    break;
                case TIMEOUT: //Custom event: SleepModeLifecycle.timeout
                    timeoutAction(context);
                    break;
                //LEVEL 2 EVENT
                case REMINDER: //Custom event: SleepModeLifecycle.reminder (timeout after 15 min) comes from ACTION_USER_PRESENT or TIMEOUT
                    //Check if phone unlock. If so, notify user to close its phone and set a new reminder
                    notifyAndResetReminder(context);
                    break;
            }
        } else { //Blackhole. Every event that enters here will never schedule anything next
            //Debug
            Log.d(DTAG, "Now is not in TimeRange");
        }
    }

    /**
     * Check if phone is unlock
     * If so, notify user to close its phone and schedule a next reminder
     * @param context current context
     */
    private void notifyAndResetReminder(Context context) {
        //Check if phone unlock. If so, notify user to close its phone
        if(phoneIsUnlock(context)) {
            SleepModeNotification.notify(context);
            setReminder(context);
        }
    }

    /**
     * Defines the action to do when timeout happens
     * @param context current context
     */
    private void timeoutAction(Context context) {

        notifyAndResetReminder(context);

        //Schedule next timeoutAction 1 day later
        Calendar startTime = SleepModeModel.getTimeRange(context).getCalendarMin();
        startTime.add(Calendar.DAY_OF_YEAR, 1); //INCREMENT

        //Cancel alarm if already exists
        if(SleepModeLifecycle.timeoutIntent != null)
            cancelAlarm(context, SleepModeLifecycle.timeoutIntent);

        //Create new alarm
        Intent intent = new Intent(context, SleepModeLifecycle.class);
        intent.setAction(TIMEOUT);
        SleepModeLifecycle.timeoutIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        //Set next alarm
        setAlarm(context, startTime.getTimeInMillis(), SleepModeLifecycle.timeoutIntent);
        Log.d(DTAG, "timeoutAction:Timeout alarm set to trigger at " + startTime.getTime().toString());
    }

    /**
     * Cancel and rebuild alarms: TimeoutIntent
     * @param context current context
     */
    public static void rebuild(Context context) {

        SleepModeNotification.dismiss(context, SleepModeNotification.CHANID); //Dismiss notification

        //Cancel alarm if already exists
        if(SleepModeLifecycle.timeoutIntent != null)
            cancelAlarm(context, SleepModeLifecycle.timeoutIntent);

        Log.d(DTAG, "Rebuild:Cancelling everything");

        //If not activate, return
        if(!SleepModeModel.isActivate(context)) return;

        //Re-create alarm
        Calendar startTime = SleepModeModel.getTimeRange(context).getCalendarMin();
        Intent intent = new Intent(context, SleepModeLifecycle.class);
        intent.setAction(TIMEOUT);
        SleepModeLifecycle.timeoutIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        setAlarm(context, startTime.getTimeInMillis(), SleepModeLifecycle.timeoutIntent);
        Log.d(DTAG, "Rebuild:Re-Creating new alarm to trigger at " + startTime.getTime().toString());
    }


    /**
     * Creates an alarm to triggers 15 minutes from now
     * @param context current context
     */
    public static void setReminder(Context context) {

        //Cancel any other reminder alarm
        if(SleepModeLifecycle.reminderIntent != null)
            cancelAlarm(context, SleepModeLifecycle.reminderIntent);

        //Create new alarm
        Intent intent = new Intent(context, SleepModeLifecycle.class);
        intent.setAction(REMINDER);
        SleepModeLifecycle.reminderIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        //Next reminder is now + REMINDER_DELAY
        Calendar nextReminder = Calendar.getInstance();
        nextReminder.setTimeInMillis(Calendar.getInstance().getTimeInMillis() + 1000*60*SleepModeModel.getReminderDelay(context));
        String date = new SimpleDateFormat("H'h'mm").format(nextReminder.getTime());
        SleepModeModel.setNextReminderDate(context, date);
        //Set next alarm
        setAlarm(context, nextReminder.getTimeInMillis(), SleepModeLifecycle.reminderIntent);
        Log.d(DTAG, "Next reminder set to trigger at " + nextReminder.getTime().toString());
    }

    /**
     * Tells if phone is actually unlocked
     * @return Boolean
     */
    private Boolean phoneIsUnlock(Context context) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return !keyguardManager.isKeyguardLocked(); //Ask android is phone is unlocked
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