package com.example.adiputra.assyst.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adiputra.assyst.Model.ListLocation;
import com.example.adiputra.assyst.R;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private java.util.List listData;
    final Context context;

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

        //final Button btnHapus = (Button) itemView.findViewById(R.id.btnHapus);

//        btnHapus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//                alertDialogBuilder.setTitle("Hapus Data");
//                alertDialogBuilder
//                        .setMessage("Anda ingin menghapus data ...")
//                        .setCancelable(false)
//                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,int id) {
//                                Toast.makeText(v.getContext(),"Configuration Deleted", Toast.LENGTH_LONG).show();
//                            }
//                        })
//                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,int id) {
//                                dialog.cancel();
//                            }
//                        });
//                // create alert dialog
//                AlertDialog alertDialog = alertDialogBuilder.create();
//                // show it
//                alertDialog.show();
//            }
//        });


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //tambah (ListLocation)
        final ListLocation l = (ListLocation) listData.get(position);
        //holder.id.setText(String.valueOf(l.getId()));
        //test
        holder.alamat.setText(l.getAlamat());
        holder.lokasi.setText(l.getLokasi());
        holder.lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Intent i = new Intent(v.getContext(), DetailActivity.class);
                //i.putExtra("id",String.valueOf(l.getId()));
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle(l.getAlamat());
                alertDialogBuilder
                    .setMessage(""+l.getMessage())
                    .setCancelable(false)
//                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog,int id) {
//                            Toast.makeText(v.getContext(),"Configuration Deleted", Toast.LENGTH_LONG).show();
//                        }
//                    })
                    .setNegativeButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                        }
                    });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}