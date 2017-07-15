package com.example.adiputra.assyst.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adiputra.assyst.R;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText etName;
    EditText etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        etPass = (EditText) findViewById(R.id.passText);
        etName = (EditText) findViewById(R.id.nameText);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if(noblank()){
                    startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Something Missing", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean noblank(){

        if(etName.getText().equals("")||etName.getText().equals(null)){
            return false;
        }
        if(etPass.getText().equals("")||etPass.getText().equals(null)){

            return false;
        }
        return true;
    }
//    public void cekLog(final String uname, final String pass){
//        //open --JSON--
//        requestQueue = Volley.newRequestQueue(this);
//        requestQueue = Volley.newRequestQueue(getApplicationContext());
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//        gson = gsonBuilder.create();
//
//        String AUTH = "https://api.bukalapak.com/v2/authenticate.json";
//        StringRequest req = new StringRequest(Request.Method.POST, AUTH,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try{
//                            Log.i("Response POST : ", response);
//                            progress.hide();
//                            ModelGetUser mgu = gson.fromJson(response, ModelGetUser.class);
//                            if(mgu.getStatus().equals("ERROR")){
//                                deleteData();
//                                Toast.makeText(activityLogin.this, mgu.getMessage(), Toast.LENGTH_LONG).show();
//                            }else if(mgu.getStatus().equals("OK")){
//                                saveData("username", uname);
//                                saveData("password", pass);
//                                saveData("user_id", Integer.toString(mgu.getUser_id()));
//                                Toast.makeText(activityLogin.this, "Login berhasil", Toast.LENGTH_LONG).show();
//                                Intent i = new Intent(activityLogin.this, MainActivity.class);
//                                saveData("user_id", String.valueOf(mgu.getUser_id()));
//                                saveData("user_token", mgu.getToken());
//                                i.putExtra("userId",String.valueOf(mgu.getUser_id()));
//                                i.putExtra("userToken",mgu.getToken());
//                                startActivity(i);
//                                finish();
//                            }
//                        }catch(Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("GetUser : ", error.toString());
//                        progress.hide();
//                        Toast.makeText(activityLogin.this, "Username/Password tidak valid", Toast.LENGTH_LONG).show();
//                    }
//                }
//        ){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/x-www-form-urlencoded");
//                headers.put("Content-Type", "application/json; charset=utf-8");
//                String creds = String.format("%s:%s",uname,pass);
//                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
//                headers.put("Authorization", auth);
//                return headers;
//            }
//        };
//        requestQueue.add(req);
//        //close --JSON--
//    }
}
