package com.example.adiputra.assyst.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.adiputra.assyst.Model.Voucher;
import com.example.adiputra.assyst.R;

import java.util.List;

/**
 * Created by adiputra on 10/23/2017.
 */

public class ListVoucherAdapter extends RecyclerView.Adapter<ListVoucherAdapter.MyViewHolder> {

    private List<Voucher> voucherList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nama_voucher, deskripsi_voucher, harga_voucher, masa_tenggang_voucher;

        public MyViewHolder(View view) {
            super(view);
            nama_voucher = (TextView) view.findViewById(R.id.tv_nama_voucher);
            deskripsi_voucher = (TextView) view.findViewById(R.id.tv_desc_voucher);
            harga_voucher = (TextView) view.findViewById(R.id.tv_harga);
            masa_tenggang_voucher = (TextView) view.findViewById(R.id.tv_masa_tenggang);
        }
    }

    public ListVoucherAdapter(List<Voucher> voucherList) {
        this.voucherList = voucherList;
    }

    @Override
    public ListVoucherAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_voucher, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListVoucherAdapter.MyViewHolder holder, int position) {
        Voucher voucher = voucherList.get(position);
        holder.nama_voucher.setText(voucher.getNama_voucher());
        holder.deskripsi_voucher.setText(voucher.getDeskripsi());
        holder.harga_voucher.setText("Harga : "+voucher.getHarga()+" P");
        holder.masa_tenggang_voucher.setText("Expired : "+voucher.getMasa_tenggang());
    }

    @Override
    public int getItemCount() {
        return voucherList.size();
    }
}
