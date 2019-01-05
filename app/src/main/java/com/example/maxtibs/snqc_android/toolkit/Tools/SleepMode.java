package com.example.maxtibs.snqc_android.toolkit.Tools;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import com.example.maxtibs.snqc_android.R;
import com.example.maxtibs.snqc_android.toolkit.Tools.Actions.SleepModeAction;
import com.example.maxtibs.snqc_android.utilities.TimeRange;

import java.util.Calendar;

public class SleepMode extends Tool {

    public int CONFIGURATION_LAYOUT = R.layout.sleepmode_config;
    private Context _context;
    private View view;
    //private SleepModeAction sleepModeAction;

    private final String START_TIME_KEY = "sleepMode_t0";
    private final String END_TIME_KEY = "sleepMode_t1";

    private class DatePickerButton {

        private DatePickerButton(final View view, final int bindLayout, final Context context, final Boolean min) {
            Button btn = view.findViewById(bindLayout);
            btn.setAllCaps(false);
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int hour, minute;
                    //Get current time set
                    if(min) {
                        hour = SleepModeAction.getTimeRange().getMin().get(Calendar.HOUR_OF_DAY);
                        minute = SleepModeAction.getTimeRange().getMin().get(Calendar.MINUTE);
                    } else {
                        hour = SleepModeAction.getTimeRange().getMax().get(Calendar.HOUR_OF_DAY);
                        minute = SleepModeAction.getTimeRange().getMax().get(Calendar.MINUTE);
                    }

                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                            Boolean rangeIsOk = false;
                            //Modify selected time
                            if(min) {
                                //Update min
                                SleepModeAction.setTimeRangeMin(context, selectedHour, selectedMinute);
                            } else {
                                //Update max
                                SleepModeAction.setTimeRangeMax(selectedHour, selectedMinute);
                            }

                            //Update btn text
                            ((Button)view.findViewById(bindLayout)).setText(intTimeToString(selectedHour, selectedMinute));
                        }
                    }, hour, minute, true);
                    mTimePicker.setTitle("Choisissez une heure");
                    mTimePicker.show();
                }
            };
            btn.setOnClickListener(onClickListener);
            if(min) btn.setText(getTime(SleepModeAction.getTimeRange().getMin()));
            else btn.setText(getTime(SleepModeAction.getTimeRange().getMax()));
        }

        private String intTimeToString(int hour, int minute) {
            String sHour = String.valueOf(hour);
            String sMinute;
            if(minute == 0) {
                sMinute = "00";
            }
            else if (minute <= 9) {
                sMinute = "0" + String.valueOf(minute);
            }
            else sMinute = String.valueOf(minute);

            return sHour + "h" + sMinute;
        }

        private String getTime(Calendar time) {

            int hour = time.get(Calendar.HOUR_OF_DAY);
            int minute = time.get(Calendar.MINUTE);

            return intTimeToString(hour, minute);
        }
    }

    public SleepMode(Context context) {
        this._name = "Mode sommeil";
        this._context = context;

        //If time range has not been updated/set yet
        if(SleepModeAction.getTimeRange() == null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("SNQC_DATA", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            //Temporary -> Should be config by user
            Calendar min = Calendar.getInstance();
            min.set(Calendar.HOUR_OF_DAY, 20);
            min.set(Calendar.MINUTE, 0);
            min.set(Calendar.SECOND, 0);

            Calendar max = Calendar.getInstance();
            max.set(Calendar.HOUR_OF_DAY, 6);
            max.set(Calendar.MINUTE, 0);
            max.set(Calendar.SECOND, 0);

            SleepModeAction.setTimeRange(new TimeRange(min, max));

        }
    }

    @Override
    public View getConfigurationView(Context c) {

        final LayoutInflater inflater = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(this.CONFIGURATION_LAYOUT, null);

        DatePickerButton start = new DatePickerButton(view, R.id.timepicker_start, c, true);
        DatePickerButton end = new DatePickerButton(view, R.id.timepicker_end, c, false);

        return view;
    }

}
