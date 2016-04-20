package com.vijayganduri.saltside.eventbus;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by vganduri on 10/18/2015.
 */
public class BusHandler {

    private static Bus ourInstance;

    public static Bus getInstance() {
        if(ourInstance == null){
            ourInstance = new Bus(ThreadEnforcer.MAIN);
        }
        return ourInstance;
    }

    private BusHandler() {
    }

}
