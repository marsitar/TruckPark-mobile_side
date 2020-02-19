package com.example.truckpark.view.main;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truckpark.R;
import com.example.truckpark.domain.entity.RouteSchedule;
import com.example.truckpark.localdatamanagment.DataGetter;
import com.example.truckpark.localdatamanagment.RouterScheduleDataManagement;
import com.example.truckpark.repository.CurrentMops;
import com.example.truckpark.repository.CurrentState;
import com.example.truckpark.service.location.LocationDeviceService;
import com.example.truckpark.service.mopdata.MopDataService;
import com.example.truckpark.service.optiomalizedriverstime.MainOptimizationDriversTimeService;
import com.example.truckpark.service.positionsender.TruckDriverPositionAndDataService;
import com.example.truckpark.view.functionality.location.MapsActivityLocation;
import com.example.truckpark.view.functionality.mop.FindMop;
import com.example.truckpark.view.functionality.navigation.NavigationMenu;
import com.example.truckpark.view.functionality.pulloff.MapsActivityPullOff;
import com.example.truckpark.view.functionality.routescheduleform.RouteScheduleForm;
import com.example.truckpark.view.functionality.weather.FindWeather;

public class MainMenu extends AppCompatActivity {

    public LocationDeviceService locationDeviceService;
    public TruckDriverPositionAndDataService truckDriverPositionAndDataService;
    public MopDataService mopDataService;
    public MainOptimizationDriversTimeService mainOptimizationDriversTimeService;

    private boolean locationDeviceServiceBound = false;
    private boolean truckDriverPositionAndDataServiceBound = false;
    private boolean mopDataServiceBound = false;
    private boolean mainOptimizationDriversTimeServiceBound = false;

    private String className = this.getClass().getSimpleName();

    private ServiceConnection locationDeviceServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder) {
            LocationDeviceService.LocationDeviceBinder locationDeviceBinder = (LocationDeviceService.LocationDeviceBinder) binder;
            locationDeviceService = locationDeviceBinder.getLocationDevice();
            locationDeviceServiceBound = true;

            Log.i(className, "LocationDeviceServiceConnection service has been connected.");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            locationDeviceServiceBound = false;

            Log.i(className, "LocationDeviceServiceConnection service has been disconnected.");
        }
    };

    private ServiceConnection truckDriverPositionAndDataServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder) {
            TruckDriverPositionAndDataService.TruckDriverPositionAndDataBinder truckDriverPositionAndDataBinder = (TruckDriverPositionAndDataService.TruckDriverPositionAndDataBinder) binder;
            truckDriverPositionAndDataService = truckDriverPositionAndDataBinder.getTruckDriverPositionAndData();
            truckDriverPositionAndDataServiceBound = true;

            Log.i(className, "TruckDriverPositionAndDataServiceConnection service has been connected.");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            truckDriverPositionAndDataServiceBound = false;

            Log.i(className, "TruckDriverPositionAndDataServiceConnection service has been disconnected.");
        }
    };

    private ServiceConnection mopDataServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder) {
            MopDataService.MopDataBinder mopDataBinder = (MopDataService.MopDataBinder) binder;
            mopDataService = mopDataBinder.getMopData();
            mopDataServiceBound = true;

            Log.i(className, "MopDataServiceConnection service has been connected.");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mopDataServiceBound = false;

            Log.i(className, "MopDataServiceConnection service has been disconnected.");
        }
    };

    private ServiceConnection mainOptimizationDriversTimeServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder) {
            MainOptimizationDriversTimeService.MainOptimizationDriversTimeBinder mainOptimizationDriversTimeBinder = (MainOptimizationDriversTimeService.MainOptimizationDriversTimeBinder) binder;
            mainOptimizationDriversTimeService = mainOptimizationDriversTimeBinder.getMainOptimizationDriversTime();
            mainOptimizationDriversTimeServiceBound = true;

            Log.i(className, "MainOptimizationDriversTimeServiceConnection service has been connected.");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mainOptimizationDriversTimeServiceBound = false;

            Log.i(className, "MainOptimizationDriversTimeServiceConnection service has been disconnected.");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Log.i(className, "Activity has been created.");
    }

    @Override
    protected void onStart() {

        super.onStart();

        Intent LocationDeviceServiceIntent = new Intent(this, LocationDeviceService.class);
        bindService(LocationDeviceServiceIntent, locationDeviceServiceConnection, Context.BIND_AUTO_CREATE);

        if (!CurrentState.getCurrentStateInstance().isPositionIsBeingSent()) {

            Intent truckDriverPositionAndDataServiceIntent = new Intent(this, TruckDriverPositionAndDataService.class);
            bindService(truckDriverPositionAndDataServiceIntent, truckDriverPositionAndDataServiceConnection, Context.BIND_AUTO_CREATE);
            CurrentState.getCurrentStateInstance().setPositionIsBeingSent(true);

            Log.i(className, "TruckDriverPositionAndDataServiceIntent has been bounded and created.");
        }

        if (!CurrentMops.getCurrentMopsInstance().isMopsRequestingOn()) {

            Intent mopDataServiceIntent = new Intent(this, MopDataService.class);
            bindService(mopDataServiceIntent, mopDataServiceConnection, Context.BIND_AUTO_CREATE);
            CurrentMops.getCurrentMopsInstance().setMopsRequestingOn(true);

            Log.i(className, "MopDataServiceIntent has been bounded and created.");
        }

        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();
        if (routerScheduleDataManagement.getData() == null) {

            Intent mainOptimizationDriversTimeServiceIntent = new Intent(this, MainOptimizationDriversTimeService.class);
            bindService(mainOptimizationDriversTimeServiceIntent, mainOptimizationDriversTimeServiceConnection, Context.BIND_AUTO_CREATE);

            Log.i(className, "MainOptimizationDriversTimeServiceIntent has been bounded and created.");
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        unboundAllServices();

        Log.i(className, "Activity has been stopped.");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        unboundAllServices();

        Log.i(className, "Back button has been pressed.");
    }

    private void unboundAllServices(){

        if (locationDeviceServiceBound) {
            unbindService(locationDeviceServiceConnection);
            locationDeviceServiceBound = false;

            Log.i(className, "LocationDeviceService has been unbound.");
        }

        if (truckDriverPositionAndDataServiceBound) {
            unbindService(truckDriverPositionAndDataServiceConnection);
            truckDriverPositionAndDataServiceBound = false;

            Log.i(className, "TruckDriverPositionAndDataService has been unbound.");
        }

        if (mopDataServiceBound) {
            unbindService(mopDataServiceConnection);
            mopDataServiceBound = false;

            Log.i(className, "MopDataService has been unbound.");
        }

        if (mainOptimizationDriversTimeServiceBound) {
            unbindService(mainOptimizationDriversTimeServiceConnection);
            mainOptimizationDriversTimeServiceBound = false;

            Log.i(className, "MainOptimizationDriversTimeService has been unbound.");
        }
    }

    public void onCheckPo(View view) {
        Intent checkPo = new Intent(this, MapsActivityLocation.class);
        startActivity(checkPo);

        Log.i(className, "onCheckPo button has been pressed.");
    }

    public void onNavigatioMen(View view) {
        Intent onNavigatioMen = new Intent(this, NavigationMenu.class);
        startActivity(onNavigatioMen);

        Log.i(className, "onNavigatioMen button has been pressed.");
    }

    public void onMapsActivityPullof(View view) {
        Intent onMapsActivityPullof = new Intent(this, MapsActivityPullOff.class);
        startActivity(onMapsActivityPullof);

        Log.i(className, "onMapsActivityPullof button has been pressed.");
    }

    public void onRouteSchedule(View view) {
        Intent onRouteSchedule = new Intent(this, RouteScheduleForm.class);
        startActivity(onRouteSchedule);

        Log.i(className, "onRouteSchedule button has been pressed.");
    }

    public void onFindMo(View view) {
        Intent FindMo = new Intent(this, FindMop.class);
        startActivity(FindMo);

        Log.i(className, "onFindMo button has been pressed.");
    }

    public void onFindWeathe(View view) {
        Intent onFindWeathe = new Intent(this, FindWeather.class);
        startActivity(onFindWeathe);

        Log.i(className, "onFindWeathe button has been pressed.");
    }
}
