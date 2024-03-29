package com.example.maxtibs.snqc_android.toolkit.Tools.SleepMode;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.maxtibs.snqc_android.utilities.DayTime;
import com.example.maxtibs.snqc_android.utilities.LocalStorage;
import com.example.maxtibs.snqc_android.utilities.TimeRange;


public class SMModel {

    public static final String FILENAME = "SMModel.data";

    public static final String START_TIME_HOUR = "SMModel.sleep_start.hour";
    public static final String START_TIME_MINUTE = "SMModel.sleep_start.minute";
    public static final String END_TIME_HOUR = "SMModel.sleep_end.hour";
    public static final String END_TIME_MINUTE = "SMModel.sleep_end.minute";
    public static final String SWITCH_STATE = "SMModel.switch";
    public static final String REMINDER_DELAY = "SMModel.reminder_delay";
    public static final String NEXT_REMINDER_DATE = "SMModel.next_reminder";

    private static final int DEFAULT = 0;

    public static final Integer[] recall_delays = {5, 10, 15, 20, 25, 30, 45, 60};

    /**
     * Return preferred time range
     * @param context current context
     * @return TimeRange
     */
    public static TimeRange getTimeRange(Context context) {

        //Get sharedPreferences to get/edit user's data
        SharedPreferences sharedPreferences = LocalStorage.getSharedPreferences(context, FILENAME);

        //Get saved time String xxhxx
        final int startHour     = sharedPreferences.getInt(START_TIME_HOUR, DEFAULT);
        final int startMinute   = sharedPreferences.getInt(START_TIME_MINUTE, DEFAULT);
        final int endHour       = sharedPreferences.getInt(END_TIME_HOUR, DEFAULT);
        final int endMinute     = sharedPreferences.getInt(END_TIME_MINUTE, DEFAULT);

        DayTime startTime = new DayTime(startHour, startMinute);
        DayTime endTime = new DayTime(endHour, endMinute);

        //Build range from daytimes
        return new TimeRange(startTime, endTime);
    }

    /**
     * Set the min value in 24h of the timerange
     * @param context current context
     * @param hour int hour
     * @param minute int minute
     */
    public static void setTimeRangeMin(Context context, int hour, int minute) {

        //Update shared preference
        SharedPreferences.Editor editor = LocalStorage.getEditor(context, FILENAME);
        editor.putInt(START_TIME_HOUR, hour);
        editor.putInt(START_TIME_MINUTE, minute);
        editor.commit();
    }

    /**
     * Set the max value in 24h of the timerange
     * @param context current context
     * @param hour int hour
     * @param minute int minute
     */
    public static void setTimeRangeMax(Context context, int hour, int minute) {

        //Update shared preference
        SharedPreferences.Editor editor = LocalStorage.getEditor(context, FILENAME);
        editor.putInt(END_TIME_HOUR, hour);
        editor.putInt(END_TIME_MINUTE, minute);
        editor.commit();
    }

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

}
