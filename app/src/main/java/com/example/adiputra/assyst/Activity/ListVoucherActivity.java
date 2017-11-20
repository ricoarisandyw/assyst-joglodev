package com.example.adiputra.assyst.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adiputra.assyst.Adapter.ImageAdapter;
import com.example.adiputra.assyst.Adapter.ListVoucherAdapter;
import com.example.adiputra.assyst.Helper.SharedPref;
import com.example.adiputra.assyst.Model.Book;
import com.example.adiputra.assyst.Model.Configure;
import com.example.adiputra.assyst.Model.Result;
import com.example.adiputra.assyst.Model.Voucher;
import com.example.adiputra.assyst.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ListVoucherActivity extends AppCompatActivity {

    private TextView tvPoint;
    private List<Voucher> voucherList = new ArrayList<>();
    private List<Voucher> tempVoucherList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ListVoucherAdapter vAdapter;
    Context context;
    //parse json
    private RequestQueue requestQueue;
    private Gson gson;
    SharedPref sharedPref = new SharedPref(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_voucher);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("List Voucher");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
                Intent i = new Intent(ListVoucherActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        tvPoint = (TextView) findViewById(R.id.tv_main_point);
        tvPoint.setText(sharedPref.loadData("point"));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        vAdapter = new ListVoucherAdapter(voucherList, ListVoucherActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(vAdapter);

        prepareVoucherData();

        SearchView searchView = (SearchView) findViewById(R.id.sv_listvoucher_voucher);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(ListVoucherActivity.this, "Find : "+query,Toast.LENGTH_SHORT).show();
                tempVoucherList = filterBook(voucherList, query);
                recyclerView.setAdapter(new ListVoucherAdapter(tempVoucherList,ListVoucherActivity.this));
                for(Voucher b : tempVoucherList){
                    Log.d("Voucher AF", b.getNama_voucher());
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    this.onQueryTextSubmit("");
                }
                return true;
            }
        });

        fFilterInit();

    }

    private void prepareVoucherData() {
        requestQueue = Volley.newRequestQueue(ListVoucherActivity.this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        String id = sharedPref.loadData("id");
        String token = sharedPref.loadData("token");
        //Toast.makeText(ListVoucherActivity.this, "id : "+id+"\ntoken : "+token, Toast.LENGTH_SHORT).show();
        String GETVOUCHER = "http://api.atrama-studio.com/backend/web/api-voucher?access-token="+token;
        StringRequest req = new StringRequest(Request.Method.GET, GETVOUCHER,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        Result result = gson.fromJson(response, Result.class);
                        Log.i("Response : ", response);
                        Log.i("Response : ", String.valueOf(result.isResult()));
                        if(result.isResult()==true){
                            Toast.makeText(ListVoucherActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
                            JsonParser jsonParser = new JsonParser();
                            JsonObject jo = (JsonObject)jsonParser.parse(response);
                            JsonArray jsonArr = jo.getAsJsonArray("voucherData");
                            List<Voucher> vouchers = Arrays.asList(gson.fromJson(jsonArr, Voucher[].class));
                            for (Voucher post : vouchers) {
                                voucherList.add(new Voucher(
                                    post.getId(),
                                    post.getProduk_id(),
                                    post.getNama_voucher(),
                                    post.getKategori_voucher(),
                                    post.getDeskripsi(),
                                    post.getHarga(),
                                    post.getMasa_tenggang()
                                ));
                                tempVoucherList.add(new Voucher(
                                        post.getId(),
                                        post.getProduk_id(),
                                        post.getNama_voucher(),
                                        post.getKategori_voucher(),
                                        post.getDeskripsi(),
                                        post.getHarga(),
                                        post.getMasa_tenggang()
                                ));
                            }
                            vAdapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(ListVoucherActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ListVoucherActivity.this, "Check Internet Connection!", Toast.LENGTH_SHORT).show();
                }
            }
        );
        requestQueue.add(req);
    }

    public List<Voucher> filterBook(List<Voucher> vouchers, String value){
        List<Voucher> vouchers1 = new ArrayList<>();
        for(Voucher b : vouchers){
            if(b.getNama_voucher().toLowerCase().contains(value.toLowerCase())){
                vouchers1.add(b);
            }
        }
        if(value.equalsIgnoreCase("")){
            vouchers1 = voucherList;
        }
        return vouchers1;
    }

    public void fFilterInit(){
        Button btnTerpopuler, btnTerlaris, btnTermurah, btnTermahal;

        btnTerlaris = (Button) findViewById(R.id.btn_ListVoucher_terlaris);
        btnTerlaris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(voucherList, Voucher.VoucherName);
            }
        });
        btnTermahal = (Button) findViewById(R.id.btn_ListVoucher_termahal);
        btnTermahal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnTermurah = (Button) findViewById(R.id.btn_ListVoucher_termurah);
        btnTermurah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(voucherList, Voucher.VoucherPrice);
            }
        });
        btnTerpopuler = (Button) findViewById(R.id.btn_ListVoucher_terpopuler);
        btnTerpopuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
