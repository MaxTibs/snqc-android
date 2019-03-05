package com.example.maxtibs.snqc_android.toolkit;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Action extends BroadcastReceiver {

    protected Context context;
    protected Intent intent;


    @Override
    public void onReceive(Context context, Intent intent){

        this.context = context;
        this.intent = intent;

        if(!canExecute()) return;
        execute();
        if(!canReschedule()) return;
        reschedule();
    }

    protected boolean canExecute() {return false;}
    protected void execute() {}
    protected boolean canReschedule() {return false;}
    protected void reschedule() {}

}
