package com.example.maxtibs.snqc_android.toolkit.Tools.SleepMode;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.maxtibs.snqc_android.toolkit.Action;
import com.example.maxtibs.snqc_android.utilities.LocalStorage;

import java.util.Calendar;

public class SMDailyAction extends Action {

    private PendingIntent alarm;
    private static SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;
    public static final String DAILY_ACTION = "SMDailyAction";

    private SMWarningAction smWarningAction;

    @Override
    protected boolean canExecute() {
        String intentAction = intent.getAction();
        boolean isActivate  = SMModel.isActivate(context);
        boolean intentOK    = intentAction.equals(DAILY_ACTION);
        return isActivate && intentOK;
    }

    @Override
    protected void execute() {
        //Trigger WARNING ACTION now
        Intent intent = new Intent();
        intent.setAction(SMWarningAction.WARNING_ACTION);
        context.sendBroadcast(intent);
    }

    @Override
    protected boolean canReschedule() {
        return true;
    }

    @Override
    protected void reschedule() {
        //Schedule next timeoutAction 1 day later
        Calendar startTime = SMModel.getTimeRange(context).getCalendarMin();
        startTime.add(Calendar.DAY_OF_YEAR, 1); //INCREMENT

        //Cancel alarm if already exists
        cancel();

        //Create new alarm
        Intent intent = new Intent(context, getClass());
        intent.setAction(DAILY_ACTION);
        alarm = PendingIntent.getBroadcast(context, 0, intent, 0);

        //Set next alarm
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                startTime.getTimeInMillis(),
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
        intent.setAction(DAILY_ACTION);
        context.sendBroadcast(intent);
    }

    public void setContext(Context c) {
        this.context = c;
        smWarningAction = new SMWarningAction();
        smWarningAction.setContext(c);
        if(onSharedPreferenceChangeListener != null) return;
                onSharedPreferenceChangeListener = new
                SharedPreferences.OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                        switch (key){
                            case SMModel.SWITCH_STATE:
                            case SMModel.START_TIME_HOUR:
                            case SMModel.END_TIME_HOUR:
                                cancel();
                                forceBroadcastOnReceiver(); //Rebuild the alarm
                                break;
                        }
                    }
                };
        LocalStorage.getSharedPreferences(context, SMModel.FILENAME).registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }
}
