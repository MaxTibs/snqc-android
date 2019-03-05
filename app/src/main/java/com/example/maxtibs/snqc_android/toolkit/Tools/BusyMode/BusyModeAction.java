//package com.example.maxtibs.snqc_android.toolkit.Tools.BusyMode;
//
//import android.app.AlarmManager;
//import android.app.KeyguardManager;
//import android.app.PendingIntent;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.util.Log;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//
//import static android.content.Intent.ACTION_USER_PRESENT;
//
//public class BusyModeAction extends BroadcastReceiver {
//
//    private static PendingIntent reminderIntent;
//
//    private static final String DTAG = "BusyModeLifecycle";
//    public static final String REMINDER = "BusyModeLifecycle.reminder";
//
//
//    //State machine
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        onSharedPreferenceListener(context);
//
//        String intentAction = intent.getAction();
//        if(!BusyModeModel.isActivate(context)) return;
//        if(intentAction.equals(ACTION_USER_PRESENT) || intentAction.equals(REMINDER)) {
//            if(phoneIsUnlock(context)) notifyAndResetReminder(context);
//        }
//    }
//
//    private void onSharedPreferenceListener(Context context){
//        SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = new
//                SharedPreferences.OnSharedPreferenceChangeListener() {
//                    @Override
//                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//                        switch (key){
//                            case BusyModeModel.SWITCH_STATE:
//                                //Reset mode
//                                break;
//                            case BusyModeModel.REMINDER_DELAY:
//                                //Reset reminder
//                                break;
//                        }
//                    }
//                };
//        BusyModeModel.getSharedPref(context).registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
//    }
//
//
//    /**
//     * Check if phone is unlock
//     * If so, notify user to close its phone and schedule a next reminder
//     * @param context current context
//     */
//    private static void notifyAndResetReminder(Context context) {
//        //Check if phone unlock. If so, notify user to close its phone
//            BusyModeNotification.notify(context);
//            setReminder(context);
//    }
//
//
//
//
//    /**
//     * Creates an alarm to triggers 15 minutes from now
//     * @param context current context
//     */
//    public static void setReminder(Context context) {
//
//        //Cancel any other reminder alarm
//        if(BusyModeAction.reminderIntent != null)
//            cancelAlarm(context, BusyModeAction.reminderIntent);
//
//        //Create new alarm
//        Intent intent = new Intent(context, BusyModeAction.class);
//        intent.setAction(REMINDER);
//        BusyModeAction.reminderIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
//
//        //Next reminder is now + REMINDER_DELAY
//        Calendar nextReminder = Calendar.getInstance();
//        nextReminder.setTimeInMillis(Calendar.getInstance().getTimeInMillis() + 1000*60* BusyModeModel.getReminderDelay(context));
//        String date = new SimpleDateFormat("H'h'mm").format(nextReminder.getTime());
//        BusyModeModel.setNextReminderDate(context, date);
//        //Set next alarm
//        setAlarm(context, nextReminder.getTimeInMillis(), BusyModeAction.reminderIntent);
//        Log.d(DTAG, "Next reminder set to trigger at " + nextReminder.getTime().toString());
//    }
//
//    /**
//     * Cancels alarm
//     * @param context current context
//     * @param pendingIntent alarm to cancel
//     */
//    private static void cancelAlarm(Context context, PendingIntent pendingIntent) {
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.cancel(pendingIntent);
//    }
//
//    /**
//     * Sets an alarm to trigger at exact ms time
//     * @param context current context
//     * @param timeInMs time in ms
//     * @param pendingIntent alarm intent
//     */
//    private static void setAlarm(Context context, long timeInMs, PendingIntent pendingIntent) {
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.setExact(
//                AlarmManager.RTC_WAKEUP,
//                timeInMs,
//                pendingIntent
//        );
//    }
//}
