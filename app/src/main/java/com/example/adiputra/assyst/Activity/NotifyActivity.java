package com.example.adiputra.assyst.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.adiputra.assyst.R;

public class NotifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        getSupportActionBar().hide();
    }
}