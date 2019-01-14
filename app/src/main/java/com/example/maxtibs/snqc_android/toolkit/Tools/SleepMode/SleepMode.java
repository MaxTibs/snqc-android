package com.example.maxtibs.snqc_android.toolkit.Tools.SleepMode;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;

import com.example.maxtibs.snqc_android.R;
import com.example.maxtibs.snqc_android.toolkit.Tools.ITool;
import com.example.maxtibs.snqc_android.toolkit.Tools.Tool;

import java.util.Calendar;

/**
 * SleepMode Tool
 * This tool notify user when he's using it's phone in the configured time range
 * Basically, this class is the SleepMode View. When view changes, it modify backend data
 */
public class SleepMode extends AppCompatActivity implements ITool {

    private static final int LAYOUT = R.layout.sleepmode_configuration;
    private static final int ICON = R.drawable.ic_sleep_icon;
    private static final String NAME = "Mode sommeil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inflate SleepMode View
        final LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(LAYOUT, null);

        //Create a DatePickerButton that is a custom class that modify backend data on change
        DatePickerButton start = new DatePickerButton(view, R.id.timepicker_start, this, true);
        DatePickerButton end = new DatePickerButton(view, R.id.timepicker_end, this, false);

        //Switch on/off listener
        Switch mSwitch = view.findViewById(R.id.sleepmode_switch);
        mSwitch.setChecked(SleepModeModel.isActivate(this));
        final Context context = this;
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SleepModeModel.activate(context, b);
            }
        });

        //Dropdown reminder preference
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(context, android.R.layout.simple_spinner_item, SleepModeModel.recall_delays);
        Spinner spinner = view.findViewById(R.id.sleepmode_recall_spinner);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(SleepModeModel.getReminderPositionInArray(context));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer selected = (Integer) parent.getItemAtPosition(position);
                SleepModeModel.setReminderDelay(context, selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        //Create view
        setContentView(view);
        overridePendingTransition(R.xml.slide_in_right, R.xml.stay);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.xml.stay, R.xml.slide_out_right);
    }

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

    /**
     * Return Tool based on this class
     * @return Tool object (interface)
     */
    public Tool getTool() {
        return new Tool(NAME, ICON, LAYOUT);
    }

    public Intent getIntent(Context context) {
        return new Intent(context, getClass());
    }
}
