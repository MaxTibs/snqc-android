package com.example.maxtibs.snqc_android.toolkit.GrayScaleStepper;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.example.maxtibs.snqc_android.R;
import com.example.maxtibs.snqc_android.toolkit.GrayScaleStepper.fragments.ActivateMonochromeStep;
import com.example.maxtibs.snqc_android.toolkit.GrayScaleStepper.fragments.DevConfigStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

public class StepperAdapter extends AbstractFragmentStepAdapter {

    private static final String CURRENT_STEP_POSITION_KEY = "current_step_position_key";

    public StepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        switch (position) {
            case 0:
                final DevConfigStep step1 = new DevConfigStep();
                Bundle b1 = new Bundle();
                b1.putInt(CURRENT_STEP_POSITION_KEY, position);
                step1.setArguments(b1);
                return step1;

            case 1:
                final ActivateMonochromeStep step2 = new ActivateMonochromeStep();
                Bundle b2 = new Bundle();
                b2.putInt(CURRENT_STEP_POSITION_KEY, position);
                step2.setArguments(b2);
                return step2;

            default:
                throw new IllegalArgumentException("Unsupported position: " + position);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        switch (position) {
            case 0:
                return new StepViewModel.Builder(context)
                        .setTitle(R.string.grayscale_stepper_first_step_title)
                        .create();

            case 1:
                return new StepViewModel.Builder(context)
                        .setTitle(R.string.grayscale_stepper_second_step_title)
                        .create();

            default:
                throw new IllegalArgumentException("Unsupported position: " + position);
        }
    }
}
