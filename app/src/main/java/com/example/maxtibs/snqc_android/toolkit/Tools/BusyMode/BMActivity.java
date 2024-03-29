package com.example.maxtibs.snqc_android.toolkit.Tools.BusyMode;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.maxtibs.snqc_android.R;
import com.example.maxtibs.snqc_android.toolkit.Tools.ITool;
import com.example.maxtibs.snqc_android.toolkit.Tools.Tool;

import java.text.DecimalFormat;

public class BMActivity extends AppCompatActivity implements ITool {

    private static final int LAYOUT = R.layout.busymode_configiguration;
    private static final int ICON = R.drawable.ic_busy_icon;
    private static final String NAME = "Mode occupé";

    private static SeekBar seekBar;
    private static TextView clockView;
    private static Button button;

    private final int SEEKBAR_UNIT_MS = 1000*60*15; //15 minutes
    private final int SEEKBAR_UNIT_S = SEEKBAR_UNIT_MS / 1000;
    private final int SEEKBAR_UNIT_M = SEEKBAR_UNIT_S / 60;
    private final int SEEKBAR_MAX_PROGRESS = 2*60 / SEEKBAR_UNIT_M; //8h

    //Timer
    private static CountDownTimer countDownTimer = null;
    private static BMAction bmAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        //Get core view element
        seekBar = findViewById(R.id.busymode_seekbar);
        clockView = findViewById(R.id.busymode_clock);
        button = findViewById(R.id.busymode_button);

        configureClockTxt();
        configureSeekbar();
        configureButton();

        //Dropdown reminder preference
        final Context context = this;
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, BMModel.recall_delays);
        Spinner spinner = findViewById(R.id.busymode_reminder_dropdown);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(BMModel.getReminderPositionInArray(this));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer selected = (Integer) parent.getItemAtPosition(position);
                BMModel.setReminderDelay(context, selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        //Activity configuration
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        overridePendingTransition(R.xml.slide_in_right, R.xml.stay);
        setTitle(NAME);

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog);
        builder.setTitle("Comment ça marche?")
                .setMessage("Configurer le minuteur.\nDes alertes vous seront envoyés périodiquement afin de vous inciter à fermer votre écran de téléphone.\n\nIl est pertinent d'utiliser cette fonctionnalité lorsqu'on désire ne pas utiliser sont téléphone ou ne pas être dérangé pendant une certaine période de temps.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

        bmAction = new BMAction();
        bmAction.setContext(this);
    }

    /**
     * Override action to do on back button click
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.xml.stay, R.xml.slide_out_right);
    }

    private void configureSeekbar() {
        seekBar.setMax(SEEKBAR_MAX_PROGRESS);
        seekBar.setProgress(BMModel.getSeekbarProgressValue(this));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int hour = progress * 15 / 60;
                int minutes = progress * 15 % 60;
                setClockValue(clockStringFormat(hour, minutes, 0));
                button.setEnabled(progress > 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    private void configureClockTxt(){
        String clockValue = BMModel.getClockValue(this);
        if(clockValue == null) {
            setClockValue(clockStringFormat(0, 0, 0));
        } else {
            clockView.setText(clockValue);
        }
        clockView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void configureButton(){
        final Context context = this;
        if(BMModel.getSeekbarProgressValue(this) == 0) button.setEnabled(false);
        if(BMModel.isActivate(this)) button.setText(getString(R.string.button_stop));
        //Action to do on button click
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if button display START
                if (((Button) v).getText() == getString(R.string.button_start)) { //If so, start timer
                    long ms = seekBar.getProgress()*SEEKBAR_UNIT_MS; //1 unit = 1/4 hour
                    start(ms);
                    ((Button) v).setText(getString(R.string.button_stop));
                    //Disable seekbar modification
                    seekBar.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return true;
                        }
                    });
                } else { //Reset timer
                    reset();
                }
            }
        });
    }

    /**
     * Reset (or set) seekbar and textview to 0
     */
    private void reset() {
        //Link Clock textView to slider
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
        countDownTimer = null;
        BMModel.activate(this, false);
        setSeekBarProgressValue(0);
        seekBar.setEnabled(true);
        setClockValue(clockStringFormat(0, 0, 0));
        clockView.setText(BMModel.getClockValue(this));
        button.setEnabled(false);
        button.setText(getString(R.string.button_start));
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    /**
     * Start timer and define what to do every second and on time elapsed
     * @param ms amount of time that the busyMode should be activate
     */
    private void start(long ms) {
        final Context context = this;
        BMActivity.countDownTimer = new CountDownTimer(ms, 1000) {
            //Update view onTick
            public void onTick(long millisUntilFinished) {
                BMModel.activate(context, true);
                long seconds = (millisUntilFinished / 1000) % 60;
                long minutes = (millisUntilFinished / (1000*60)) % 60;
                long hour = (millisUntilFinished / (1000*60*60)) % 24;

                //update text
                setClockValue(clockStringFormat(hour, minutes, seconds));
                //update seekbar
                double seekbarProgress = Math.ceil((double)millisUntilFinished / (SEEKBAR_UNIT_MS));
                setSeekBarProgressValue((int)seekbarProgress);

            }
            //When time is elapsed
            public void onFinish() {
                setClockValue("Temps écoulé!");
                BMModel.activate(context, false);
            }
        }.start();
    }

    /**
     * Store clock value in model and update view
     * @param clockValue
     */
    private void setClockValue(String clockValue) {
        BMModel.setClockValue(this, clockValue);
        clockView.setText(clockValue);
    }
    /**
     * Store progress value in model and update view
     * @param progressValue
     */
    private void setSeekBarProgressValue(int progressValue) {
        BMModel.setSeekbarProgressValue(this, progressValue);
        seekBar.setProgress(progressValue);
    }

    /**
     * Format hour, minutes and seconds to a displayable string time
     * @param hour
     * @param minutes
     * @param seconds
     * @return
     */
    private static String clockStringFormat(long hour, long minutes, long seconds) {
        DecimalFormat formattter = new DecimalFormat("00");
        String twoDigitsMinutes = formattter.format(minutes);
        String twoDigitsSeconds = formattter.format(seconds);
        return hour + ":" + twoDigitsMinutes + ":" + twoDigitsSeconds;
    }

    public Tool getTool() {
        return new Tool(NAME, ICON, LAYOUT);
    }

    public Intent getIntent(Context context) {
        return new Intent(context, getClass());
    }

}
