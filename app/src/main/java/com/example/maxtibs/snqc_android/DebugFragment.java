package com.example.maxtibs.snqc_android;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.maxtibs.snqc_android.toolkit.Tools.SleepMode.SMModel;

public class DebugFragment extends Fragment {

    private final int CONFIGURATION_LAYOUT = R.layout.debug_layout;
    private View parent;

    public DebugFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(this.CONFIGURATION_LAYOUT, null);
        LinearLayout containter = v.findViewById(R.id.ll);

        //Debug views
        timerDebugLayout(containter);

        return v;
    }

    private void timerDebugLayout(LinearLayout parent) {

        TextView sleepModeTitle = getTitleText();
        sleepModeTitle.setText("Sleep mode timers");

        LinearLayout row1 = new LinearLayout(getContext());
        row1.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout row2 = new LinearLayout(getContext());
        row2.setOrientation(LinearLayout.HORIZONTAL);

        //Next schedule
        TextView timeoutNext = getRegularText();
        TextView schedule = getRegularText();
        timeoutNext.setText("Range start");
        schedule.setText(SMModel.getTimeRange(getContext()).getMin().getStringTime());
        schedule.setTypeface(null, Typeface.ITALIC);
        row1.addView(timeoutNext);
        row1.addView(schedule);

        //Range end
        TextView rangeEnd = getRegularText();
        TextView scheduleEnd = getRegularText();
        rangeEnd.setText("Range end");
        schedule.setText(SMModel.getTimeRange(getContext()).getMax().getStringTime());
        scheduleEnd.setTypeface(null, Typeface.ITALIC);

        row2.addView(rangeEnd);
        row2.addView(scheduleEnd);

        parent.addView(sleepModeTitle);
        parent.addView(row1);
        parent.addView(row2);

    }

    private TextView getTitleText() {
        TextView tv = new TextView(getContext());
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
        tv.setTypeface(null, Typeface.BOLD);
        return tv;
    }
    private TextView getRegularText() {
        TextView tv = new TextView(getContext());
        tv.setPadding(0, 5, 30, 5);
        return tv;

    }

}
