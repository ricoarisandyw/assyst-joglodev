package com.example.adiputra.assyst.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adiputra.assyst.Activity.MenuActivity;
import com.example.adiputra.assyst.Activity.SaveLocationActivity;
import com.example.adiputra.assyst.Model.ListLocation;
import com.example.adiputra.assyst.R;

import java.util.HashMap;
import java.util.Map;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private java.util.List listData;
    final Context context;
    private RequestQueue requestQueue;
    String strAudio, strWifi, strAirPlane, strBluetooth, strMobileData, strMessage2;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id, lokasi, alamat;

        public MyViewHolder(View view) {
            super(view);
            //id = (TextView) view.findViewById(R.id.id);
            lokasi = (TextView) view.findViewById(R.id.lokasi);
            alamat = (TextView) view.findViewById(R.id.alamat);
        }
    }

    public ListAdapter(java.util.List listData, Context context) {
        this.listData = listData;
        this.context = context;
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

        //tambah (ListLocation)
        final ListLocation l = (ListLocation) listData.get(position);
        //test
        holder.alamat.setText(l.getAlamat());
        holder.lokasi.setText(l.getLokasi());
        holder.lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Toast.makeText(context, ""+l.getAlamat(), Toast.LENGTH_SHORT).show();
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.activity_detail);
                dialog.setTitle(l.getAlamat());
                //set ID
                TextView tvDetailId = (TextView) dialog.findViewById(R.id.tvDetailId);
                tvDetailId.setText(String.valueOf(l.getId()));
                //set Lokasi
                TextView tvDetailLokasi = (TextView) dialog.findViewById(R.id.tvDetailLokasi);
                tvDetailLokasi.setText(l.getLokasi());
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
                ImageButton dialogBtnDelete = (ImageButton) dialog.findViewById(R.id.btnDetailDelete);
                dialogBtnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE: // Yes button clicked
                                        try {
                                            String DELETE_LOCATION = "http://adiputra17.it.student.pens.ac.id/joglo-developer/index.php/v1/delete_location ";
                                            StringRequest stringRequest = new StringRequest(Request.Method.POST, DELETE_LOCATION,
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
                                                    }){
                                                @Override
                                                protected Map<String,String> getParams(){
                                                    Map<String,String> params = new HashMap<String, String>();
                                                    params.put("id", String.valueOf(l.getId()));
                                                    return params;
                                                }
                                            };
                                            requestQueue.add(stringRequest);
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }

                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE: // No button clicked // do nothing
                                        //Toast.makeText(context, "No Clicked", Toast.LENGTH_LONG).show(); break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure delete ?")
                                .setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener)
                                .show();
                    }
                });

                //set btnEdit
                ImageButton btnEdit = (ImageButton) dialog.findViewById(R.id.btnDetailEdit);
                btnEdit.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(context, "Go to edit", Toast.LENGTH_SHORT).show();
                        final Dialog dialogEdit = new Dialog(context);
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
                                //json
                                try {
                                    String SAVE_LOCATION = "http://adiputra17.it.student.pens.ac.id/joglo-developer/index.php/v1/update_location";
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, SAVE_LOCATION,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    Toast.makeText(context, "Configuration Updated",Toast.LENGTH_LONG).show();
                                                    dialogEdit.dismiss();
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
                                            params.put("latitude", String.valueOf(l.getLatitude()));
                                            params.put("longitude", String.valueOf(l.getLongitude()));
                                            params.put("radius", String.valueOf(l.getRadius()));
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
                Button dialogBtn = (Button) dialog.findViewById(R.id.btnDetailDismiss);
                dialogBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
