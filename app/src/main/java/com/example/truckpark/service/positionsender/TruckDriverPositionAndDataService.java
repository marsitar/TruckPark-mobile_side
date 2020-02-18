package com.example.truckpark.service.positionsender;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.example.truckpark.properties.PropertyManager;

public class TruckDriverPositionAndDataService extends Service {

    private final static String PROPERTY_FILE_NAME = "truckparkserver.properties";
    private String URI;
    private final PropertyManager propertyManager;
    private String className = this.getClass().getSimpleName();

    private final IBinder binder = new TruckDriverPositionAndDataBinder();

    public TruckDriverPositionAndDataService() {
        propertyManager = new PropertyManager(PROPERTY_FILE_NAME);
    }

    public class TruckDriverPositionAndDataBinder extends Binder {
        public TruckDriverPositionAndDataService getTruckDriverPositionAndData() {
            Log.i(className, "TruckDriverPositionAndDataService is to be get.");
            return TruckDriverPositionAndDataService.this;
        }
    }

    @Override
    public void onCreate() {
        Log.d(className, "TruckDriverPositionAndDataService has been created.");

        URI = propertyManager.getProperty("URI", TruckDriverPositionAndDataService.this);

        sendPost();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(className, "TruckDriverPositionAndDataService is to be bound.");
        return binder;
    }

    private void sendPost() {

        Log.d(className, "TruckDriverPositionAndData is to be periodically sent to remote server.");

        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {

                TruckDriverPositionAndDataSenderAsyncTask truckDriverPositionAndDataSenderAsyncTask = new TruckDriverPositionAndDataSenderAsyncTask(URI);
                truckDriverPositionAndDataSenderAsyncTask.execute();

                handler.postDelayed(this, 10000);
            }
        });
    }

}
