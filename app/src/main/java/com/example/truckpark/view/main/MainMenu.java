package com.example.truckpark.view.main;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truckpark.R;
import com.example.truckpark.repository.CurrentState;
import com.example.truckpark.service.location.LocationDeviceService;
import com.example.truckpark.service.positionsender.SendTruckDriverPositionAndDataService;
import com.example.truckpark.view.functionality.location.MapsActivityLocation;
import com.example.truckpark.view.functionality.mop.FindMop;
import com.example.truckpark.view.functionality.navigation.NavigationMenu;
import com.example.truckpark.view.functionality.pulloff.MapsActivityPulloff;
import com.example.truckpark.view.functionality.weather.FindWeather;

public class MainMenu extends AppCompatActivity {

    public LocationDeviceService locationDeviceService;
    public SendTruckDriverPositionAndDataService sendTruckDriverPositionAndDataService;

    private boolean locationDeviceServiceBound = false;

    private boolean sendTruckDriverPositionAndDataServiceBound = false;

    private ServiceConnection locationDeviceServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder) {
            LocationDeviceService.LocationDeviceBinder locationDeviceBinder = (LocationDeviceService.LocationDeviceBinder) binder;
            locationDeviceService = locationDeviceBinder.getLocationDevice();
            locationDeviceServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            locationDeviceServiceBound = false;
        }
    };

    private ServiceConnection sendTruckDriverPositionAndDataServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder) {
            SendTruckDriverPositionAndDataService.SendTruckDriverPositionAndDataBinder sendTruckDriverPositionAndDataBinder = (SendTruckDriverPositionAndDataService.SendTruckDriverPositionAndDataBinder) binder;
            sendTruckDriverPositionAndDataService = sendTruckDriverPositionAndDataBinder.getSendTruckDriverPositionAndData();
            sendTruckDriverPositionAndDataServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            sendTruckDriverPositionAndDataServiceBound = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    @Override
    protected void onStart() {

        super.onStart();

        Intent LocationDeviceServiceIntent = new Intent(this, LocationDeviceService.class);
        bindService(LocationDeviceServiceIntent, locationDeviceServiceConnection, Context.BIND_AUTO_CREATE);

        if (!CurrentState.getCurrentStateInstance().isPositionIsBeingSent()){
            Intent sendTruckDriverPositionAndDataServiceIntent = new Intent(this, SendTruckDriverPositionAndDataService.class);
            bindService(sendTruckDriverPositionAndDataServiceIntent, sendTruckDriverPositionAndDataServiceConnection, Context.BIND_AUTO_CREATE);
            CurrentState.getCurrentStateInstance().setPositionIsBeingSent(true);
        }

    }

    @Override
    protected void onStop() {

        super.onStop();

        if (locationDeviceServiceBound) {
            unbindService(locationDeviceServiceConnection);
            locationDeviceServiceBound = false;
        }

        if (sendTruckDriverPositionAndDataServiceBound) {
            unbindService(sendTruckDriverPositionAndDataServiceConnection);
            sendTruckDriverPositionAndDataServiceBound = false;
        }
    }

    public void onCheckPo(View view) {
        Intent checkPo = new Intent(this, MapsActivityLocation.class);
        startActivity(checkPo);
    }

    public void onNavigatioMen(View view) {
        Intent onNavigatioMen = new Intent(this, NavigationMenu.class);
        startActivity(onNavigatioMen);
    }

    public void onMapsActivityPullof(View view) {
        Intent onMapsActivityPullof = new Intent(this, MapsActivityPulloff.class);
        startActivity(onMapsActivityPullof);
    }

    public void onFindMo(View view) {
        Intent FindMo = new Intent(this, FindMop.class);
        startActivity(FindMo);
    }

    public void onFindWeathe(View view) {
        Intent onFindWeathe = new Intent(this, FindWeather.class);
        startActivity(onFindWeathe);
    }
}
