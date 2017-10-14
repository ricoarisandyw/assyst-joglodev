package com.example.adiputra.assyst.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adiputra.assyst.Model.Result;
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
    EditText etName, etPass, etEmail, etConfirmPass;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        linearLayout = (LinearLayout) findViewById(R.id.linear_layout);

        etEmail = (EditText) findViewById(R.id.idEmail);
        etName = (EditText) findViewById(R.id.idUsername);
        etPass = (EditText) findViewById(R.id.idPassword);
        etConfirmPass = (EditText) findViewById(R.id.idConfirmPassword);
        Button btnSignUp = (Button) findViewById(R.id.btnToSignUp);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (etEmail.getText().toString().equals("")) {
                    //Toast.makeText(SignUpActivity.this, "Email cannot blank!", Toast.LENGTH_SHORT).show();
                    Snackbar.make(linearLayout,"Email cannot blank!",Snackbar.LENGTH_SHORT).show();
                } else if (etName.getText().toString().equals("")) {
                    //Toast.makeText(SignUpActivity.this, "Username cannot blank!", Toast.LENGTH_SHORT).show();
                    Snackbar.make(linearLayout,"Username cannot blank!",Snackbar.LENGTH_SHORT).show();
                } else if (etPass.getText().toString().equals("")) {
                    //Toast.makeText(SignUpActivity.this, "Password cannot blank!", Toast.LENGTH_SHORT).show();
                    Snackbar.make(linearLayout,"Password cannot blank!",Snackbar.LENGTH_SHORT).show();
                } else if (etConfirmPass.getText().toString().equals("")) {
                    //Toast.makeText(SignUpActivity.this, "Confirm Password cannot blank!", Toast.LENGTH_SHORT).show();
                    Snackbar.make(linearLayout,"Confirm Password cannot blank!",Snackbar.LENGTH_SHORT).show();
                } else if (!etPass.getText().toString().equals(etConfirmPass.getText().toString())) {
                    //Toast.makeText(SignUpActivity.this, "Password & Confirm Password not match!", Toast.LENGTH_SHORT).show();
                    Snackbar.make(linearLayout,"Password & Confirm Password not match",Snackbar.LENGTH_SHORT).show();
                } else {
                    Log.i(null,"response : masuk else");
                    progress = new ProgressDialog(SignUpActivity.this);
                    progress.setMessage("Please wait...");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    //progress.setIndeterminate(true);
                    progress.setProgress(0);
                    progress.setCanceledOnTouchOutside(false);
                    progress.show();
                    Log.i(null,"response : Progress show()");
                    postData();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }

    private void postData(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        Log.i(null,"response : masuk ke postData()");
        final String name = etName.getText().toString().trim();
        final String email = etEmail.getText().toString().trim();
        final String password = etPass.getText().toString().trim();
        //final String phone = "081081081081";
        String SIGNUP_URL = "http://api.atrama-studio.com/backend/web/auth/signup";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SIGNUP_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Result result = gson.fromJson(response, Result.class);
                        Log.i(null,"response : "+response);
                        if(result.isResult()==true){
                            Toast.makeText(SignUpActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            progress.hide();
                        }else{
                            Toast.makeText(SignUpActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
                            progress.hide();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SignUpActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    Snackbar.make(linearLayout, "Check Internet Connection",Snackbar.LENGTH_SHORT).show();
                }
            }
        )
        {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("email",email.toString());
                params.put("username",name.toString());
                params.put("password",password.toString());
                Log.i(null, "response : "+email+name+password);
                //params.put("phone",phone);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
