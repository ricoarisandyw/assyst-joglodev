package com.example.adiputra.assyst.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adiputra.assyst.Helper.SharedPref;
import com.example.adiputra.assyst.Model.Result;
import com.example.adiputra.assyst.Model.User;
import com.example.adiputra.assyst.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
    SharedPref sharedPref = new SharedPref(this);
    public static final String mypreference = "mypref";
    public static final int Id = 0;
    public static final String Token = "tokenKey";
    private ProgressDialog progress;
    EditText etName;
    EditText etPass;
    private RequestQueue requestQueue;
    private Gson gson;
    //Assyst assyst = new Assyst();
    List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestQueue = Volley.newRequestQueue(LoginActivity.this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        Button btnSignUp = (Button) findViewById(R.id.btnToSignUp);
        etPass = (EditText) findViewById(R.id.idPassword);
        etName = (EditText) findViewById(R.id.idUsername);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (etName.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Username tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (etPass.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Password tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else {
                    progress = new ProgressDialog(LoginActivity.this);
                    progress.setMessage("Harap tunggu...");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setIndeterminate(true);
                    progress.setProgress(0);
                    progress.setCanceledOnTouchOutside(false);
                    progress.show();
                    cekLog(etName.getText().toString().trim(), etPass.getText().toString().trim());
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    public boolean cekLog(final String uname, final String pass) {
        String url = "http://api.atrama-studio.com/backend/web/auth/login";
        StringRequest req = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Result result = gson.fromJson(response, Result.class);
                        if(result.isResult()==true){
                            Toast.makeText(LoginActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(LoginActivity.this, ""+result.getUserData().getId()+"\n"+result.getUserData().getToken(), Toast.LENGTH_SHORT).show();
                            sharedPref.saveData("id", String.valueOf(result.getUserData().getId()));
                            sharedPref.saveData("token", result.getUserData().getToken());
                            startActivity(new Intent(LoginActivity.this, ListActivity.class));
                            progress.hide();
                        }else{
                            Toast.makeText(LoginActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Log.e("Get Data : ", error.toString());
                    Toast.makeText(LoginActivity.this, "Check Internet Connection!", Toast.LENGTH_SHORT).show();
                }
            }
        )
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", uname.toString().trim());
                params.put("password", pass.toString().trim());
                return params;
            }

        };
        requestQueue.add(req);
        return true;
    }
}
