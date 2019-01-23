package com.example.maxtibs.snqc_android.toolkit.Tools.BusyMode;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.maxtibs.snqc_android.Utilities.LocalStorage;

public class BusyModeModel {

    private static final String FILENAME = "BusyMode.data";

    private static final String SWITCH_STATE = "BusyMode.switch";
    private static final String REMINDER_DELAY = "BusyMode.reminder_delay";
    private static final String NEXT_REMINDER_DATE = "BusyMode.next_reminder";
    private static final String CLOCK_VALUE = "Busymode.clock_value";
    private static final String SEEKBAR_PROGRESS_VALUE = "Busymode.seekbar_progress";

    private static final int DEFAULT = 0;

    public static final Integer[] recall_delays = {5, 10, 15, 20, 25, 30, 45, 60};

    /**
     * Returns boolean telling if mode is activate or not
     * @param context current context
     * @return switch's state
     */
    public static boolean isActivate(Context context) {
        SharedPreferences sharedPreferences = LocalStorage.getSharedPreferences(context, FILENAME);
        return sharedPreferences.getBoolean(SWITCH_STATE, false);
    }

    /**
     * Set the state of the mode. It is a boolean telling if mode is activate or not.
     * @param context current context
     * @param state state of switch
     */
    public static void activate(Context context, boolean state) {
        SharedPreferences.Editor editor = LocalStorage.getEditor(context, FILENAME);
        editor.putBoolean(SWITCH_STATE, state);
        editor.commit();
    }

    /**
     * Set preffered delay between reminders
     * @param context current context
     * @param minutes delay
     */
    public static void setReminderDelay(Context context, int minutes) {
        SharedPreferences.Editor editor = LocalStorage.getEditor(context, FILENAME);
        editor.putInt(REMINDER_DELAY, minutes);
        editor.commit();

        //Reset reminder
        BusyModeLifeCycle.setReminder(context);

        //Reset timer if active
        if(BusyModeModel.isActivate(context)) BusyModeNotification.notify(context);
    }

    public static Integer getReminderDelay(Context context) {
        SharedPreferences sharedPreferences = LocalStorage.getSharedPreferences(context, FILENAME);
        return sharedPreferences.getInt(REMINDER_DELAY, recall_delays[0]);
    }
    public static Integer getReminderPositionInArray(Context context) {
        Integer val = getReminderDelay(context);
        for(int i = 0; i < recall_delays.length; i++) {
            if(recall_delays[i].equals(val)) {
                return i;
            }
        }
        return 0;
    }

    public static void setNextReminderDate(Context context, String date) {
        SharedPreferences.Editor editor = LocalStorage.getEditor(context, FILENAME);
        editor.putString(NEXT_REMINDER_DATE, date);
        editor.commit();
    }
    public static String getNextReminderDate(Context context) {
        SharedPreferences sharedPreferences = LocalStorage.getSharedPreferences(context,FILENAME);
        return sharedPreferences.getString(NEXT_REMINDER_DATE, "UNDEFINED");
    }

    public static void setClockValue(Context context, String value) {
        SharedPreferences.Editor editor = LocalStorage.getEditor(context, FILENAME);
        editor.putString(CLOCK_VALUE, value);
    }
    public static String getClockValue(Context context) {
        SharedPreferences sharedPreferences = LocalStorage.getSharedPreferences(context,FILENAME);
        return sharedPreferences.getString(CLOCK_VALUE, "0:00:00");
    }
    public static void setSeekbarProgressValue(Context context, int value) {
        SharedPreferences.Editor editor = LocalStorage.getEditor(context, FILENAME);
        editor.putInt(SEEKBAR_PROGRESS_VALUE, value);
    }
    public static int getSeekbarProgressValue(Context context) {
        SharedPreferences sharedPreferences = LocalStorage.getSharedPreferences(context, FILENAME);
        return sharedPreferences.getInt(SEEKBAR_PROGRESS_VALUE, DEFAULT);
    }

    /**
     * Notify BusyModeModel to rebuild notifications and logic
     */
    public static void notifyLifecycle(Context context) {
        if(!BusyModeModel.isActivate(context)) BusyModeNotification.dismiss(context);
        Intent intent = new Intent(context, BusyModeLifeCycle.class);
        intent.setAction(BusyModeLifeCycle.REMINDER);
        context.sendBroadcast(intent);
    }

}
