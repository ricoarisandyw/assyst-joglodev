package com.example.adiputra.assyst.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.example.adiputra.assyst.Helper.SharedPref;
import com.example.adiputra.assyst.Model.Configure;
import com.example.adiputra.assyst.Model.Result;
import com.example.adiputra.assyst.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private java.util.List<Configure> listData;
    private RequestQueue requestQueue;
    private Context context;
    private Gson gson;
    String strAudio, strWifi, strAirPlane, strBluetooth, strMobileData, strMessage2;
    Activity mActivity = null;

    public ListAdapter(List<Configure> listData, Context context) {
        super();
        this.listData = listData;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id, lokasi, alamat;
        public CardView cvList;
        public View vList;

        public MyViewHolder(View view) {
            super(view);
            //id = (TextView) view.findViewById(R.id.id);
            lokasi = (TextView) view.findViewById(R.id.lokasi);
            alamat = (TextView) view.findViewById(R.id.alamat);
            cvList = (CardView) view.findViewById(R.id.cvList);
            vList = (View) view.findViewById(R.id.vList);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        requestQueue = Volley.newRequestQueue(context);
        final SharedPref sharedPref = new SharedPref(context);
        //tambah (Configure)
        final Configure l = listData.get(position);
        //test
        holder.lokasi.setText(l.getLokasi());
        holder.alamat.setText(l.getAlamat());
        holder.cvList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Toast.makeText(context, ""+l.getLokasi(), Toast.LENGTH_SHORT).show();
                final Dialog dialog = new Dialog(v.getRootView().getContext());
                dialog.setContentView(R.layout.activity_detail);
                dialog.setTitle(l.getLokasi());
                //set ID
                TextView tvDetailId = (TextView) dialog.findViewById(R.id.tvDetailId);
                tvDetailId.setText(String.valueOf(l.getId()));
                //set Lokasi
                TextView tvDetailAlamat = (TextView) dialog.findViewById(R.id.tvDetailAlamat);
                tvDetailAlamat.setText(l.getAlamat());
                //set Message
                TextView tvDetailMessage = (TextView) dialog.findViewById(R.id.tvDetailMessage);
                tvDetailMessage.setText("- "+l.getMessage()+" -");
                //set Wifi
                TextView tvDetailWifi = (TextView) dialog.findViewById(R.id.tvDetailWifi);
                tvDetailWifi.setText(l.getWifi());
                //set Bluetooth
                TextView tvDetailBluetooth = (TextView) dialog.findViewById(R.id.tvDetailBluetooth);
                tvDetailBluetooth.setText(l.getBluetooth());
                //set Audio
                TextView tvDetailAudio = (TextView) dialog.findViewById(R.id.tvDetailAudio);
                tvDetailAudio.setText(l.getAudio());
                //set AirPlane
                TextView tvDetailAirPlane = (TextView) dialog.findViewById(R.id.tvDetailAirPlane);
                tvDetailAirPlane.setText(l.getAir_plane());
                //set MobileData
                TextView tvDetailMobileData = (TextView) dialog.findViewById(R.id.tvDetailMobileData);
                tvDetailMobileData.setText(l.getMobile_data());
                //set btnDelete
                //ImageButton dialogBtnDelete = (ImageButton) dialog.findViewById(R.id.btnDetailDelete);
                Button dialogBtnDelete = (Button) dialog.findViewById(R.id.btnDetailDelete);
                dialogBtnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                        alert.setTitle("DELETE");
                        alert.setMessage("Are you sure delete ?");
                        //alert.setPositiveButton("Yes", dialogClickListener);
                        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    String id = sharedPref.loadData("id");
                                    String token = sharedPref.loadData("token");
                                    String DELETE_LOCATION = "http://api.atrama-studio.com/backend/web/api-configure/"+l.getId()+"?access-token="+token;
                                    StringRequest stringRequest = new StringRequest(Request.Method.DELETE, DELETE_LOCATION,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Toast.makeText(context, "Data Deleted", Toast.LENGTH_SHORT).show();
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(context,"Check Your Internet Connection",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    );
                                    requestQueue.add(stringRequest);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });
                        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alert.show();
                    }
                });

                //set btnEdit
                //ImageButton btnEdit = (ImageButton) dialog.findViewById(R.id.btnDetailEdit);
                Button btnEdit = (Button) dialog.findViewById(R.id.btnDetailEdit);
                btnEdit.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(context, "Go to edit", Toast.LENGTH_SHORT).show();
                        final Dialog dialogEdit = new Dialog(v.getRootView().getContext());
                        dialogEdit.setContentView(R.layout.activity_edit);
                        dialogEdit.setTitle("Edit Configuration");
                        //editText Message 11
                        final EditText etMessage = (EditText) dialogEdit.findViewById(R.id.etDetailMessage);
                        etMessage.setText(l.getMessage());
                        //SPINNER
                        Spinner audio = (Spinner) dialogEdit.findViewById(R.id.spinnerAudio2);
                        audio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                strAudio = parent.getItemAtPosition(position).toString();
                                //Toast.makeText(context, "Audio : "+strAudio, Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {}
                        });

                        Spinner wifi = (Spinner) dialogEdit.findViewById(R.id.spinnerWifi2);
                        wifi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                strWifi = parent.getItemAtPosition(position).toString();
                                //Toast.makeText(context, "Wifi : "+strWifi, Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {}
                        });

                        Spinner bluetooth = (Spinner) dialogEdit.findViewById(R.id.spinnerBluetooth2);
                        bluetooth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                strBluetooth = parent.getItemAtPosition(position).toString();
                                //Toast.makeText(context, "Bluetooth : "+strBluetooth, Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {}
                        });

                        Spinner mobileData = (Spinner) dialogEdit.findViewById(R.id.spinnerMobileData2);
                        mobileData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                strMobileData = parent.getItemAtPosition(position).toString();
                                //Toast.makeText(context, "Mobile Data : "+strMobileData, Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {}
                        });

                        Spinner airPlane = (Spinner) dialogEdit.findViewById(R.id.spinnerAirPlane2);
                        airPlane.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                strAirPlane = parent.getItemAtPosition(position).toString();
                                //Toast.makeText(context, "Air Plane : "+strAirPlane, Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {}
                        });

                        //btnEditUpdate
                        Button btnEditUpdate = (Button) dialogEdit.findViewById(R.id.btnEditUpdate);
                        btnEditUpdate.setOnClickListener(new View.OnClickListener(){
                            public void onClick(View v) {
                                try {
                                    final String id = sharedPref.loadData("id");
                                    String token = sharedPref.loadData("token");
                                    GsonBuilder gsonBuilder = new GsonBuilder();
                                    gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                                    gson = gsonBuilder.create();
                                    String UPDATE = "http://api.atrama-studio.com/backend/web/api-configure/update?access-token="+token;
                                    StringRequest stringRequest = new StringRequest(Request.Method.PUT, UPDATE,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    Result result = gson.fromJson(response, Result.class);
                                                    if(result.isResult()==true){
                                                        Toast.makeText(context, result.getMessage(),Toast.LENGTH_LONG).show();
                                                        dialogEdit.dismiss();
                                                    }else {
                                                        Toast.makeText(context, result.getMessage(),Toast.LENGTH_LONG).show();
                                                        dialogEdit.dismiss();
                                                    }
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(context,"Check Internet Connection",Toast.LENGTH_LONG).show();
                                                }
                                            }){
                                        @Override
                                        protected Map<String,String> getParams(){
                                            Map<String,String> params = new HashMap<String, String>();
                                            params.put("id", String.valueOf(l.getId()));
                                            params.put("lokasi", l.getLokasi());
                                            params.put("alamat", l.getAlamat());
                                            params.put("latitude", l.getLatitude().toString());
                                            params.put("longitude", l.getLongitude().toString());
                                            params.put("radius", Integer.toString(l.getRadius()));
                                            params.put("message", etMessage.getText().toString());
                                            params.put("wifi", strWifi);
                                            params.put("bluetooth", strBluetooth);
                                            params.put("audio", strAudio);
                                            params.put("air_plane", strAirPlane);
                                            params.put("mobile_data", strMobileData);
                                            return params;
                                        }
                                    };
                                    requestQueue.add(stringRequest);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });
                        dialogEdit.show();
                    }
                });

                //set btnDismiss
//                Button dialogBtn = (Button) dialog.findViewById(R.id.btnDetailDismiss);
//                dialogBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
