package com.example.adiputra.assyst.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import com.example.adiputra.assyst.Fragment.MapFragment;
import com.example.adiputra.assyst.R;
import com.example.adiputra.assyst.Fragment.SettingFragment;
import com.example.adiputra.assyst.Service.MapIntentService;

public class MenuActivity extends AppCompatActivity {

//    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent i = new Intent(getBaseContext(), MapIntentService.class);
        startService(i);

        //Daftar Menu pada Tab
        TabLayout tab = (TabLayout) findViewById(R.id.tab1);
        tab.addTab(tab.newTab().setText("LIST"));
        tab.addTab(tab.newTab().setText("MAP"));
        tab.addTab(tab.newTab().setText("SETTING"));

        //Load pertama pada tab
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.listFragment,new ListFragment());
        ft.commit();

        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    //intent ke ListFragment
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.listFragment,new ListFragment());
                    ft.addToBackStack(null);
                    ft.commit();
                }else if(tab.getPosition()==1){
                    //intent ke MapFragment
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.listFragment,new MapFragment());
                    ft.addToBackStack(null);
                    ft.commit();
                }else if(tab.getPosition()==2){
                    //intent ke SettingFragment
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.listFragment,new SettingFragment());
                    ft.commit();
                }
            }

            //Tab unselected
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            //Tab reselected
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}