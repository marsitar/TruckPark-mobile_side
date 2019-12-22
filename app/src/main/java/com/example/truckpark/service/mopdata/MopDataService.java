package com.example.truckpark.service.mopdata;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import com.example.truckpark.properties.PropertyManager;

public class MopDataService extends Service {

    private final PropertyManager propertyManager;
    private String URI;
    private String CATEGORY;

    private final IBinder binder = new MopDataBinder();

    public MopDataService() {

        propertyManager = new PropertyManager("truckparkserver.properties");

    }

    public class MopDataBinder extends Binder {
        public MopDataService getMopData() {
            return MopDataService.this;
        }
    }

    @Override
    public void onCreate() {

        URI = propertyManager.getProperty("URI", MopDataService.this);
        CATEGORY = "mops";

        getCurrentMopsFromServer();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private void getCurrentMopsFromServer() {

        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {

                MopDataRequestAsyncTask mopDataRequestAsyncTask = new MopDataRequestAsyncTask(URI, CATEGORY);
                mopDataRequestAsyncTask.execute();

                handler.postDelayed(this, 10000);
            }
        });
    }


}
