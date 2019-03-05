package com.example.maxtibs.snqc_android.toolkit.Tools.SleepMode;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.maxtibs.snqc_android.toolkit.Action;
import com.example.maxtibs.snqc_android.utilities.LocalStorage;
import com.example.maxtibs.snqc_android.utilities.TimeRange;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.Intent.ACTION_SCREEN_OFF;

public class SMWarningAction extends Action {

    private PendingIntent alarm;
    private static SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;
    public static final String WARNING_ACTION = "SMWarningAction";

    @Override
    protected boolean canExecute() {
        String intentAction = intent.getAction();
        Calendar now = Calendar.getInstance();

        //Min and Max as of today
        TimeRange tr = SMModel.getTimeRange(context);
        Calendar min = tr.getCalendarMin();
        Calendar max = tr.getCalendarMax();

        boolean isActivate = SMModel.isActivate(context);
        boolean isInRange = (now.compareTo(min) >= 0 && now.compareTo(max) < 0);
        boolean intentOK = intentAction.equals(WARNING_ACTION) || intentAction.equals(ACTION_SCREEN_OFF);

        return isActivate && isInRange && intentOK;
    }

    @Override
    protected void execute() {
        SMNotification.dismiss(context);
        SMNotification.build(context);
    }

    @Override
    protected boolean canReschedule() {
        return true;
    }

    @Override
    protected void reschedule() {
        //Cancel any other reminder alarm
        cancel();

        //Create new alarm
        Intent intent = new Intent(context, getClass());
        intent.setAction(WARNING_ACTION);
        alarm = PendingIntent.getBroadcast(context, 0, intent, 0);

        //Next reminder is now + REMINDER_DELAY
        Calendar nextReminder = Calendar.getInstance();
        nextReminder.setTimeInMillis(Calendar.getInstance().getTimeInMillis() + 1000*60* SMModel.getReminderDelay(context));
        String date = new SimpleDateFormat("H'h'mm").format(nextReminder.getTime());
        SMModel.setNextReminderDate(context, date);

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
        intent.setAction(WARNING_ACTION);
        context.sendBroadcast(intent);
    }

    public void setContext(final Context c) {
        this.context = c;
        if(onSharedPreferenceChangeListener != null) return;
        onSharedPreferenceChangeListener = new
                SharedPreferences.OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                        switch (key){
                            case SMModel.SWITCH_STATE:
                                cancel();
                                SMNotification.dismiss(context);
                                break;
                            case SMModel.REMINDER_DELAY:
                                cancel();
                                reschedule();
                                SMNotification.dismiss(context);
                                SMNotification.build(context);
                                break;
                        }
                    }
                };
        LocalStorage.getSharedPreferences(context, SMModel.FILENAME).registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }


}
