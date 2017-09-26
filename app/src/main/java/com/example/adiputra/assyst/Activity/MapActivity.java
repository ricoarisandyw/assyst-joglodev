package com.example.adiputra.assyst.Activity;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adiputra.assyst.Fragment.SearchFragment;
import com.example.adiputra.assyst.Model.ListLocation;
import com.example.adiputra.assyst.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MapActivity extends AppCompatActivity implements PlaceSelectionListener{

    MapView mMapView;
    private Context context;
    private GoogleMap map;
    private GoogleMap googleMap;
    public CharSequence locationName;
    public CharSequence alamat;
    public Double latitude;
    public Double longitude;
    private RequestQueue requestQueue;
    private Gson gson;
    private int n;
    private Button btnSaveLoc;
    private String[] loc = new String[100];
    private float[] lat = new float[100];
    private float[] lng = new float[100];
    private int[] rad = new int[100];
    private static final String LOG_TAG = "PlaceSelectionListener";
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private static final int REQUEST_SELECT_PLACE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);

        Toolbar toolbarMap = (Toolbar) findViewById(R.id.toolbarMap);
        toolbarMap.setTitle(" ");
        setSupportActionBar(toolbarMap);
        toolbarMap.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbarMap.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
//        btnSaveLoc.setVisibility(GONE);

        //GET ALL LOCATION
        final ArrayList<LatLng> coordinates = new ArrayList<LatLng>();
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
                            //Log.i("Response : ", response);
                            //Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                            List<ListLocation> posts = Arrays.asList(gson.fromJson(response, ListLocation[].class));
                            int i=0;
                            for (ListLocation post : posts) {
                                n = n+1;
                                loc[i] = post.getLokasi();
                                lat[i] = post.getLatitude();
                                lng[i] = post.getLongitude();
                                rad[i] = post.getRadius();
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
                        Toast.makeText(context, "Check Internet Connection!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(req);

        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    googleMap.setMyLocationEnabled(true);
                    return;
                }else{
                    if(!googleMap.isMyLocationEnabled())
                        googleMap.setMyLocationEnabled(true);
                    LocationManager lm = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
                    Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (myLocation == null) {
                        Criteria criteria = new Criteria();
                        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                        String provider = lm.getBestProvider(criteria, true);
                        myLocation = lm.getLastKnownLocation(provider);
                    }
                    if(myLocation!=null){
                        LatLng userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()), 8));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10), 1500, null);

                        for(int i = 0; i <  n; ++i){
                            coordinates.add(new LatLng(lat[i], lng[i]));
                        }

                        int i=0;
                        for(LatLng cor : coordinates){
                            googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(cor.latitude, cor.longitude))
                                    .title(loc[i])
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                            i++;
                        }
                    }
                }
            }
        });

        btnSaveLoc = (Button) findViewById(R.id.btnSaveLocation);
        btnSaveLoc.setVisibility(GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            try {
                Intent intent = new PlaceAutocomplete.IntentBuilder
                        (PlaceAutocomplete.MODE_OVERLAY)
                        .setBoundsBias(BOUNDS_MOUNTAIN_VIEW)
                        .build(MapActivity.this);
                startActivityForResult(intent, REQUEST_SELECT_PLACE);
            } catch (GooglePlayServicesRepairableException |
                    GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPlaceSelected(Place place) {
        Toast.makeText(MapActivity.this, "Lokasi : "+place.getName(), Toast.LENGTH_SHORT).show();
        Log.i(LOG_TAG, "Place Selected: " + place.getName());
        btnSaveLoc.setVisibility(VISIBLE);
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
                Intent saveLocation = new Intent(getApplicationContext(), SaveLocationActivity.class);
                saveLocation.putExtra("locationName", locationName);
                saveLocation.putExtra("alamat", alamat);
                saveLocation.putExtra("latitude", latitude.toString());
                saveLocation.putExtra("longitude", longitude.toString());
                startActivity(saveLocation);
            }
        });
        if (!TextUtils.isEmpty(place.getAttributions())){
            //attributionsTextView.setText(Html.fromHtml(place.getAttributions().toString()));
            Toast.makeText(MapActivity.this, ""+ Html.fromHtml(place.getAttributions().toString()), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(Status status) {
        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_PLACE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                this.onPlaceSelected(place);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                this.onError(status);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
