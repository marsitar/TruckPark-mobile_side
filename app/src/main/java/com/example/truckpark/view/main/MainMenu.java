package com.example.truckpark.view.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.example.truckpark.service.positionsender.SendTruckDriverPositionAndDataService;
import com.example.truckpark.view.functionality.mop.FindMop;
import com.example.truckpark.view.functionality.weather.FindWeather;
import com.example.truckpark.view.functionality.location.MapsActivityLocation;
import com.example.truckpark.view.functionality.pulloff.MapsActivityPulloff;
import com.example.truckpark.view.functionality.navigation.NavigationMenu;
import com.example.truckpark.R;
import com.example.truckpark.service.location.LocationDeviceService;

public class MainMenu extends AppCompatActivity {

    public LocationDeviceService locationDevice;
    public SendTruckDriverPositionAndDataService sendTruckDriverPositionAndDataService;

    private boolean bound = false;

    private boolean senderBound = false;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder) {
            LocationDeviceService.LocationDeviceBinder locationDeviceBinder = (LocationDeviceService.LocationDeviceBinder) binder;
            locationDevice = locationDeviceBinder.getLocationDevice();
            bound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound=false;
        }
    };

    private ServiceConnection secondServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder) {
            SendTruckDriverPositionAndDataService.SendTruckDriverPositionAndDataBinder sendTruckDriverPositionAndDataBinder = (SendTruckDriverPositionAndDataService.SendTruckDriverPositionAndDataBinder) binder;
            sendTruckDriverPositionAndDataService = sendTruckDriverPositionAndDataBinder.getSendTruckDriverPositionAndData();
            senderBound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            senderBound=false;
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
        Intent intent= new Intent(this, LocationDeviceService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);


        Intent secIntent = new Intent(this, SendTruckDriverPositionAndDataService.class);
        bindService(secIntent, secondServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound){
            unbindService(connection);
            bound=false;
        }

        if (senderBound){
            unbindService(secondServiceConnection);
            senderBound=false;
        }
    }

    public void onCheckPo(View view){
        Intent checkPo = new Intent(this, MapsActivityLocation.class);
        startActivity(checkPo);
    }

    public void onNavigatioMen(View view){
        Intent onNavigatioMen = new Intent(this, NavigationMenu.class);
        startActivity(onNavigatioMen);
    }

    public void onMapsActivityPullof(View view){
        Intent onMapsActivityPullof = new Intent(this, MapsActivityPulloff.class);
        startActivity(onMapsActivityPullof);
    }

    public void onFindMo(View view){
        Intent FindMo = new Intent(this, FindMop.class);
        startActivity(FindMo);
    }

    public void onFindWeathe(View view){
        Intent onFindWeathe = new Intent(this, FindWeather.class);
        startActivity(onFindWeathe);
    }
}
