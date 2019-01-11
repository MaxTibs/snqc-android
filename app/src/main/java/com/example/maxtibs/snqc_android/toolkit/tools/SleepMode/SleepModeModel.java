package com.example.maxtibs.snqc_android.toolkit.tools.SleepMode;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.maxtibs.snqc_android.utilities.DayTime;
import com.example.maxtibs.snqc_android.utilities.TimeRange;


public class SleepModeModel {

    private static final String FILENAME = "SNQC_DATA";

    private static final String START_TIME_HOUR = "SleepModeModel.sleep_start.hour";
    private static final String START_TIME_MINUTE = "SleepModeModel.sleep_start.minute";
    private static final String END_TIME_HOUR = "SleepModeModel.sleep_end.hour";
    private static final String END_TIME_MINUTE = "SleepModeModel.sleep_end.minute";
    private static final String SWITCH_STATE = "SleepModeModel.switch";

    private static final int DEFAULT = 0;

    /**
     * Return preferred time range
     * @param context
     * @return
     */
    public static TimeRange getTimeRange(Context context) {

        //Get sharedPreferences to get/edit user's data
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);

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
     * Returns boolean telling if mode is activate or not
     * @param context current context
     * @return switch's state
     */
    public static boolean isActivate(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(SWITCH_STATE, false);
    }

    /**
     * Set the state of the mode. It is a boolean telling if mode is activate or not.
     * @param context current context
     * @param state state of switch
     */
    public static void setSwitchState(Context context, boolean state) {
        SharedPreferences.Editor editor = getSPEditor(context);
        editor.putBoolean(SWITCH_STATE, state);
        editor.commit();
        //Notify lifecycle that model has change
        notifyLifecycle(context);
    }

    /**
     * Set the min value in 24h of the timerange
     * @param context current context
     * @param hour int hour
     * @param minute int minute
     */
    public static void setTimeRangeMin(Context context, int hour, int minute) {

        //Update shared preference
        SharedPreferences.Editor editor = getSPEditor(context);
        editor.putInt(START_TIME_HOUR, hour);
        editor.putInt(START_TIME_MINUTE, minute);
        editor.commit();

        //Cancel & Re-build alarms
        notifyLifecycle(context);
    }

    /**
     * Set the max value in 24h of the timerange
     * @param context current context
     * @param hour int hour
     * @param minute int minute
     */
    public static void setTimeRangeMax(Context context, int hour, int minute) {

        //Update shared preference
        SharedPreferences.Editor editor = getSPEditor(context);
        editor.putInt(END_TIME_HOUR, hour);
        editor.putInt(END_TIME_MINUTE, minute);
        editor.commit();

        //Cancel & Re-build alarms
        notifyLifecycle(context);
    }

    /**
     * Returns SharedPreference editor from contenxt
     * @param context current context
     * @return SP.editor
     */
    private static SharedPreferences.Editor getSPEditor(Context context) {
        //Get sharedPreferences to get/edit user's data
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        return editor;
    }

    /**
     * Notify SleepModeLifecycle
     */
    private static void notifyLifecycle(Context context) {
        SleepModeLifecycle.cancelAndRebuild(context);
    }

}
