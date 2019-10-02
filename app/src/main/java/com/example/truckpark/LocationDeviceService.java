package com.example.truckpark;

import android.Manifest;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class LocationDeviceService extends Service {
    private final IBinder binder = new LocationDeviceBinder();
    public static double distanceInMeters;
    public static Location lastLocation = null;


    //////////////////////////////////
    ///Dodatek mapowy ---- działa !!!!!!!!!!!!!!!!!!!/////////////////
    static protected double lattitude;
    static protected double longitude;
    static public Marker mCurrLocationMarker;
    //////////////////////////////////

    public LocationDeviceService() {
    }


    @Override
    public void onCreate() {
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (lastLocation == null) {
                    lastLocation = location;
                }
                distanceInMeters += location.distanceTo(lastLocation);
                lastLocation = location;

                //Dodatek mapowy ---- działa !!!!!!!!!!!!!!!!!!!
                if (location != null) {
                    lattitude = location.getLatitude();
                    longitude = location.getLongitude();

                    LatLng latLng = new LatLng(lattitude, longitude);
                    if (MapsActivity.mMap !=null) {
                        if (mCurrLocationMarker != null) {
                            mCurrLocationMarker.setPosition(latLng);
                        } else {
                            mCurrLocationMarker = MapsActivity.mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Sydney"));
                        }
                        MapsActivity.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                    }
                }
                /////////////////////////////////////////////////////////
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,1, listener);
    }

    public class LocationDeviceBinder extends Binder {
        LocationDeviceService getLocationDevice() {
            return LocationDeviceService.this;
        }
    }

    public double getDistance() {
        return distanceInMeters/1000;
    }

    public double getLatitiude() {
        double r = 0.0;
        if (lastLocation != null) {
            r=lastLocation.getLatitude();
        }
        return r;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    /*
    /////////////////////////////////////////////////////////////////
    //////////////Proba zrobienia intent service, nieudana///////////
    /////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////

    static protected double lattitude;
    static protected double longitude;
    public static Location lastLocation;
    Marker mCurrLocationMarker;
    private Handler handler;




    public LocationDevice() {
        super("LocationDevice");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler = new Handler();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (lastLocation == null) {
                    lastLocation = location;
                }
                lastLocation = location;
                Log.v("dupa", lastLocation.toString());

                lattitude = location.getLatitude();
                longitude = location.getLongitude();

                LatLng latLng = new LatLng(lattitude, longitude);

                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.setPosition(latLng);
                } else {
                    mCurrLocationMarker = MapsActivity.mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Sydney"));
                }
                MapsActivity.mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, listener);
        chuj();

    }
    private void chuj(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (lastLocation==null){
                    Toast.makeText(getApplicationContext(), "dupa a nie lokalizacja", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), lastLocation.toString(), Toast.LENGTH_LONG).show();
                }
                handler.postDelayed(this,6000);
            }
        });
    }

    */

}
