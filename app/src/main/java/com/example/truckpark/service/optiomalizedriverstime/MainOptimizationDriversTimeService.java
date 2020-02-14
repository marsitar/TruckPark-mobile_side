package com.example.truckpark.service.optiomalizedriverstime;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MainOptimizationDriversTimeService extends Service {

    private String className = this.getClass().getSimpleName();

    private final IBinder binder = new MainOptimizationDriversTimeBinder();

    public class MainOptimizationDriversTimeBinder extends Binder {
        public MainOptimizationDriversTimeService getMainOptimizationDriversTime() {
            Log.i(className, "MainOptimizationDriversTimeService is to be get.");
            return MainOptimizationDriversTimeService.this;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(className, "MainOptimizationDriversTimeService is to be bound.");
        return binder;
    }

    public void service() {


    }
}
