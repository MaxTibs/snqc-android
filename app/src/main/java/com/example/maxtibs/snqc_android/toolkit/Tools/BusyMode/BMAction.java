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

    private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;

    @Override
    protected boolean canExecute() {
        String intentAction = intent.getAction();
        boolean isActivate  = BusyModeModel.isActivate(context);
        boolean intentOK    = intentAction.equals(ACTION_USER_PRESENT) || intentAction.equals(REMINDER);
        boolean phoneUnlock = DeviceUtility.phoneIsUnlock(context);

        return isActivate && intentOK && phoneUnlock;
    }

    @Override
    protected void execute() {
        //Notify
        BusyModeNotification.notify(context);
    }

    @Override
    protected boolean canReschedule() {
        boolean isActivate  = BusyModeModel.isActivate(context);
        boolean phoneUnlock = DeviceUtility.phoneIsUnlock(context);

        return isActivate && phoneUnlock;
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
        nextReminder.setTimeInMillis(Calendar.getInstance().getTimeInMillis() + 1000*60* BusyModeModel.getReminderDelay(context));
        String date = new SimpleDateFormat("H'h'mm").format(nextReminder.getTime());
        BusyModeModel.setNextReminderDate(context, date);

        //Set next alarm
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                nextReminder.getTimeInMillis(),
                alarm
        );
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
        onSharedPreferenceChangeListener = new
                SharedPreferences.OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                        switch (key){
                            case BusyModeModel.SWITCH_STATE:
                            case BusyModeModel.REMINDER_DELAY:
                                cancel();
                                BusyModeNotification.dismiss(context);
                                forceBroadcastOnReceiver();
                                break;
                        }
                    }
                };
        LocalStorage.getSharedPreferences(context, BusyModeModel.FILENAME).registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

}
