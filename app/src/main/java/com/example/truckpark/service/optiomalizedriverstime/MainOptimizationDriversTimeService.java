package com.example.truckpark.service.optiomalizedriverstime;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.esri.core.geometry.Polygon;
import com.example.truckpark.domain.entity.MopForm;
import com.example.truckpark.domain.entity.RouteSchedule;
import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.localdatamanagment.DataGetter;
import com.example.truckpark.localdatamanagment.RouterScheduleDataManagement;

import java.time.LocalDateTime;
import java.util.List;

public class MainOptimizationDriversTimeService extends Service {

    private LocalDateTime firstBreakTime;

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

        DataGetter<RouteSchedule> routeScheduleDataGetter = new RouterScheduleDataManagement();
        LocalDateTime timeOfSavingSchedule = routeScheduleDataGetter.getData().getSaveDateAndTime();

        this.firstBreakTime = timeOfSavingSchedule.plusHours(3).plusMinutes(30);

        service();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(className, "MainOptimizationDriversTimeService is to be bound.");
        return binder;
    }

    public void service() {

        Log.i(className, "TruckDriverPositionAndData is to be periodically sent to remote server.");

        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {

                if (firstBreakTime.minusMinutes(30).compareTo(LocalDateTime.now()) >= 0) {

                    List<MopForm> closestMopForms = getMopFormsFromAlgorithmClasses();

                    Toast toast = Toast.makeText(getApplicationContext(), String.format("closestMop1: %s, %d", closestMopForms.get(0).getMopName(), closestMopForms.get(0).getLeftKilometers()), Toast.LENGTH_SHORT);
                    toast.show();

                }

                handler.postDelayed(this, 300000);
            }
        });


    }

    private List<MopForm> getMopFormsFromAlgorithmClasses() {
        GisGeometry gisGeometry = new GisGeometry();
        Polygon gisPolygon = gisGeometry.generateBufferOnTheRightSideOfRoad();

        GeometryOperationService geometryOperationService = new GeometryOperationService();
        List<Mop> potentialStopMops = geometryOperationService.getPotentialStopMops(gisPolygon);

        ClosestMopFormsGenerator closestMopFormsGenerator = new ClosestMopFormsGenerator(getApplicationContext());
        return closestMopFormsGenerator.generateClosestMopFormsGenerator(potentialStopMops);
    }
}
