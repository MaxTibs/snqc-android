package com.example.maxtibs.snqc_android;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DashboardFragment extends Fragment {

    public DashboardFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Random rand = new Random();

        View v = getLayoutInflater().inflate(R.layout.dashboard, null);
        BarChart barChart = (BarChart) v.findViewById(R.id.chart);
        List<BarEntry> entries = new ArrayList<BarEntry>();
        for(int i = 0; i < 7; i++) entries.add(new BarEntry(i, rand.nextInt(4) + 1));

        BarDataSet barDataSet = new BarDataSet(entries, "label");
        BarData barData = new BarData(barDataSet);
        barDataSet.setColor(getResources().getColor(R.color.colorAccent));
        barChart.setData(barData);

        YAxis left = barChart.getAxisLeft();
        left.setDrawLabels(true);
        left.setDrawAxisLine(false);
        left.setDrawGridLines(true);
        left.setDrawZeroLine(true);
        barChart.getAxisRight().setEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        barChart.getLegend().setEnabled(false);
        barChart.getDescription().setEnabled(false);

        return v;
    }
}
