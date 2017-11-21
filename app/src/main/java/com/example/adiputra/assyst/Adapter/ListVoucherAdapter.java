package com.example.adiputra.assyst.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adiputra.assyst.Helper.SharedPref;
import com.example.adiputra.assyst.Model.Result;
import com.example.adiputra.assyst.Model.Voucher;
import com.example.adiputra.assyst.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adiputra on 10/23/2017.
 */

public class ListVoucherAdapter extends RecyclerView.Adapter<ListVoucherAdapter.MyViewHolder> {

    //tes
    Context context;
    private RequestQueue requestQueue;
    private RequestQueue requestQueue2;
    private Gson gson;
    private List<Voucher> voucherList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nama_voucher, deskripsi_voucher, harga_voucher, masa_tenggang_voucher;
        public RelativeLayout relativeLayout;
        public CardView cardViewVoucher;

        public MyViewHolder(View view) {
            super(view);
            cardViewVoucher = (CardView) view.findViewById(R.id.cv_voucher_item);
            nama_voucher = (TextView) view.findViewById(R.id.tv_nama_voucher);
            deskripsi_voucher = (TextView) view.findViewById(R.id.tv_desc_voucher);
            harga_voucher = (TextView) view.findViewById(R.id.tv_harga);
            masa_tenggang_voucher = (TextView) view.findViewById(R.id.tv_masa_tenggang);
        }
    }

    public ListVoucherAdapter(List<Voucher> voucherList, Context context) {
        super();
        this.voucherList = voucherList;
        this.context = context;
    }

    @Override
    public ListVoucherAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_voucher, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListVoucherAdapter.MyViewHolder holder, int position) {
        final SharedPref sharedPref = new SharedPref(context);
        final Voucher voucher = voucherList.get(position);
        holder.nama_voucher.setText(voucher.getNama_voucher().toUpperCase());
        holder.deskripsi_voucher.setText(voucher.getDeskripsi());
        holder.harga_voucher.setText(voucher.getHarga()+" P");
        holder.masa_tenggang_voucher.setText("Expired : "+voucher.getMasa_tenggang());
        holder.cardViewVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(v.getRootView().getContext());
                dialog.setContentView(R.layout.activity_detail_voucher);
                dialog.setTitle(voucher.getNama_voucher().toUpperCase());

                TextView tvVoucherHarga = (TextView) dialog.findViewById(R.id.v_detail_harga);
                tvVoucherHarga.setText(String.valueOf(voucher.getHarga()));

                TextView tvVoucherDeskripsi = (TextView) dialog.findViewById(R.id.v_detail_deskripsi);
                tvVoucherDeskripsi.setText(String.valueOf(voucher.getDeskripsi()));

                TextView tvVoucherMasaTenggang = (TextView) dialog.findViewById(R.id.v_detail_masa_tenggang);
                tvVoucherMasaTenggang.setText("Expired : "+String.valueOf(voucher.getMasa_tenggang()));

                Button btnRedeem = (Button) dialog.findViewById(R.id.btn_redeem_point);
                btnRedeem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), ""+String.valueOf(voucher.getId())+" "+String.valueOf(voucher.getProduk_id()), Toast.LENGTH_SHORT).show();
                        String point = sharedPref.loadData("point");
                        int _point = Integer.parseInt(point);
                        int _harga = Integer.parseInt(String.valueOf(voucher.getHarga()));
                        final int total_point = (_point)-(_harga);
                        actionBtnRedeem(voucher.getId(), voucher.getProduk_id(), _harga, total_point);
                    }
                });

                dialog.show();
            }
        });
    }

    public void actionBtnRedeem(int id, final int produk_id, final int _harga, final int total_point){
        SharedPref sharedPref = new SharedPref(context);
        final String user_id = sharedPref.loadData("id");
        String token = sharedPref.loadData("token");
        final String point_id = sharedPref.loadData("point_id");
        requestQueue = Volley.newRequestQueue(context);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        String PUTPOINT_URL = "http://api.atrama-studio.com/backend/web/api-point/update?user_id="+user_id+"&access-token="+token;
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, PUTPOINT_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Result result = gson.fromJson(response, Result.class);
                        Log.i(null,"response : "+response);
                        if(result.isResult()==true){
                            Toast.makeText(context, "Your Point : "+total_point, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();
                }
            }
        ){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("total", String.valueOf(total_point));
                return params;
            }
        };
        requestQueue.add(stringRequest);

        requestQueue2 = Volley.newRequestQueue(context);
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        String POSTLOGPOINT = "http://api.atrama-studio.com/backend/web/api-log-point?access-token="+token;
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, POSTLOGPOINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Result result = gson.fromJson(response, Result.class);
                            Log.i(null,"response : "+response);
                            if(result.isResult()==true){
                                Toast.makeText(context, "Log point success", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("transaksi_point", String.valueOf(_harga));
                params.put("produk_id", String.valueOf(produk_id));
                params.put("point_id", String.valueOf(point_id));
                return params;
            }
        };
        requestQueue2.add(stringRequest2);
    }

    @Override
    public int getItemCount() {
        return voucherList.size();
    }
}
