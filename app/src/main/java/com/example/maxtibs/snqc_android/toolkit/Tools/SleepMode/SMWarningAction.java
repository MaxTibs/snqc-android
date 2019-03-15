package com.example.maxtibs.snqc_android.toolkit.Tools.SleepMode;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.maxtibs.snqc_android.toolkit.Action;
import com.example.maxtibs.snqc_android.toolkit.Tools.BusyMode.BMNotification;
import com.example.maxtibs.snqc_android.utilities.LocalStorage;
import com.example.maxtibs.snqc_android.utilities.TimeRange;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.Intent.ACTION_USER_PRESENT;

/**
 *
 * @author Maxime Thibault
 *
 * Notify user if he's using its phone when SleepMode is activate and in its time range.
 *
 */
public class SMWarningAction extends Action {

    private PendingIntent alarm;
    private static SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;
    private static Calendar nextReminder;
    public static final String WARNING_ACTION = "SMWarningAction";

    @Override
    protected boolean canExecute() {
        /**
         * Multiple rules.
         * 1. SleepMode has to be activated
         * 2. Right now has to be a time between the start and end of the SleepMode's activation time
         * 3. The intend ID has to match this action ID's (SMWarningAction) or match ACTION_USER_PRESENT
         */
        String intentAction = intent.getAction();

        //Min and Max as of today
        boolean isActivate = SMModel.isActivate(context);
        boolean isInRange = SMModel.getTimeRange(context).isInRange(Calendar.getInstance());
        boolean intentOK = intentAction.equals(WARNING_ACTION) || intentAction.equals(ACTION_USER_PRESENT);

        return isActivate && isInRange && intentOK;
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
        nextReminder.setTimeInMillis(Calendar.getInstance().getTimeInMillis() + 1000*60* SMModel.getReminderDelay(context));
        String date = new SimpleDateFormat("H'h'mm").format(nextReminder.getTime());
        SMModel.setNextReminderDate(context, date);
        //Update
        SMNotification.build(context);
    }

    @Override
    protected boolean canReschedule() {
        //Always
        return true;
    }

    @Override
    protected void reschedule() {
        //TODO: Maybe wrap this code into a class because it repeats amongs other actions

        //Cancel any other reminder alarm
        cancel();

        //Create new alarm
        Intent intent = new Intent(context, getClass());
        intent.setAction(WARNING_ACTION);
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
     * Cancel THIS ACTION reminder
     */
    private void cancel() {
        if(alarm != null) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(alarm);
        }
    }

    /**
     * Set the context of this action.
     * It is necessary to listen any changes of SleepMode's SharedPreferences
     */
    public void setContext(final Context c) {
        this.context = c;
        if(onSharedPreferenceChangeListener != null) return;
        onSharedPreferenceChangeListener = new
                SharedPreferences.OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                        switch (key){
                            /**
                             * If the Activation's state change
                             * We need to dismiss the notification -> ALWAYS because SMDailyAction
                             * will trigger anyway and call THIS action
                             */
                            case SMModel.SWITCH_STATE:
                                cancel();
                                SMNotification.dismiss(context);
                                break;
                            /**
                             * When the delay between reminder is modified, we need to update the
                             * notification. So,
                             * 1. Dismiss it
                             * 2. Rebuild it
                             */
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
