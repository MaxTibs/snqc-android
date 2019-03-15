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

    /**
     * Tells if the action can execute.
     * Usually, when overrinding this function, you have to define rules to tell when to execute
     * the 'execute' function.
     *
     * @return boolean
     */
    protected boolean canExecute() {return false;}

    /**
     * Code to execute
     */
    protected void execute() {}

    /**
     * Tells if ths action can re-schedule.
     * This is only relevent if the reschedule() function is implemented
     * @return boolean
     */
    protected boolean canReschedule() {return false;}

    /**
     * The re-schedule action
     */
    protected void reschedule() {}

}
