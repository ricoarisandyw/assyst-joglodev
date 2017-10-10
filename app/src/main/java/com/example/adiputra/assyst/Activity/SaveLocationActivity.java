package com.example.adiputra.assyst.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adiputra.assyst.Database.DatabaseOperations;
import com.example.adiputra.assyst.Helper.SharedPref;
import com.example.adiputra.assyst.Model.Result;
import com.example.adiputra.assyst.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class SaveLocationActivity extends AppCompatActivity {
    //int id;
    RelativeLayout relativeLayout;
    String strAudio, strWifi, strAirPlane, strBluetooth, strMobileData;
    private TextView location, alamat, latitude, longitude;
    private EditText radius, message;
    Context ctx = this;
    private RequestQueue requestQueue;
    private Gson gson;
    SharedPref sharedPref = new SharedPref(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_location);

        Toolbar toolbarMap = (Toolbar) findViewById(R.id.toolbarMap);
        toolbarMap.setTitle("Save Location");
        setSupportActionBar(toolbarMap);
        toolbarMap.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbarMap.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        relativeLayout = (RelativeLayout) findViewById(R.id.relative_layout);

        requestQueue = Volley.newRequestQueue(SaveLocationActivity.this);

        //final TextView ID = (TextView) findViewById(R.id.tvId2);
        location = (TextView) findViewById(R.id.tvLocationName2);
        alamat = (TextView) findViewById(R.id.tvAlamat2);
        latitude = (TextView) findViewById(R.id.tvLatitude);
        longitude = (TextView) findViewById(R.id.tvLongitude);
        radius = (EditText) findViewById(R.id.etRadius);
        message = (EditText) findViewById(R.id.etMessage);
        Button save = (Button) findViewById(R.id.btnSave);
        Spinner audio = (Spinner) findViewById(R.id.spinnerAudio);
        audio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strAudio = parent.getItemAtPosition(position).toString();
                //Toast.makeText(SaveLocationActivity.this, "Audio : "+strAudio, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Spinner wifi = (Spinner) findViewById(R.id.spinnerWifi);
        wifi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strWifi = parent.getItemAtPosition(position).toString();
                //Toast.makeText(SaveLocationActivity.this, "Wifi : "+strWifi, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Spinner bluetooth = (Spinner) findViewById(R.id.spinnerBluetooth);
        bluetooth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strBluetooth = parent.getItemAtPosition(position).toString();
                //Toast.makeText(SaveLocationActivity.this, "Bluetooth : "+strBluetooth, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Spinner mobileData = (Spinner) findViewById(R.id.spinnerMobileData);
        mobileData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strMobileData = parent.getItemAtPosition(position).toString();
                //Toast.makeText(SaveLocationActivity.this, "Mobile Data : "+strMobileData, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Spinner airPlane = (Spinner) findViewById(R.id.spinnerAirPlane);
        airPlane.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strAirPlane = parent.getItemAtPosition(position).toString();
                //Toast.makeText(SaveLocationActivity.this, "Air Plane : "+strAirPlane, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            location.setText(extras.getString("locationName"));
            alamat.setText(extras.getString("alamat"));
            latitude.setText(extras.getString("latitude"));
            longitude.setText(extras.getString("longitude"));
            radius.setText(extras.getString("radius"));
            message.setText(extras.getString("message"));
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radius.getText().toString().equals("")){
                    Snackbar.make(relativeLayout,"Radius cannot blank!",Snackbar.LENGTH_SHORT).show();
                } else {
                    try {
                        final String id = sharedPref.loadData("id");
                        String token = sharedPref.loadData("token");
                        requestQueue = Volley.newRequestQueue(getApplicationContext());
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                        gson = gsonBuilder.create();
                        String URL = "http://api.atrama-studio.com/backend/web/api-configure?access-token="+token;
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Result result = gson.fromJson(response, Result.class);
                                        Log.i("Response : ", response);
                                        Log.i("Response : ", String.valueOf(result.isResult()));
                                        if(result.isResult()==true){
                                            Toast.makeText(SaveLocationActivity.this, result.getMessage(),Toast.LENGTH_LONG).show();
                                            Intent i = new Intent(SaveLocationActivity.this, ListActivity.class);
                                            startActivity(i);
                                            finish();
                                        } else {
                                            Toast.makeText(SaveLocationActivity.this, result.getMessage(),Toast.LENGTH_LONG).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(SaveLocationActivity.this,"Check Internet Connection",Toast.LENGTH_LONG).show();
                                    }
                                }){
                            @Override
                            protected Map<String,String> getParams(){
                                Map<String,String> params = new HashMap<String, String>();
                                params.put("user_id", id);
                                params.put("lokasi", location.getText().toString());
                                params.put("alamat", alamat.getText().toString());
                                params.put("latitude", latitude.getText().toString());
                                params.put("longitude", longitude.getText().toString());
                                params.put("radius", radius.getText().toString());
                                params.put("message", message.getText().toString());
                                params.put("wifi", strWifi);
                                params.put("bluetooth", strBluetooth);
                                params.put("audio", strAudio);
                                params.put("air_plane", strAirPlane);
                                params.put("mobile_data", strMobileData);
                                return params;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

//        SAVE.setOnClickListener(new View.OnClickListener() {
//            //Integer idObj = new Integer(id);
//            @Override
//            public void onClick(View v) {
//                //id = ID.getText().toString();
//                location = LOCATION.getText().toString();
//                alamat = ALAMAT.getText().toString();
//                latitude = LATITUDE.getText().toString();
//                longitude = LONGITUDE.getText().toString();
//                radius = RADIUS.getText().toString();
//                message = MESSAGE.getText().toString();
//
//                DatabaseOperations DB = new DatabaseOperations(ctx);
//                DB.putInformation(DB, location, alamat, latitude, longitude, radius, message);
//                Toast.makeText(getBaseContext(), "Configuration Saved", Toast.LENGTH_LONG).show();
//                Intent i = new Intent(SaveLocationActivity.this, MenuActivity.class);
//                startActivity(i);
//            }
//        });
    }
}
