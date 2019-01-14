package com.example.maxtibs.snqc_android.Utilities;

public class DayTime {
    private int hour = 0;
    private int minute = 0;

    public DayTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public void setHour(int hour) { this.hour = hour; }
    public void setMinute(int minute) { this.minute = minute; }
    public void setTime(int hour, int minute) { this.hour = hour; this.minute = minute; }

    public int getHour() { return this.hour; }
    public int getMinute() { return this.minute; }

    public String getStringTime() {
        String sHour = String.valueOf(hour);
        String sMinute;

        //If minute is one digit
        if (minute <= 9) {
            sMinute = "0" + String.valueOf(minute); //Make it 2 digits
        }
        else sMinute = String.valueOf(minute); //It is already 2 digits

        return sHour + "h" + sMinute;
    }
}
