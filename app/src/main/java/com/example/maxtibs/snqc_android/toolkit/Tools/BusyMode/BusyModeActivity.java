package com.example.maxtibs.snqc_android.toolkit.Tools.BusyMode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.maxtibs.snqc_android.R;
import com.example.maxtibs.snqc_android.toolkit.Tools.ITool;
import com.example.maxtibs.snqc_android.toolkit.Tools.Tool;

import java.text.DecimalFormat;

public class BusyModeActivity extends AppCompatActivity implements ITool {

    private static final int LAYOUT = R.layout.busymode_configiguration;
    private static final int ICON = R.drawable.ic_busy_icon;
    private static final String NAME = "Mode occupé";

    private static SeekBar seekBar;
    private static TextView clockView;
    private static Button button;

    private final int SEEKBAR_UNIT_MS = 1000*60*15; //15 minutes
    private final int SEEKBAR_UNIT_S = SEEKBAR_UNIT_MS / 1000;
    private final int SEEKBAR_UNIT_M = SEEKBAR_UNIT_S / 60;
    private final int SEEKBAR_MAX_PROGRESS = 8*60 / SEEKBAR_UNIT_M; //8h

    //To persist time throug multiple activity open
    private static String lastClockUpdate = null;
    private static int lastSeekBarProgress = 0;

    //Timer
    private static CountDownTimer countDownTimer = null;
    public static boolean isActive = false;

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

        //Activity configuration
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        overridePendingTransition(R.xml.slide_in_right, R.xml.stay);
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
        seekBar.setProgress(lastSeekBarProgress);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int hour = progress * 15 / 60;
                int minutes = progress * 15 % 60;
                setClockView(clockStringFormat(hour, minutes, 0));
                button.setEnabled(progress > 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        if(countDownTimer == null) return;
    }
    private void configureClockTxt(){
        if(lastClockUpdate == null) {
            setClockView(clockStringFormat(0, 0, 0));
        } else {
            clockView.setText(lastClockUpdate);
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
        if(lastSeekBarProgress == 0) button.setEnabled(false);
        if(isActive) button.setText(getString(R.string.button_stop));
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
            countDownTimer = null;
            isActive = false;
        }
        setSeekBarProgress(0);
        seekBar.setEnabled(true);
        setClockView(clockStringFormat(0, 0, 0));
        clockView.setText(lastClockUpdate);
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
        BusyModeActivity.countDownTimer = new CountDownTimer(ms, 1000) {
            //Update view onTick
            public void onTick(long millisUntilFinished) {
                isActive = true;
                long seconds = (millisUntilFinished / 1000) % 60;
                long minutes = (millisUntilFinished / (1000*60)) % 60;
                long hour = (millisUntilFinished / (1000*60*60)) % 24;

                //update text
                setClockView(clockStringFormat(hour, minutes, seconds));
                //update seekbar
                double seekbarProgress = Math.ceil((double)millisUntilFinished / (SEEKBAR_UNIT_MS));
                setSeekBarProgress((int)seekbarProgress);
            }
            //When time is elapsed
            public void onFinish() {
                setClockView("Temps écoulé!");
                isActive = false;
            }
        }.start();
    }

    private void setClockView(String str) {
        lastClockUpdate = str;
        clockView.setText(str);
    }
    private void setSeekBarProgress(int progress) {
        lastSeekBarProgress = progress;
        seekBar.setProgress(progress);
    }

    private String clockStringFormat(long hour, long minutes, long seconds) {
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
