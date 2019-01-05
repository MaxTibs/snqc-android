package com.example.maxtibs.snqc_android.utilities;

import android.util.Log;

import java.util.Calendar;

public class TimeRange {

    private Calendar min;
    private Calendar max;

    public TimeRange(Calendar min, Calendar max) {
        this.setMinMax(min, max);
    }

    /**
     * Check if max < min when they are normalized. That means max should be incremented by 1 day
     * @return
     */
    private Boolean maxIsNextDay() {
        return min.compareTo(max) > 0;
    }

    public Calendar getMin() {
        return this.min;
    }
    public Calendar getMax() {
        return this.max;
    }

    public void setMinMax(Calendar min, Calendar max) {
        normalize(min, max);
        this.min = min;
        this.max = max;

        if(maxIsNextDay()) {
            this.max.add(Calendar.DAY_OF_YEAR, 1);
        }
    }

    /**
     * Increment min and max by 1 day
     */
    public void incrementDay() {
        this.min.add(Calendar.DAY_OF_YEAR, 1);
        this.max.add(Calendar.DAY_OF_YEAR, 1);
    }

    /**
     * Normalize 'from' to be the same day as 'to'
     * @param from
     * @param to
     * @return
     */
    public void normalize(Calendar from, Calendar to) {
        from.set(Calendar.DAY_OF_YEAR, to.get(Calendar.DAY_OF_YEAR));
    }

    /**
     * Check if time is between min and max.
     */
    public Boolean isInRange(Calendar time) {
        return time.compareTo(min) >= 0 && time.compareTo(max) < 0;
    }

    public void updateMin(Calendar min) {
        normalize(this.max, min); //Normalize max to min
        this.setMinMax(min, max); //Rebuild timerange
    }
    public void updateMax(Calendar max) {
        normalize(max, this.min); //Normalize max to min
        this.setMinMax(min, max);
    }

}
