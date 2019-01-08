package com.example.maxtibs.snqc_android.toolkit.tools.SleepMode;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.example.maxtibs.snqc_android.R;
import com.example.maxtibs.snqc_android.toolkit.tools.Tool;
import com.example.maxtibs.snqc_android.utilities.TimeRange;

import java.util.Calendar;

/**
 * SleepModeController Tool
 * This tool notify user when he's using it's phone in the configured time range
 * Basically, this class is the SleepModeController View. When view changes, it modify backend data
 */
public class SleepModeController extends Tool {

    public final int CONFIGURATION_LAYOUT = R.layout.sleepmode_config;
    private View view;

    /**
     * Custom TimePickerButton.
     * This button opens a TimePicker dialog onClick.
     * When user select a time, it is displayed on button and backend data is modified.
     */
    private class DatePickerButton {

        /**
         * Create DatePickerButton
         * @param view It is the layout View that contains button
         * @param bindLayout ID of the button to be bound to
         * @param context Current context
         * @param min Because it is a TimeRange, we need a min and max time. So, this specify what backend value needs to be change onChange
         */
        private DatePickerButton(final View view, final int bindLayout, final Context context, final Boolean min) {

            //Create button and configure it
            Button btn = view.findViewById(bindLayout);
            btn.setAllCaps(false);

            //Create action onClick
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int hour, minute;
                    //Get current time configured
                    if(min) {
                        hour = SleepModeModel.getTimeRange(context).getMin().getHour();
                        minute = SleepModeModel.getTimeRange(context).getMin().getMinute();
                    } else {
                        hour = SleepModeModel.getTimeRange(context).getMax().getHour();
                        minute = SleepModeModel.getTimeRange(context).getMax().getMinute();
                    }

                    //Opens up a TimePickerDialog
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

                        /**
                         * Action do to when time is selected.
                         * 1.Modify backend value
                         * 2.Update view
                         * @param timePicker timepicker
                         * @param selectedHour Hour selected
                         * @param selectedMinute Minute selected
                         */
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            //Create time calendar
                            Calendar time = Calendar.getInstance();
                            time.set(Calendar.HOUR_OF_DAY, selectedHour);
                            time.set(Calendar.MINUTE, selectedMinute);
                            time.set(Calendar.SECOND, 0);

                            //Modify selected time
                            String timeStr;
                            if(min) {
                                //Update backend min
                                SleepModeModel.setTimeRangeMin(context, selectedHour, selectedMinute);
                                timeStr = SleepModeModel.getTimeRange(context).getMin().getStringTime();
                            } else {
                                //Update backend max
                                SleepModeModel.setTimeRangeMax(context, selectedHour, selectedMinute);
                                timeStr = SleepModeModel.getTimeRange(context).getMax().getStringTime();
                            }

                            //Update view
                            ((Button)view.findViewById(bindLayout)).setText(timeStr);
                        }
                    }, hour, minute, true);
                    mTimePicker.setTitle("Choisissez une heure");
                    mTimePicker.show();
                }
            };

            //Bind onClickListener to Button
            btn.setOnClickListener(onClickListener);

            //Initialize displayed on button
            if(min) btn.setText(SleepModeModel.getTimeRange(context).getMin().getStringTime());
            else btn.setText(SleepModeModel.getTimeRange(context).getMax().getStringTime());
        }
    }

    public SleepModeController(Context context) {
        super("Mode sommeil");
    }

    /**
     * Returns SleepModeController View
     * @param c context
     * @return View
     */
    @Override
    public View getConfigurationView(Context c) {

        //Inflate SleepModeController View
        final LayoutInflater inflater = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(this.CONFIGURATION_LAYOUT, null);

        //Create a DatePickerButton that is a custom class that modify backend data on change
        DatePickerButton start = new DatePickerButton(view, R.id.timepicker_start, c, true);
        DatePickerButton end = new DatePickerButton(view, R.id.timepicker_end, c, false);

        return view;
    }

}
