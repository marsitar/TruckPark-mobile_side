package com.example.truckpark.service.positionsender;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import com.example.truckpark.properties.PropertyManager;

public class TruckDriverPositionAndDataService extends Service {

    private String URI;
    private final PropertyManager propertyManager;

    private final IBinder binder = new TruckDriverPositionAndDataBinder();

    public TruckDriverPositionAndDataService() {
        propertyManager = new PropertyManager("truckparkserver.properties");
    }

    public class TruckDriverPositionAndDataBinder extends Binder {
        public TruckDriverPositionAndDataService getTruckDriverPositionAndData() {
            return TruckDriverPositionAndDataService.this;
        }
    }

    @Override
    public void onCreate() {

        URI = propertyManager.getProperty("URI", TruckDriverPositionAndDataService.this);

        sendPost();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private void sendPost() {

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
