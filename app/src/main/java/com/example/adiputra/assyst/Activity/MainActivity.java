package com.example.adiputra.assyst.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adiputra.assyst.Adapter.SlideAdapter;
import com.example.adiputra.assyst.Helper.SharedPref;
import com.example.adiputra.assyst.Helper.SpeakOut;
import com.example.adiputra.assyst.Model.Result;
import com.example.adiputra.assyst.R;
import com.example.adiputra.assyst.Service.MapIntentService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    SlideAdapter adapter;
    LinearLayout sliderDotspanel;
    TextView signUp;
    Button signIn;
    private int dotscount;
    private ImageView[] dots;
    FloatingActionButton fabMain;
    private RequestQueue requestQueue;
    private Gson gson;
    SharedPref sharedPref = new SharedPref(this);
    private TextView tvMainPoint, tvUsername;

    //TEST STREAM MP3
    static final String AUDIO_PATH =
            "http://yourHost/play.mp3";
    private MediaPlayer mediaPlayer;
    private int playbackPosition=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new SpeakOut(this,"Welcome to Assyst. Your personal assistant.");

        String username = sharedPref.loadData("username");
        tvUsername = (TextView) findViewById(R.id.tv_username);
        tvUsername.setText("Hi, "+username+"!");

        tvMainPoint = (TextView) findViewById(R.id.tv_main_point);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        String id = sharedPref.loadData("id");
        String token = sharedPref.loadData("token");
        String GETPOINT_URL = "http://api.atrama-studio.com/backend/web/api-point?user_id="+id+"&access-token="+token;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GETPOINT_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Result result = gson.fromJson(response, Result.class);
                        Log.i(null,"response : "+response);
                        if(result.isResult()==true){
                            Toast.makeText(MainActivity.this, "Your Point : "+result.getPointData().getTotal(), Toast.LENGTH_SHORT).show();
                            sharedPref.saveData("point", String.valueOf(result.getPointData().getTotal()));
                            sharedPref.saveData("point_id", String.valueOf(result.getPointData().getId()));
                            tvMainPoint.setText(String.valueOf(result.getPointData().getTotal()));
                        }else{
                            Toast.makeText(MainActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                }
            }
        );
        requestQueue.add(stringRequest);

        viewPager = (ViewPager)findViewById(R.id.vp_image_slider);
        sliderDotspanel = (LinearLayout)findViewById(R.id.sliderDots_main);
        adapter = new SlideAdapter(this);
        viewPager.setAdapter(adapter);
        dotscount = adapter.getCount();
        dots = new ImageView[dotscount];
        ImageView ivActiveBook = (ImageView) findViewById(R.id.iv_main_activeBook);
        ivActiveBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DetailModulActivity.class));
            }
        });

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

        Intent service = new Intent(MainActivity.this, MapIntentService.class);
        startService(service);

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
                        finish();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            //Intent intent = new Intent(MainActivity.this, class);
            //intent.addCategory(Intent.CATEGORY_HOME);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //startActivity(intent);
            finish();
            return true;
        }
        return false;
    }

    private void playAudio(String url) throws Exception
    {
        killMediaPlayer();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(url);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    private void killMediaPlayer() {
        if(mediaPlayer!=null) {
            try {
                mediaPlayer.release();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*private void playLocalAudio_UsingDescriptor() throws Exception {

        AssetFileDescriptor fileDesc = getResources().openRawResourceFd(
                R.raw.music_file);
        if (fileDesc != null) {

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fileDesc.getFileDescriptor(), fileDesc
                    .getStartOffset(), fileDesc.getLength());

            fileDesc.close();

            mediaPlayer.prepare();
            mediaPlayer.start();
        }
    }

    private void playLocalAudio() throws Exception
    {
        mediaPlayer = MediaPlayer.create(this, R.raw.music_file);
        mediaPlayer.start();
    }*/
}
