package com.example.adiputra.assyst.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adiputra.assyst.Adapter.SlideAdapter;
import com.example.adiputra.assyst.ListModulActivity;
import com.example.adiputra.assyst.ListVoucherActivity;
import com.example.adiputra.assyst.R;
import com.example.adiputra.assyst.SettingsActivity;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    SlideAdapter adapter;
    LinearLayout sliderDotspanel;
    TextView signUp;
    Button signIn;
    private int dotscount;
    private ImageView[] dots;
    FloatingActionButton fabMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.vp_image_slider);
        sliderDotspanel = (LinearLayout)findViewById(R.id.sliderDots_main);
        adapter = new SlideAdapter(this);
        viewPager.setAdapter(adapter);
        dotscount = adapter.getCount();
        dots = new ImageView[dotscount];

        for(int i=0;i<dotscount;i++){
            dots[i] =new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_nonactivedot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,8,0);

            sliderDotspanel.addView(dots[i],params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_activedot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i=0;i<dotscount;i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_nonactivedot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_activedot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fabMain = (FloatingActionButton) findViewById(R.id.fab_main);
        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View menuContent = View.inflate(MainActivity.this, R.layout.popup_menu, null);
                final Dialog popupMain = new Dialog(MainActivity.this);
                popupMain.getWindow().getAttributes().windowAnimations = R.style.PopAnimation;

                popupMain.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                popupMain.setContentView(menuContent);

                popupMain.setCanceledOnTouchOutside(true);

                WindowManager.LayoutParams wmlp = popupMain.getWindow().getAttributes();

                wmlp.gravity = Gravity.BOTTOM | Gravity.RIGHT;

                popupMain.show();

                FloatingActionButton fbSettings = (FloatingActionButton) menuContent.findViewById(R.id.fb_popupmenu_settings);
                fbSettings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                    }
                });
                FloatingActionButton fbLocation = (FloatingActionButton) menuContent.findViewById(R.id.fb_popupmenu_location);
                fbLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, ListActivity.class));
                    }
                });
                FloatingActionButton fbVoucher = (FloatingActionButton) menuContent.findViewById(R.id.fb_popupmenu_voucher);
                fbVoucher.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, ListVoucherActivity.class));
                    }
                });
                FloatingActionButton fbModul = (FloatingActionButton) menuContent.findViewById(R.id.fb_popupmenu_modul);
                fbModul.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, ListModulActivity.class));
                    }
                });
            }
        });

    }
}
