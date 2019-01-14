package com.example.maxtibs.snqc_android.toolkit.Tools.GrayScaleMode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.maxtibs.snqc_android.R;
import com.example.maxtibs.snqc_android.toolkit.Tools.ITool;
import com.example.maxtibs.snqc_android.toolkit.Tools.Tool;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

public class GrayScaleModeActivity extends AppCompatActivity implements StepperLayout.StepperListener, ITool {

    private static final int LAYOUT = R.layout.grayscale_stepper;
    private static final int ICON = R.drawable.ic_grayscale_icon;
    private static final String NAME = "Mode ton de gris";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        setTitle(NAME);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        overridePendingTransition(R.xml.slide_in_right, R.xml.stay);
        StepperLayout mStepperLayout = findViewById(R.id.grayScaleLayout);
        mStepperLayout.setAdapter(new StepperAdapter(getSupportFragmentManager(), this));
        mStepperLayout.setListener(this);
    }

    @Override
    public void onCompleted(View completeButton) {
        finish();
    }

    @Override
    public void onError(VerificationError verificationError) {
        Toast.makeText(this, verificationError.getErrorMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStepSelected(int newStepPosition) {

    }

    @Override
    public void onReturn() {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.xml.stay, R.xml.slide_out_right);
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
