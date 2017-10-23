package com.example.adiputra.assyst.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.adiputra.assyst.Adapter.ListVoucherAdapter;
import com.example.adiputra.assyst.Model.Voucher;
import com.example.adiputra.assyst.R;

import java.util.ArrayList;
import java.util.List;

public class ListVoucherActivity extends AppCompatActivity {

    private List<Voucher> voucherList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ListVoucherAdapter vAdapter;

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

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        vAdapter = new ListVoucherAdapter(voucherList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(vAdapter);

        prepareVoucherData();
    }

    private void prepareVoucherData() {
        Voucher voucher = new Voucher(
                "GEMSCHOOL NEW LOSTSAGA",
                "Dapatkan hero terlengkap dan paling hebat yang pernah ada di dunia",
                100,
                "2015");
        voucherList.add(voucher);

        voucher = new Voucher(
                "GEMSCHOOL NEW LOSTSAGA",
                "Dapatkan hero terlengkap dan paling hebat yang pernah ada di dunia",
                100,
                "2015");
        voucherList.add(voucher);

        voucher = new Voucher(
                "GEMSCHOOL NEW LOSTSAGA",
                "Dapatkan hero terlengkap dan paling hebat yang pernah ada di dunia",
                100,
                "2015");
        voucherList.add(voucher);

        voucher = new Voucher(
                "GEMSCHOOL NEW LOSTSAGA",
                "Dapatkan hero terlengkap dan paling hebat yang pernah ada di dunia",
                100,
                "2015");
        voucherList.add(voucher);

        voucher = new Voucher(
                "GEMSCHOOL NEW LOSTSAGA",
                "Dapatkan hero terlengkap dan paling hebat yang pernah ada di dunia",
                100,
                "2015");
        voucherList.add(voucher);

        vAdapter.notifyDataSetChanged();
    }
}
