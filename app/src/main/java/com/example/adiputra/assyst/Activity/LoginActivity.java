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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adiputra.assyst.Model.User;
import com.example.adiputra.assyst.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
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

        try{
            String user = loadData("username");
            String pass = loadData("password");
            Log.d("User dan Pass", user + pass);
            if(user!=null||!user.equalsIgnoreCase("")){
                if(pass!=null||!pass.equalsIgnoreCase("")){
                    if(cekLog(user,pass)){
                        startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                    }
                }
            }
        }catch (Exception e){

        }

        requestQueue = Volley.newRequestQueue(LoginActivity.this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        Button btnSignUp = (Button) findViewById(R.id.btnToSignUp);
        etPass = (EditText) findViewById(R.id.passText);
        etName = (EditText) findViewById(R.id.nameText);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                progress=new ProgressDialog(LoginActivity.this);
                progress.setMessage("Harap tunggu...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.setProgress(0);
                progress.setCanceledOnTouchOutside(false);
                progress.show();
                if(cekLog(etName.getText().toString(),etPass.getText().toString())){
                    saveData("username", etName.getText().toString());//!!!
                    saveData("password", etPass.getText().toString());
                    startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                }else{
                    Toast.makeText(LoginActivity.this, "Username/Password Salah", Toast.LENGTH_SHORT).show();
                }
                progress.hide();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
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
    public boolean cekLog(final String uname, final String pass){
        String url = "http://adiputra17.it.student.pens.ac.id/joglo-developer/index.php/v1/show_user";
        StringRequest req = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
//                            Log.i("Response : ", response);
                            List<User> posts = Arrays.asList(gson.fromJson(response, User[].class));
//                            Log.d("Pesan", Integer.toString(posts.size()));
                            for (int i = 0;i<posts.size();i++) {
                                Log.d("Pesan 2", i+">"+posts.get(i).getUsername());
                                userList.add(new User(
                                        posts.get(i).getUsername(),
                                        posts.get(i).getEmail(),
                                        posts.get(i).getPassword(),
                                        posts.get(i).getPhone()
                                ));
                            }
                            progress.hide();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Get Data : ", error.toString());
                        Toast.makeText(LoginActivity.this, "Check Internet Connection!", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
//                params.put("user_id",user_id);
                return params;
            }
        };
        requestQueue.add(req);
        try{
            for(User user :userList) {
                if (user.getUsername().equalsIgnoreCase(uname) || user.getPhone().equalsIgnoreCase(uname)) {
                    if (user.getPassword().equalsIgnoreCase(pass)) {
                        return true;
                    }else{
                        Log.d("UserEx", user.getPassword() + "XXX" + pass);
                    }
                }else{
                    Log.d("UserEx", user.getUsername() + "XXX" + uname);
                }
                Log.d("UserEx", user.getUsername() + "XXX" + uname);
            }
        }catch(Exception e){
            Log.d("UserEx", e.toString());
        }
        return false;
    }
    public void saveData(String name, String value){
        SharedPreferences prefs = getSharedPreferences("UserData", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(name, value);
        Log.d(name + " masuk :", value);
        editor.commit();
    }
    public String loadData(String name){
        SharedPreferences prefs = getSharedPreferences("UserData", 0);
        String data = prefs.getString(name,"");
        Log.d(name + " keluar:", data);
        return data;
    }
    public void deleteData(){
        SharedPreferences prefs = getSharedPreferences("UserData", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", "");
        Log.d("Hapus Data:", "");
        editor.commit();
    }
}
