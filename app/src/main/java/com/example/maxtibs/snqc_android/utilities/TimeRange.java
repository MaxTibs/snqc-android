package com.example.maxtibs.snqc_android.utilities;

import java.util.Calendar;

/**
 * TimeRange always return range as of today. It is also always modified to match current day and year
 * So, get and set returns or set min/max as of today
 */
public class TimeRange {

    private Calendar min;
    private Calendar max;

    public TimeRange(Calendar min, Calendar max) {

        //Set min and max asOfToday
        this.min = asOfToday(min);
        this.max = asOfToday(max);

        //Check if max < min and fix DAY
        fixRange(this.min, this.max);
    }

    /**
     * Check if max < min and fix DAY by adding 1 day to max
     * @param min min calendar
     * @param max max calendar
     */
    private void fixRange(Calendar min, Calendar max) {
        if(max.compareTo(min) < 0) {
            max.add(Calendar.DAY_OF_YEAR, 1);//TODO: CHeck if This increment Year
        }
    }

    public void setMin(Calendar min) {
        this.min = asOfToday(min);
        fixRange(this.min, this.max);
    }
    public void setMax(Calendar max) {
        this.max = asOfToday(max);
        fixRange(this.min, this.max);
    }

    public Calendar getMin() {
        asOfToday(this.min);
        fixRange(this.min, this.max);
        return this.min;
    }
    public Calendar getMax() {
        asOfToday(this.min);
        fixRange(this.min, this.max);
        return this.max;
    }

    /**
     * Modify cal to match today. DAY_OF_YEAR and YEAR are set as today
     * @param cal calender to modify
     * @return to date calendar
     */
    private Calendar asOfToday(Calendar cal) {
        Calendar now = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_YEAR, now.get(Calendar.DAY_OF_YEAR));
        cal.set(Calendar.YEAR, now.get(Calendar.YEAR));
        return this.min;
    }
}
