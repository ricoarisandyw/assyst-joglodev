package com.example.adiputra.assyst.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adiputra.assyst.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private ProgressDialog progress;
    private RequestQueue requestQueue;
    private Gson gson;
    EditText etNama, etPass, etEmail, etConfirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etNama = (EditText) findViewById(R.id.idUsername);
        etPass = (EditText) findViewById(R.id.idPassword);
        etConfirmPass = (EditText) findViewById(R.id.idConfirmPassword);
        etEmail = (EditText) findViewById(R.id.idEmail);
        Button btnSignUp = (Button) findViewById(R.id.btnToSignUp);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                progress=new ProgressDialog(SignUpActivity.this);
                progress.setMessage("Harap tunggu...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.setProgress(0);
                progress.setCanceledOnTouchOutside(false);
                progress.show();
                postData();
                // Perform action on click
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });


        requestQueue = Volley.newRequestQueue(SignUpActivity.this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
    }

    private void postData(){
        final String nama = etNama.getText().toString().trim();
        final String email = etEmail.getText().toString().trim();
        final String password = etPass.getText().toString().trim();
        final String phone = "081081081081";
        String INPUT_PESANAN_URL = "http://adiputra17.it.student.pens.ac.id/joglo-developer/index.php/v1/create_user";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, INPUT_PESANAN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.hide();
                        Log.d("Responses", response);
                        Toast.makeText(SignUpActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUpActivity.this,"Cek Koneksi Internet"+"\n",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("email",email);
                params.put("username",nama);
                params.put("password",password);
                params.put("phone",phone);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
