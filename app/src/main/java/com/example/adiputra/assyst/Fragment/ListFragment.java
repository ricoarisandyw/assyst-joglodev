package com.example.adiputra.assyst.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adiputra.assyst.Adapter.ListAdapter;
import com.example.adiputra.assyst.Model.ListLocation;
import com.example.adiputra.assyst.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    private RecyclerView recyclerView;
    private List listData = new ArrayList<>();
    private ListAdapter mAdapter;

    //defineDatabaseContext
    Context CTX;

    //parse json
    private RequestQueue requestQueue;
    private Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        CTX = container.getContext();
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        String GETLOC = "http://adiputra17.it.student.pens.ac.id/joglo-developer/index.php/v1/show_location";
        StringRequest req = new StringRequest(Request.Method.POST, GETLOC,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            Log.i("Response : ", response);
                            //Toast.makeText(CTX, response, Toast.LENGTH_SHORT).show();
                            List<ListLocation> posts = Arrays.asList(gson.fromJson(response, ListLocation[].class));
                            for (ListLocation post : posts) {
                                listData.add(new ListLocation(
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
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Get Data : ", error.toString());
                    }
                }
        );
        requestQueue.add(req);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
//        getActivity().finish();
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new ListAdapter(listData, CTX);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        //prepareListData();
    }
    //WITH DATABASE
//    private void prepareListData() {
//        try{
//            DatabaseOperations DOP = new DatabaseOperations(CTX);
//            Cursor CR = DOP.getInformation(DOP);
//            CR.moveToFirst();
//            //String ID;
//            String LOCATION = "";
//            String ALAMAT = "";
//            String LATITUDE = "";
//            String LONGITUDE = "";
//            String RADIUS = "";
//            String MESSAGE = "";
//            do{
//                //ID = CR.getString(0);
//                LOCATION = CR.getString(0);
//                ALAMAT = CR.getString(1);
//                LATITUDE = CR.getString(2);
//                LONGITUDE = CR.getString(3);
//                RADIUS = CR.getString(4);
//                MESSAGE = CR.getString(5);
//                ListLocation l = new ListLocation(LOCATION, ALAMAT, LATITUDE, LONGITUDE, RADIUS, MESSAGE);
//                listData.add(l);
//                Collections.reverse(listData);
//            }while(CR.moveToNext());
//        }catch(Exception e){
//            System.out.println(e);
//        }
//    }
}
