package com.example.maxtibs.snqc_android.utilities;

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
}
