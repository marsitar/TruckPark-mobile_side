package com.example.truckpark.service.location;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.example.truckpark.repository.CurrentPosition;
import com.example.truckpark.view.functionality.location.MapsActivityLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import java.util.Optional;


public class LocationDeviceService extends Service {

    public final IBinder binder = new LocationDeviceBinder();
    private Boolean isFirstTime = true;

    public LocationDeviceService() {
    }


    @Override
    public void onCreate() {
        LocationListener listener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

                setCurrentPositionInRepository(location);

                LatLng latLng = new LatLng(CurrentPosition.getCurrentPositionInstance().getCurrentX()
                        , CurrentPosition.getCurrentPositionInstance().getCurrentY());

                moveCameraOnMapViews(latLng);

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

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, listener);
        }

    }

    private void setCurrentPositionInRepository(Location location) {
        Optional.ofNullable(location)
                .map(Location::getLatitude)
                .ifPresent(latitude ->
                        CurrentPosition
                                .getCurrentPositionInstance()
                                .setCurrentX(latitude));

        Optional.ofNullable(location)
                .map(Location::getLongitude)
                .ifPresent(longitude ->
                        CurrentPosition
                                .getCurrentPositionInstance()
                                .setCurrentY(longitude));
    }

    private void moveCameraOnMapViews(LatLng latLng) {
        if (MapsActivityLocation.mMap != null) {
            if (isFirstTime) {
                CurrentPosition.getCurrentPositionInstance().setLocationOn(true);
                MapsActivityLocation.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                isFirstTime = false;
            } else {
                MapsActivityLocation.mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }
    }

    public class LocationDeviceBinder extends Binder {
        public LocationDeviceService getLocationDevice() {
            return LocationDeviceService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

}
