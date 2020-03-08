package com.example.truckpark.service.optiomalizedriverstime;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.esri.core.geometry.Polygon;
import com.example.truckpark.R;
import com.example.truckpark.domain.entity.MopForm;
import com.example.truckpark.domain.entity.RouteSchedule;
import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.localdatamanagment.DataGetter;
import com.example.truckpark.localdatamanagment.RouterScheduleDataManagement;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainOptimizationDriversTimeService extends Service {

    private List<LocalDateTime> driverBreaks = new ArrayList<>();

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

        addBreaksAndWorkEndTime();
        runMainOptimizationDriversTimeService();

        Log.i(className, "MainOptimizationDriversTimeService has been created.");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(className, "MainOptimizationDriversTimeService is to be bound.");
        return binder;
    }

    public void runMainOptimizationDriversTimeService() {

        Log.i(className, "TruckDriverPositionAndData is to be periodically sent to remote server.");

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                LocalDateTime firstBreakMinusPeriodOfTime = driverBreaks.get(0).minusMinutes(30);

                if (firstBreakMinusPeriodOfTime.compareTo(LocalDateTime.now()) >= 0) {

                    List<MopForm> closestMopForms = getMopFormsFromAlgorithmClasses();
                    if(!closestMopForms.isEmpty()) {
                        Toast toast = Toast.makeText(getApplicationContext(), String.format("closestMop1: %s, %d", closestMopForms.get(0).getMopName(), closestMopForms.get(0).getLeftKilometers()), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

                Log.d(className, String.format("firstBreakMinusPeriodOfTime = %s, timeNow = %s", firstBreakMinusPeriodOfTime, LocalDateTime.now()));

                handler.postDelayed(this, 60000);
            }
        });
    }

    private List<MopForm> getMopFormsFromAlgorithmClasses() {

        GisGeometry gisGeometry = new GisGeometry();
        Polygon gisPolygon = gisGeometry.generateBufferOnTheRightSideOfRoad();

        GeometryOperationService geometryOperationService = new GeometryOperationService();
        List<Mop> potentialStopMops = geometryOperationService.getPotentialStopMops(gisPolygon);

        ClosestMopFormsGenerator closestMopFormsGenerator = new ClosestMopFormsGenerator(getApplicationContext());
        List<MopForm> mopForms = closestMopFormsGenerator.generateClosestMopFormsGenerator(potentialStopMops);

        Log.d(className, String.format("Mops get form algorithm classes: ", mopForms));

        return mopForms;
    }

    private void addBreaksAndWorkEndTime() {
        DataGetter<RouteSchedule> routeScheduleDataGetter = new RouterScheduleDataManagement();
        LocalDateTime timeOfSavingSchedule = routeScheduleDataGetter.getData().getSaveDateAndTime();

        LocalDateTime firstBreak = timeOfSavingSchedule.plusHours(4).plusMinutes(30);
        Duration firstBreakDuration = Duration.ofMinutes(15);
        LocalDateTime secondBreak = firstBreak.plus(firstBreakDuration).plusHours(1).plusMinutes(15);
        Duration secondBreakDuration = Duration.ofMinutes(15);
        LocalDateTime endWorkDayTime = secondBreak.plus(secondBreakDuration).plusHours(1).plusMinutes(45);

        driverBreaks.addAll(Arrays.asList(firstBreak, secondBreak, endWorkDayTime));
    }

    private View generateClosestMopToastViewWithTextValues(String origin, String destination, int distance, Duration duration) {

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View toastLayout = layoutInflater.inflate(R.layout.single_closest_mop, null);
        toastLayout.setClipToOutline(true);

        TextView originValue = toastLayout.findViewById(R.id.origin_value);
        TextView destinationValue = toastLayout.findViewById(R.id.destination_value);
        TextView distanceValue = toastLayout.findViewById(R.id.distance_value);
        TextView durationValue = toastLayout.findViewById(R.id.duration_value);

        int distanceInKilometers = distance / 1000;
        long durationInHours = duration.toHours();
        long restDurationInMinutes = duration.toMinutes() - duration.toHours() * 60l;

        originValue.setText(origin);
        destinationValue.setText(destination);
        distanceValue.setText(String.format("%d km", distanceInKilometers));
        durationValue.setText(String.format("%d h, %d min.", durationInHours, restDurationInMinutes));

        Log.d(className, "PolylineToastViewWithTextValue has been created.");

        return toastLayout;
    }
}
