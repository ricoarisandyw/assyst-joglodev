package com.example.adiputra.assyst.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adiputra.assyst.Database.DatabaseOperations;
import com.example.adiputra.assyst.R;

public class SaveLocationActivity extends AppCompatActivity {

    //int id;
    String id, location, alamat, latitude, longitude, radius, message;
    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_location);
        getSupportActionBar().hide();

        //final TextView ID = (TextView) findViewById(R.id.tvId2);
        final TextView LOCATION = (TextView) findViewById(R.id.tvLocationName2);
        final TextView ALAMAT = (TextView) findViewById(R.id.tvAlamat2);
        final TextView LATITUDE = (TextView) findViewById(R.id.tvLatitude);
        final TextView LONGITUDE = (TextView) findViewById(R.id.tvLongitude);
        final EditText RADIUS = (EditText) findViewById(R.id.etRadius);
        final EditText MESSAGE = (EditText) findViewById(R.id.etMessage);
        Button SAVE = (Button) findViewById(R.id.btnSave);
//        Spinner SELECT = (Spinner) findViewById(R.id.spinnerAudio);
//
//        addItemsOnSELECT();
//        addListenerOnSpinnerItemSelection();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //ID.setText(extras.getString("id"));
            LOCATION.setText(extras.getString("locationName"));
            ALAMAT.setText(extras.getString("alamat"));
            LATITUDE.setText(extras.getString("latitude"));
            LONGITUDE.setText(extras.getString("longitude"));
            RADIUS.setText(extras.getString("radius"));
            MESSAGE.setText(extras.getString("message"));
        }

        SAVE.setOnClickListener(new View.OnClickListener() {
            //Integer idObj = new Integer(id);
            @Override
            public void onClick(View v) {

                //id = ID.getText().toString();
                location = LOCATION.getText().toString();
                alamat = ALAMAT.getText().toString();
                latitude = LATITUDE.getText().toString();
                longitude = LONGITUDE.getText().toString();
                radius = RADIUS.getText().toString();
                message = MESSAGE.getText().toString();

                DatabaseOperations DB = new DatabaseOperations(ctx);
                DB.putInformation(DB, location, alamat, latitude, longitude, radius, message);
                Toast.makeText(getBaseContext(), "Configuration Saved", Toast.LENGTH_LONG).show();
                Intent i = new Intent(SaveLocationActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });
    }
}
