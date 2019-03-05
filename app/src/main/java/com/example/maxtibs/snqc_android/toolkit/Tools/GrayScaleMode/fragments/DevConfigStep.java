package com.example.maxtibs.snqc_android.toolkit.Tools.GrayScaleMode.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.maxtibs.snqc_android.R;
import com.example.maxtibs.snqc_android.utilities.GrayScaleUtility;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

public class DevConfigStep extends Fragment implements Step {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activate_dev_config, container, false);
        final DevConfigStep devConfigStepRef = this;
        v.findViewById(R.id.openDeviceInfoButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                devConfigStepRef.openDeviceInfo(v);
            }
        });
        return v;
    }

    @Override
    public VerificationError verifyStep() {
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
        return GrayScaleUtility.isDeveloperOptionsEnabled(getContext()) ? null :
                new VerificationError("Vous devez activer les options de développement avant de continuer!");
    }

    @Override
    public void onSelected() {
        //update UI when selected
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        Toast.makeText(getContext(), error.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    private void openDeviceInfo(View v) {
        GrayScaleUtility.openDeviceInfoParameter(v.getContext());
    }
}
