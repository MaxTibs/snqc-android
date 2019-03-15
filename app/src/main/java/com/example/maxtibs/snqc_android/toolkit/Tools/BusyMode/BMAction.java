package com.example.maxtibs.snqc_android.toolkit.Tools.BusyMode;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.maxtibs.snqc_android.toolkit.Action;
import com.example.maxtibs.snqc_android.toolkit.Tools.SleepMode.SMModel;
import com.example.maxtibs.snqc_android.utilities.DeviceUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.example.maxtibs.snqc_android.utilities.LocalStorage;

import static android.content.Intent.ACTION_USER_PRESENT;

/**
 *
 * @author Maxime Thibault
 *
 * Notify the user if he uses its phone when the timer is on.
 */
public class BMAction extends Action {

    private static final String REMINDER = "BusyModeLifecycle.reminder";
    private PendingIntent alarm;

    private static SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;
    private static Calendar nextReminder;

    @Override
    protected boolean canExecute() {
        /**
         * Multiple rules:
         * 1. BusyMode has to be activated
         * 2. The intent has to match ACTION_USER_PRESENT or THIS ID (REMINDER)
         * 3. The phone has to be unlock.. indeed TODO: Not sure it is necessary
         * 4. SleepMode has to be out of its range
         */
        String intentAction = intent.getAction();
        boolean isActivate  = BMModel.isActivate(context);
        boolean intentOK    = intentAction.equals(ACTION_USER_PRESENT) || intentAction.equals(REMINDER);
        boolean phoneUnlock = DeviceUtility.phoneIsUnlock(context);
        boolean sleepModeOFF = !(SMModel.isActivate(context) && SMModel.getTimeRange(context).isInRange(Calendar.getInstance()));

        return isActivate && intentOK && phoneUnlock && sleepModeOFF;
    }

    @Override
    protected void execute() {
        /**
         * Risky if reschedule failed...
         * 1.Define next reminder date
         * 2.Update notification
         */
        //Next reminder is now + REMINDER_DELAY
        nextReminder = Calendar.getInstance();
        nextReminder.setTimeInMillis(Calendar.getInstance().getTimeInMillis() + 1000*60* BMModel.getReminderDelay(context));
        String date = new SimpleDateFormat("H'h'mm").format(nextReminder.getTime());
        BMModel.setNextReminderDate(context, date);
        //update
        BMNotification.dismiss(context);
        BMNotification.build(context);

    }

    @Override
    protected boolean canReschedule() {
        //Always
        return true;
    }

    @Override
    protected void reschedule() {
        //Cancel any other reminder alarm
        cancel();

        //Create new alarm
        Intent intent = new Intent(context, getClass());
        intent.setAction(REMINDER);
        alarm = PendingIntent.getBroadcast(context, 0, intent, 0);

        //Set next alarm
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                nextReminder.getTimeInMillis(),
                alarm
        );
    }

    /**
     * Cancel reminder
     */
    private void cancel() {
        if(alarm != null) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(alarm);
        }
    }

    /**
     * Force THIS to execute
     */
    private void forceBroadcastOnReceiver() {
        Intent intent = new Intent();
        intent.setAction(REMINDER);
        context.sendBroadcast(intent);
    }

    /**
     * Set the context of this action.
     * It is necessary to listen any changes of SleepMode's SharedPreferences
     */
    public void setContext(Context c) {
        this.context = c;
        if(onSharedPreferenceChangeListener != null) return;
        onSharedPreferenceChangeListener = new
                SharedPreferences.OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                        switch (key){
                            /**
                             * If BusyMode activation's state change or reminder delay change,
                             * 1. Cancel the notification
                             * 2. Force THIS to re-execute.
                             * "This is a kind of reset for the notification"
                             */
                            case BMModel.SWITCH_STATE:
                            case BMModel.REMINDER_DELAY:
                                cancel();
                                BMNotification.dismiss(context);
                                forceBroadcastOnReceiver();
                                break;
                        }
                    }
                };
        LocalStorage.getSharedPreferences(context, BMModel.FILENAME).registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

}
