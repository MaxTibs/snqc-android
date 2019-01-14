package com.example.maxtibs.snqc_android.Utilities;

import android.util.Log;
import android.util.Pair;


import java.util.Calendar;

public class TimeRange {

    private DayTime min;
    private DayTime max;

    public TimeRange(DayTime min, DayTime max) {
        this.min = min;
        this.max = max;
    }

    public DayTime getMin() { return min; }
    public DayTime getMax() { return max; }

    public void setMin(int hour, int minute) {
        this.min.setTime(hour, minute);
    }
    public void setMax(int hour, int minute) {
        this.max.setTime(hour, minute);
    }

    /**
     * Return DayTime as Calendar today
     * @param dayTime time to adjust
     * @return Adjusted daytime
     */
    private Calendar dayTimeNow(DayTime dayTime) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, dayTime.getHour());
        now.set(Calendar.MINUTE, dayTime.getMinute());
        now.set(Calendar.SECOND, 0);
        Log.d("TimeRange", "dayTimeNow: " + now.getTime().toString());
        return now;
    }

    /**
     * Return Calendar range min, max. Ranges are fix based on now.
     *  1.If whole range is in the past, add 1 day to min, max
     *  2.If max < min, add 1 day to max.
     * @return Pair object containing min and max
     */
    private Pair<Calendar, Calendar> getCalendarRange() {

        Calendar now = Calendar.getInstance();

        //Min and Max as of today
        Calendar min = dayTimeNow(getMin());
        Calendar max = dayTimeNow(getMax());

        /////FIX RANGE///////

        //Check if max < min. If so, add 1 day to max. -> Check if min,max are in order
        if(max.compareTo(min) < 0) {
            max.add(Calendar.DAY_OF_YEAR, 1);
            Log.d("TimeRange", "max < min");
        }

        //if now < min (since max is tommorow) -> Check if range is too far (1 day)
        if(now.compareTo(min) < 0) {
            Calendar minTmp = (Calendar) min.clone();
            Calendar maxTmp = (Calendar) max.clone();

            minTmp.add(Calendar.DAY_OF_YEAR, -1);
            maxTmp.add(Calendar.DAY_OF_YEAR, -1);

            //if now > minTmp & now < maxTmp
            if(now.compareTo(minTmp) >= 0 && now.compareTo(maxTmp) < 0) {
                //minTmp and maxTmp are ok
                min = minTmp;
                max = maxTmp;
            }

        }

        //Check if min & max is less than now. If so, add 1 day to each. -> Check if range is behind
        if(min.compareTo(now) < 0 && max.compareTo(now) < 0) {
            min.add(Calendar.DAY_OF_YEAR, 1);
            max.add(Calendar.DAY_OF_YEAR, 1);
            Log.d("TimeRange", "Not in range");
        }

        return new Pair<>(min, max);
    }

    /**
     * Return min Calendar from CalendarRange
     * @return min calendar
     */
    public Calendar getCalendarMin() {
        Pair<Calendar, Calendar> pair = getCalendarRange();
        Log.d("TimeRange", "getCalendarMin: " + pair.first.getTime().toString());
        return pair.first;
    }

    /**
     * Return max Calendar from CalendarRange
     * @return max calendar
     */
    public Calendar getCalendarMax() {
        Pair<Calendar, Calendar> pair = getCalendarRange();
        return pair.second;
    }
}
