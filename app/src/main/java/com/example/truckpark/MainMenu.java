package com.example.truckpark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {

    private LocationDeviceService locationDevice;
    private boolean bound = false;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        //watchMileage();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent= new Intent(this, LocationDeviceService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound){
            unbindService(connection);
            bound=false;
        }
    }
    /*
    private void watchMileage() {
       final TextView distanceView = (TextView)findViewById(R.id.distance);
       final Handler handler = new Handler();
       handler.post(new Runnable() {
           @Override
           public void run() {
               double distance=0.0;
               if (locationDevice != null){
                   //distance = locationDevice.getDistance();
                   distance = locationDevice.getLatitiude();
               }
               distanceView.setText(Double.toString(distance));
               handler.postDelayed(this,5000);
           }
       });
    }
    */

    public void onCheckPo(View view){
        Intent checkPo = new Intent(this,MapsActivity.class);
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
        Intent FindMo = new Intent(this,FindMop.class);
        startActivity(FindMo);
    }

    public void onFindWeathe(View view){
        Intent onFindWeathe = new Intent(this, FindWeather.class);
        startActivity(onFindWeathe);
    }
}
