package com.example.adiputra.assyst.Fragment;

import android.Manifest;
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
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.adiputra.assyst.Activity.SaveLocationActivity;
import com.example.adiputra.assyst.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment  {

    public MapFragment() {
        // Required empty public constructor
    }

    MapView mMapView;
    private GoogleMap map;
    private GoogleMap googleMap;
    public CharSequence locationName;
    public CharSequence alamat;
    public Double latitude;
    public Double longitude;
    //private BluetoothAdapter BA;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                if (ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    googleMap.setMyLocationEnabled(true);
                    return;
                }else{
                    if(!googleMap.isMyLocationEnabled())
                        googleMap.setMyLocationEnabled(true);
                    LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
                    Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (myLocation == null) {
                        Criteria criteria = new Criteria();
                        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                        String provider = lm.getBestProvider(criteria, true);
                        myLocation = lm.getLastKnownLocation(provider);
                    }
                    if(myLocation!=null){
                        LatLng userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
//                        googleMap.addMarker(new MarkerOptions()
//                                .position(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()))
//                                .title("My Location")
//                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()), 8));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14), 1500, null);
                        //RUMUS
                        double x1, x2, y1, y2;
                        x1 = myLocation.getLatitude();
                        y1 = myLocation.getLongitude();
                        x2 = -7.276383; //lat PENS tujuan
                        y2 = 112.795164; //long PENS tujuan
                        googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(-7.276383, 112.795164))
                            .title("PENS")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                        double jarak = 1500;
//                        Double a = Math.acos(Math.sin(Math.toRadians(x0)));
//                        Double b = Math.sin(Math.toRadians(x1));
//                        Double c = Math.cos(Math.toRadians(y0-y1));
//                        Double d = Math.cos(Math.toRadians(x0));
//                        Double e = Math.cos(Math.toRadians(x1));
//                        Double result = Math.abs((6371*a*b)+(c*d*e));
                        double R = 6372.8;
                        double dLat = Math.toRadians(x2 - x1);
                        double dLon = Math.toRadians(y2 - y1);
                        x1 = Math.toRadians(x1);
                        x2 = Math.toRadians(x2);

                        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(x1) * Math.cos(x2);
                        double c = 2 * Math.asin(Math.sqrt(a));
                        double result =  (R * c)*1000;

                        Toast.makeText(getContext(), "My Location : "+
                                myLocation.getLatitude()+
                                "-"+myLocation.getLongitude()+
                                "\nJARAK : "+result+" meter", Toast.LENGTH_LONG).show();

                        if(result <= jarak ){
                            WifiManager wifi = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                            wifi.setWifiEnabled(true);
                            BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
                            ba.enable();
                            AudioManager audio = (AudioManager) getActivity().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                            audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                            Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(),Settings.Global.AIRPLANE_MODE_ON,1);

                            Toast.makeText(getActivity(),"Anda Masuk Wilayah PENS",Toast.LENGTH_LONG).show();
                            Notification notification = new NotificationCompat.Builder(getActivity())
                            .setTicker("joglo-developer")
                            .setSmallIcon(android.R.drawable.star_on)
                            .setContentTitle("Assyst-Notification")
                            .setContentText("Anda Masuk Wilayah PENS")
                            .setAutoCancel(true)
                            .build();
                            NotificationManager notifier = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
                            notifier.notify(0, notification);
                        }else{
                            Notification notification = new NotificationCompat.Builder(getActivity())
                            .setTicker("joglo-developer")
                            .setSmallIcon(android.R.drawable.star_on)
                            .setContentTitle("Assyst-Notification")
                            .setContentText("Kamu Belum Sampai")
                            .setAutoCancel(true)
                            .build();
                            NotificationManager notifier = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
                            notifier.notify(0, notification);
                        }
                    }
                }
            }
        });

        final Button btnSaveLoc = (Button) rootView.findViewById(R.id.btnSaveLocation);
        btnSaveLoc.setVisibility(View.GONE);

        //Search Locate Manually
        PlaceAutocompleteFragment places = (PlaceAutocompleteFragment)
                getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        places.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                btnSaveLoc.setVisibility(View.VISIBLE);
                locationName = place.getName();
                alamat = place.getAddress();
                latitude = place.getLatLng().latitude;
                longitude = place.getLatLng().longitude;

                LatLng mySearch = place.getLatLng();
                googleMap.addMarker(new MarkerOptions().position(mySearch).title(place.getName().toString()).snippet(place.getAddress().toString()));
                CameraPosition cameraPosition = new CameraPosition.Builder().target(mySearch).zoom(20).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                //Define Button btnSaveLocation
                btnSaveLoc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent saveLocation = new Intent(getActivity(), SaveLocationActivity.class);
                        saveLocation.putExtra("locationName", locationName);
                        saveLocation.putExtra("alamat", alamat);
                        saveLocation.putExtra("latitude", latitude.toString());
                        saveLocation.putExtra("longitude", longitude.toString());
                        startActivity(saveLocation);
                    }
                });
            }
            @Override
            public void onError(Status status) {
                Toast.makeText(getActivity(),status.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        //Filter Search
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_NONE)
                .build();
        places.setFilter(typeFilter);

        return rootView;
    }

    public void onLocationChanged(Location loc) {
        // TODO Auto-generated method stub
        loc.getLatitude();
        loc.getLongitude();
        String text="my current location is"+"lat: "+loc.getLatitude()+"long: "+loc.getLongitude();
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}