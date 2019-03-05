package com.example.maxtibs.snqc_android.toolkit.Tools.BusyMode;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.maxtibs.snqc_android.toolkit.Action;
import com.example.maxtibs.snqc_android.utilities.DeviceUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.example.maxtibs.snqc_android.utilities.LocalStorage;

import static android.content.Intent.ACTION_USER_PRESENT;

public class BMAction extends Action {

    private static final String REMINDER = "BusyModeLifecycle.reminder";
    private PendingIntent alarm;

    private static SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;

    @Override
    protected boolean canExecute() {
        String intentAction = intent.getAction();
        boolean isActivate  = BMModel.isActivate(context);
        boolean intentOK    = intentAction.equals(ACTION_USER_PRESENT) || intentAction.equals(REMINDER);
        boolean phoneUnlock = DeviceUtility.phoneIsUnlock(context);

        return isActivate && intentOK && phoneUnlock;
    }

    @Override
    protected void execute() {
        //Do nothing
    }

    @Override
    protected boolean canReschedule() {
        return true;
    }

    @Override
    protected void reschedule() {
        //Notify later
        //Cancel any other reminder alarm
        cancel();

        //Create new alarm
        Intent intent = new Intent(context, getClass());
        intent.setAction(REMINDER);
        alarm = PendingIntent.getBroadcast(context, 0, intent, 0);

        //Next reminder is now + REMINDER_DELAY
        Calendar nextReminder = Calendar.getInstance();
        nextReminder.setTimeInMillis(Calendar.getInstance().getTimeInMillis() + 1000*60* BMModel.getReminderDelay(context));
        String date = new SimpleDateFormat("H'h'mm").format(nextReminder.getTime());
        BMModel.setNextReminderDate(context, date);

        //Set next alarm
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                nextReminder.getTimeInMillis(),
                alarm
        );

        //Rebuild notification
        BMNotification.dismiss(context);
        BMNotification.build(context);
    }

    private void cancel() {
        if(alarm != null) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(alarm);
        }
    }

    private void forceBroadcastOnReceiver() {
        Intent intent = new Intent();
        intent.setAction(REMINDER);
        context.sendBroadcast(intent);
    }

    public void setContext(Context c) {
        this.context = c;
        if(onSharedPreferenceChangeListener != null) return;
        onSharedPreferenceChangeListener = new
                SharedPreferences.OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                        switch (key){
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
