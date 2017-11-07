package com.example.adiputra.assyst.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adiputra.assyst.Helper.SharedPref;
import com.example.adiputra.assyst.Model.Voucher;
import com.example.adiputra.assyst.R;

import java.util.List;

/**
 * Created by adiputra on 10/23/2017.
 */

public class ListVoucherAdapter extends RecyclerView.Adapter<ListVoucherAdapter.MyViewHolder> {

    Context context;
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
                        actionBtnRedeem(voucher.getId(), voucher.getProduk_id());
                    }
                });

                dialog.show();
            }
        });
    }

    public void actionBtnRedeem(int id, int produk_id){
        SharedPref sharedPref = new SharedPref(context);
        String point = sharedPref.loadData("token");
        Toast.makeText(context, ""+point, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return voucherList.size();
    }
}
