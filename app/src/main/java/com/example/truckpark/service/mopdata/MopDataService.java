package com.example.truckpark.service.mopdata;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.example.truckpark.properties.PropertyManager;

public class MopDataService extends Service {

    private final static String PROPERTY_FILE_NAME = "truckparkserver.properties";
    private final PropertyManager propertyManager;
    private String URI;
    private String CATEGORY;
    private String className = this.getClass().getSimpleName();

    private final IBinder binder = new MopDataBinder();

    public MopDataService() {

        propertyManager = new PropertyManager(PROPERTY_FILE_NAME);

    }

    public class MopDataBinder extends Binder {
        public MopDataService getMopData() {
            Log.i(className, "MopDataService is to be get.");
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
        Log.i(className, "MopDataService is to be bound.");
        return binder;
    }

    private void getCurrentMopsFromServer() {

        Log.i(className, "Mops from server are to be periodically get from remote server.");

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
