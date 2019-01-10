package com.example.maxtibs.snqc_android.toolkit.GrayScaleStepper.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maxtibs.snqc_android.R;
import com.example.maxtibs.snqc_android.Utilities.GrayScaleUtility;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

public class ActivateMonochromeStep extends Fragment implements Step {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activate_monochrome_config, container, false);
        final ActivateMonochromeStep activateMonochromeStepRef = this;
        v.findViewById(R.id.openDevOptionsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activateMonochromeStepRef.openDevOptions(v);
            }
        });
        return v;
    }

    @Override
    public VerificationError verifyStep() {
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
        return null;
    }

    @Override
    public void onSelected() {
        //update UI when selected
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        //handle error inside of the fragment, e.g. show error on EditText
    }

    private void openDevOptions(View v) {
        GrayScaleUtility.openDeveloperOptions(v.getContext());
    }
}
