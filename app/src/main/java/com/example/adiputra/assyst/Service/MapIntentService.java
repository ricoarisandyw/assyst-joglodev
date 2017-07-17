package com.example.adiputra.assyst.Service;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.adiputra.assyst.Helper.GPSTracker;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MapIntentService extends IntentService {

//    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
//    private GoogleApiClient mGoogleApiClient;
//    private LocationRequest mLocationRequest;
//    private double currentLatitude;
//    private double currentLongitude;
//    private Location mLastLocation;
//    // GPSTracker class
//    GPSTracker gps;

    public MapIntentService() {
        super("MapIntentService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service started...", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service stoped...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        this.stopSelf();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this) {
            for (int count = 0; ; count++) {
                try {
                    wait(1000);
                    Log.i("SERVICE COUNT : ", String.valueOf(count));
//                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                            this, Manifest.permission.ACCESS_COARSE_LOCATION)
//                            != PackageManager.PERMISSION_GRANTED) {
//                        return;
//                    }
//                    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//
//                    if (mLastLocation == null) {
//                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) this);
//                    } else {
//                        currentLatitude = mLastLocation.getLatitude();
//                        currentLongitude = mLastLocation.getLongitude();
//
//                        //Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
//                    }


//                    // create class object
//                    gps = new GPSTracker(MapIntentService.this);
//
//                    // check if GPS enabled
//                    if(gps.canGetLocation()){
//
//                        double latitude = gps.getLatitude();
//                        double longitude = gps.getLongitude();
//                        Log.i("SERVICE COUNT : ", String.valueOf(count)+ ":" + latitude+" & "+longitude);
//
//                        // \n is for new line
//                        //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
//                    }else{
//                        // can't get location
//                        // GPS or Network is not enabled
//                        // Ask user to enable GPS/network in settings
//                        gps.showSettingsAlert();
//                    }


                    //Toast.makeText(getApplicationContext(), "Count : "+count, Toast.LENGTH_SHORT).show();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
