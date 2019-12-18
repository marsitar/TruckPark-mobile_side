package com.example.truckpark.service.location;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.example.truckpark.view.functionality.location.MapsActivityLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;


public class LocationDeviceService extends Service {

    public final IBinder binder = new LocationDeviceBinder();
    public static double distanceInMeters;
    public static Location lastLocation = null;

    static public double latitude;
    static public double longitude;
    private Boolean isFirstTime = true;

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

                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    LatLng latLng = new LatLng(latitude, longitude);
                    if (MapsActivityLocation.mMap != null) {
                        if (isFirstTime) {
                            MapsActivityLocation.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                            isFirstTime = false;
                        } else {
                            MapsActivityLocation.mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


                            //////////////////////////////////////////////////////////////////
                            ///////////Wait for an multithreading approach
                            //////////////////////////////////////////////////////////////////
//                            List<Mop> allMops = MapsActivityLocation.requestMopDataService.getAllMopsData();
//
////                            MapsActivityLocation.allMops.clear();
//
//                            allMops.forEach(mop -> MapsActivityLocation.markersList.add(new MarkerOptions()
//                                    .position(new LatLng(mop.getCoordinate().getX(), mop.getCoordinate().getY()))
//                                    .title(mop.getPlace())
//                                    .snippet(String.format("Liczba wolnych miejsc dla Tir-ów: %d",mop.getOccupiedTruckPlaces()))));
//
//                            MapsActivityLocation.mMap.clear();
//
//                            MapsActivityLocation.markersList.forEach(marker -> MapsActivityLocation.mMap.addMarker(marker));

                        }
                    }
                }

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

        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 1, listener);

    }

    public class LocationDeviceBinder extends Binder {
        public LocationDeviceService getLocationDevice() {
            return LocationDeviceService.this;
        }
    }

    public double getDistance() {
        return distanceInMeters / 1000;
    }

    public double getLatitiude() {
        double r = 0.0;
        if (lastLocation != null) {
            r = lastLocation.getLatitude();
        }
        return r;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

}
