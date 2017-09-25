package com.example.adiputra.assyst.Service;

import android.Manifest;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adiputra.assyst.Model.ListLocation;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapIntentService extends IntentService {

    final ArrayList<LatLng> coordinates = new ArrayList<LatLng>();
    private RequestQueue requestQueue;
    private Gson gson;
    private int n;
    private String[] loc = new String[100];
    private float[] lat = new float[100];
    private float[] lng = new float[100];
    private int[] rad = new int[100];
    private String[] mes = new String[100];
    private String[] audio = new String[100];
    private String[] wifi = new String[100];
    private String[] blue = new String[100];
    private String[] air = new String[100];
    private String[] mob = new String[100];

    //SYSTEM
    private AudioManager myAudioManager;
    private BluetoothAdapter BA;

    public MapIntentService() {
        super("MapIntentService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service started...", Toast.LENGTH_SHORT).show();
        //GET DATA FROM DB
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        String GETLOC = "http://adiputra17.it.student.pens.ac.id/joglo-developer/index.php/v1/show_location";
        StringRequest req = new StringRequest(Request.Method.GET, GETLOC,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            List<ListLocation> posts = Arrays.asList(gson.fromJson(response, ListLocation[].class));
                            int i=0;
                            for (ListLocation post : posts) {
                                n = n+1;
                                loc[i] = post.getLokasi();
                                lat[i] = post.getLatitude();
                                lng[i] = post.getLongitude();
                                rad[i] = post.getRadius();
                                mes[i] = post.getMessage();
                                audio[i] = post.getAudio();
                                wifi[i] = post.getWifi();
                                blue[i] = post.getBluetooth();
                                air[i] = post.getAir_plane();
                                mob[i] = post.getMobile_data();
                                i++;
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Get Data : ", error.toString());
                        Toast.makeText(getApplication(), "Check Internet Connection!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(req);
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
                    wait(2000);
                    //Log.i("SERVICE COUNT : ", String.valueOf(count));
                    //GET CURRENT LOCATION
                    LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if (ActivityCompat.checkSelfPermission(
                            this, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(
                                    this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        return;
                    }
                    Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (myLocation == null) {
                        Criteria criteria = new Criteria();
                        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                        String provider = lm.getBestProvider(criteria, true);
                        myLocation = lm.getLastKnownLocation(provider);
                    }
                    if(myLocation!=null) {
                        Log.i("SERVICE COUNT : ", String.valueOf(myLocation.getLatitude()+" : "+myLocation.getLongitude()));
                        for(int i = 0; i <  n; ++i){
                            coordinates.add(new LatLng(lat[i], lng[i]));
                            double x1, x2, y1, y2;
                            x1 = myLocation.getLatitude();
                            y1 = myLocation.getLongitude();
                            x2 = lat[i]; //lat PENS tujuan-7.276383
                            y2 = lng[i]; //long PENS tujuan112.795164
                            double jarak = rad[i];
                            double R = 6372.8;
                            double dLat = Math.toRadians(x2 - x1);
                            double dLon = Math.toRadians(y2 - y1);
                            x1 = Math.toRadians(x1);
                            x2 = Math.toRadians(x2);
                            double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(x1) * Math.cos(x2);
                            double c = 2 * Math.asin(Math.sqrt(a));
                            double result =  (R * c)*1000;
                            Log.i("SERVICE COUNT LOC : ", loc[i]+" : "+jarak+" : "+result);
                            if(result <= jarak ){

                                //AUDIO
                                if(audio[i].equals("MUTE")){
                                    myAudioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                                    myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                }else if(audio[i].equals("SOUND")){
                                    myAudioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                                    myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                }else {
                                    myAudioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                                    myAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                                }

                                //WIFI
                                if(wifi[i].equals("ON")){
                                    WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                                    wifi.setWifiEnabled(true);
                                }else{
                                    WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                                    wifi.setWifiEnabled(false);
                                }

                                //Bluetooth
                                if(blue[i].equals("ON")){
                                    BA = BluetoothAdapter.getDefaultAdapter();
                                    BA.enable();
                                }else{
                                    BA = BluetoothAdapter.getDefaultAdapter();
                                    BA.disable();
                                }

                                //AirPlane
                                if(air[i].equals("ON")){
                                    Settings.System.putInt(getApplicationContext().getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 1);
                                }else{
                                    Settings.System.putInt(getApplicationContext().getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0);
                                }

                                //Toast.makeText(this, "Anda Masuk Wilayah "+loc[i],Toast.LENGTH_LONG).show();
                                Notification notification = new NotificationCompat.Builder(this)
                                        //.setTicker("joglo-developer")
                                        .setSmallIcon(android.R.drawable.btn_star_big_on)
                                        .setContentTitle("Anda Masuk Wilayah "+loc[i])
                                        .setContentText(""+mes[i])
                                        .setAutoCancel(true)
                                        .build();
                                NotificationManager notifier = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                notifier.notify(0, notification);
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
