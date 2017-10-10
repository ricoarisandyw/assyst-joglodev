package com.example.adiputra.assyst.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adiputra.assyst.Adapter.ListAdapter;
import com.example.adiputra.assyst.Helper.SharedPref;
import com.example.adiputra.assyst.Model.Configure;
import com.example.adiputra.assyst.Model.Result;
import com.example.adiputra.assyst.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    SharedPref sharedPref = new SharedPref(this);
    private RecyclerView recyclerView;
    private List<Configure> listData = new ArrayList<>();
    private ListAdapter mAdapter;
    //defineDatabaseContext
    Context context;
    //parse json
    private RequestQueue requestQueue;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("List Configure");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
                Intent i = new Intent(ListActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListActivity.this, MapActivity.class);
                startActivity(i);
            }
        });

//        final Animation animPopin = AnimationUtils.loadAnimation(this, R.anim.pop_in);
//        Animation animPopout = AnimationUtils.loadAnimation(this, R.anim.pop_out);
//        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                View menuContent = View.inflate(ListActivity.this, R.layout.popup_menu, null);
//                final Dialog popup = new Dialog(ListActivity.this);
//                popup.getWindow().getAttributes().windowAnimations = R.style.PopAnimation;
//
//                popup.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                popup.setContentView(menuContent);
////                FloatingActionButton fb = (FloatingActionButton) menuContent.findViewById(R.id.fb_popupmenu_location);
////                popup.setTitle("Select Filter");
//
//                popup.setCanceledOnTouchOutside(true);
//
//                WindowManager.LayoutParams wmlp = popup.getWindow().getAttributes();
//
//                wmlp.gravity = Gravity.BOTTOM | Gravity.RIGHT;
//
//                popup.show();
//            }
//        });

        mAdapter = new ListAdapter(listData, context);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        requestQueue = Volley.newRequestQueue(ListActivity.this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        String id = sharedPref.loadData("id");
        String token = sharedPref.loadData("token");
        Toast.makeText(ListActivity.this, "id : "+id+"\ntoken : "+token, Toast.LENGTH_SHORT).show();
        String GETLOC = "http://api.atrama-studio.com/backend/web/api-configure?user_id="+id+"&access-token="+token;
        StringRequest req = new StringRequest(Request.Method.GET, GETLOC,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        Result result = gson.fromJson(response, Result.class);
                        Log.i("Response : ", response);
                        Log.i("Response : ", String.valueOf(result.isResult()));
                        if(result.isResult()==true){
                            Toast.makeText(ListActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
                            JsonParser jsonParser = new JsonParser();
                            JsonObject jo = (JsonObject)jsonParser.parse(response);
                            JsonArray jsonArr = jo.getAsJsonArray("configureData");
                            List<Configure> configures = Arrays.asList(gson.fromJson(jsonArr, Configure[].class));
                            for (Configure post : configures) {
                                listData.add(new Configure(
                                    post.getId(),
                                    post.getLokasi(),
                                    post.getAlamat(),
                                    post.getMessage(),
                                    post.getWifi(),
                                    post.getBluetooth(),
                                    post.getAudio(),
                                    post.getAir_plane(),
                                    post.getMobile_data()
                                ));
                            }
                            mAdapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(ListActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ListActivity.this, "Check Internet Connection!", Toast.LENGTH_SHORT).show();
                }
            }
        );
        requestQueue.add(req);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                break;
            case R.id.action_profil:
                Toast.makeText(this, "Profil selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }
}
