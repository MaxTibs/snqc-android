package com.example.maxtibs.snqc_android.toolkit.tools.SleepMode;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.maxtibs.snqc_android.utilities.TimeRange;

import java.util.Calendar;

public class SleepModeModel {

    private static TimeRange timeRange;

    private static final String SLEEP_START_TIME = "SleepModeModel.sleep_start";
    private static final String SLEEP_END_TIME = "SleepModeModel.sleep_end";

    private static final int DEFAULT_SLEEP_START_TIME = 0;
    private static final int DEFAULT_SLEEP_END_TIME = 0;

    public static TimeRange getTimeRange(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("SNQC_DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Get saved time in ms
        final int sleep_start_time  = sharedPreferences.getInt(SLEEP_START_TIME, DEFAULT_SLEEP_START_TIME);
        final int sleep_end_time    = sharedPreferences.getInt(SLEEP_END_TIME, DEFAULT_SLEEP_END_TIME);

        //If start & end == 0, TimeRange is null
        if(sleep_start_time == 0 && sleep_end_time == 0) {
            Log.d("SleepModeModel", "getTimeRange: No range defined. Returning null");
            return null;
        }
        else {
            Calendar min = Calendar.getInstance();
            Calendar max = Calendar.getInstance();
            min.setTimeInMillis(sleep_start_time);
            max.setTimeInMillis(sleep_end_time);

            //Using old range to build today range
            return new TimeRange(min, max);
        }
    }


    public static void setTimeRangeMin(Context context, Calendar min) {

        //Create min value for today. This let TimeRange adjust Max value
        timeRange.setMin(min);
        Log.d("SleepModeModel", "Changing min to " + timeRange.getMin().getTime().toString());

        //TODO: Cancel & Re-build alarms
        notifyLifecycle(context);

    }
    public static void setTimeRangeMax(Context context, Calendar max) {

        //Create max value from today. Let timeRane adjust it from min.
        timeRange.setMax(max);

        //TODO: Cancel & Re-build alarms
        notifyLifecycle(context);

    }

    /**
     * Notify SleepModeLifecycle
     */
    private static void notifyLifecycle(Context context) {
        //TODO: Notify SleepModeLifecycle to cancel & re-build alarms
        SleepModeLifecycle.cancelAndRebuild(context);
    }

}
