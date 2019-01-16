package com.example.maxtibs.snqc_android.toolkit.Tools.BusyMode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.maxtibs.snqc_android.MainActivity;
import com.example.maxtibs.snqc_android.R;
import com.example.maxtibs.snqc_android.toolkit.Tools.ITool;
import com.example.maxtibs.snqc_android.toolkit.Tools.Tool;

import java.text.DecimalFormat;

public class BusyModeActivity extends AppCompatActivity implements ITool {

    private static final int LAYOUT = R.layout.busymode_configiguration;
    private static final int ICON = R.drawable.ic_busy_icon;
    private static final String NAME = "Mode occupé";

    private static CountDownTimer countDownTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        //Link Clock textView to slider
        init();

    }

    private void init() {
        final SeekBar sb = findViewById(R.id.busymode_seekbar);
        final TextView clock = findViewById(R.id.busymode_clock);
        final Button button = findViewById(R.id.busymode_button);

        sb.setMax(8 * 60 / 15); //8h*6 / 15minutes
        sb.incrementProgressBy(15);
        reset();
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int hour = progress * 15 / 60;
                int minutes = progress * 15 % 60;
                int secondes = 0;

                DecimalFormat formattter = new DecimalFormat("00");
                String twoDigitsMinutes = formattter.format(minutes);
                String twoDigitsSeconds = formattter.format(secondes);

                clock.setText(hour + ":" + twoDigitsMinutes + ":" + twoDigitsSeconds);
                button.setEnabled(progress > 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        clock.addTextChangedListener(new TextWatcher() {
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((Button) v).getText() == getString(R.string.button_start)) {
                    long ms = sb.getProgress()*15*60*1000;
                    start(ms, (long)1000, clock);
                    sb.setEnabled(false);
                    ((Button) v).setText(getString(R.string.button_stop));
                } else {
                    reset();
                }
            }
        });
    }

    private void reset() {
        //Link Clock textView to slider
        SeekBar sb = findViewById(R.id.busymode_seekbar);
        final TextView clock = findViewById(R.id.busymode_clock);
        Button button = findViewById(R.id.busymode_button);

        if(countDownTimer != null) countDownTimer.cancel();

        sb.setProgress(0);
        sb.setEnabled(true);
        clock.setText("0:00:00");
        button.setEnabled(false);
        button.setText(getString(R.string.button_start));
    }

    private void start(long ms, long tick, final TextView clock) {
        BusyModeActivity.countDownTimer = new CountDownTimer(ms, tick) {
            public void onTick(long millisUntilFinished) {

                long secondes = (millisUntilFinished / 1000) % 60;
                long minutes = (millisUntilFinished / (1000*60)) % 60;
                long hour = (millisUntilFinished / (1000*60*60)) % 24;

                DecimalFormat formattter = new DecimalFormat("00");
                String twoDigitsMinutes = formattter.format(minutes);
                String twoDigitsSeconds = formattter.format(secondes);

                clock.setText(hour + ":" + twoDigitsMinutes + ":" + twoDigitsSeconds);

                updateSeekBar(millisUntilFinished);

            }

            public void onFinish() {
                clock.setText("Temps écoulé!");
            }
        }.start();
    }

    private void updateSeekBar(long ms) {
        SeekBar sb = findViewById(R.id.busymode_seekbar);
        sb.setProgress((int) ms / 1000 / 60 / 15);
    }

    public Tool getTool() {
        return new Tool(NAME, ICON, LAYOUT);
    }

    public Intent getIntent(Context context) {
        return new Intent(context, getClass());
    }

}
