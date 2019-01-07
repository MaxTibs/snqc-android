package com.example.maxtibs.snqc_android.toolkit.Tools;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.example.maxtibs.snqc_android.R;
import com.example.maxtibs.snqc_android.toolkit.Tools.Actions.SleepModeAction;
import com.example.maxtibs.snqc_android.utilities.TimeRange;

import java.util.Calendar;

/**
 * SleepMode Tool
 * This tool notify user when he's using it's phone in the configured time range
 * Basically, this class is the SleepMode View. When view changes, it modify backend data
 */
public class SleepMode extends Tool {

    public int CONFIGURATION_LAYOUT = R.layout.sleepmode_config;
    private View view;

    private final String START_TIME_KEY = "sleepMode_t0";
    private final String END_TIME_KEY = "sleepMode_t1";

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

            //Create button and config
            Button btn = view.findViewById(bindLayout);
            btn.setAllCaps(false);

            //Create action onClick
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int hour, minute;
                    //Get current time configured
                    if(min) {
                        hour = SleepModeAction.getTimeRange().getMin().get(Calendar.HOUR_OF_DAY);
                        minute = SleepModeAction.getTimeRange().getMin().get(Calendar.MINUTE);
                    } else {
                        hour = SleepModeAction.getTimeRange().getMax().get(Calendar.HOUR_OF_DAY);
                        minute = SleepModeAction.getTimeRange().getMax().get(Calendar.MINUTE);
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

                            //Modify selected time
                            if(min) {
                                //Update backend min
                                SleepModeAction.setTimeRangeMin(context, selectedHour, selectedMinute);
                            } else {
                                //Update backend max
                                SleepModeAction.setTimeRangeMax(selectedHour, selectedMinute);
                            }

                            //Update view
                            ((Button)view.findViewById(bindLayout)).setText(intTimeToString(selectedHour, selectedMinute));
                        }
                    }, hour, minute, true);
                    mTimePicker.setTitle("Choisissez une heure");
                    mTimePicker.show();
                }
            };

            //Bind onClickListener to Button
            btn.setOnClickListener(onClickListener);

            //Initialise displayed on button
            if(min) btn.setText(getTime(SleepModeAction.getTimeRange().getMin()));
            else btn.setText(getTime(SleepModeAction.getTimeRange().getMax()));
        }

        /**
         * Transform int time to displayable String value
         * @param hour Hour in int
         * @param minute Minute in int
         * @return Displayable String Time
         */
        private String intTimeToString(int hour, int minute) {
            String sHour = String.valueOf(hour);
            String sMinute;

            //If minute is one digit
            if (minute <= 9) {
                sMinute = "0" + String.valueOf(minute); //Make it 2 digits
            }
            else sMinute = String.valueOf(minute); //It is already 2 digits

            return sHour + "h" + sMinute;
        }

        /**
         * Returns String time of Calendar
         * @param time Calendar to display
         * @return String time
         */
        private String getTime(Calendar time) {

            int hour = time.get(Calendar.HOUR_OF_DAY);
            int minute = time.get(Calendar.MINUTE);

            return intTimeToString(hour, minute);
        }
    }

    public SleepMode(Context context) {
        super("Mode sommeil");

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

    /**
     * Returns SleepMode View
     * @param c context
     * @return View
     */
    @Override
    public View getConfigurationView(Context c) {

        //Inflate SleepMode View
        final LayoutInflater inflater = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(this.CONFIGURATION_LAYOUT, null);

        //Create a DatePickerButton that is a custom class that modify backend data on change
        DatePickerButton start = new DatePickerButton(view, R.id.timepicker_start, c, true);
        DatePickerButton end = new DatePickerButton(view, R.id.timepicker_end, c, false);

        return view;
    }

}
