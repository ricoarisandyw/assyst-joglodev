package com.example.adiputra.assyst.Fragment;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.adiputra.assyst.Activity.SpeechToTextActivity;
import com.example.adiputra.assyst.Activity.TextToSpeechActivity;
import com.example.adiputra.assyst.R;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    private Button btnEnableWifi, btnDisableWifi, btnEnableBluetooth, btnDisableBluetooth, btnVibrateAudio,
            btnNormalAudio, btnSilentAudio, btnEnableAirPlane, btnDisableAirPlane, btnEnableMobileData, btnDisableMobileData,
            btnSpeechToText, btnTextToSpeech;
    private BluetoothAdapter BA;
    private AudioManager myAudioManager;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Setting WIFI
        btnEnableWifi = (Button) getActivity().findViewById(R.id.btnEnableWifi);
        btnDisableWifi = (Button) getActivity().findViewById(R.id.btnDisableWifi);
        btnEnableWifi.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                WifiManager wifi = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                wifi.setWifiEnabled(true);
                Toast.makeText(getActivity(), "Turned On Wifi",Toast.LENGTH_LONG).show();
            }
        });
        btnDisableWifi.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                WifiManager wifi = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                wifi.setWifiEnabled(false);
                Toast.makeText(getActivity(), "Turned Off Wifi",Toast.LENGTH_LONG).show();
            }
        });

//        Setting BLUETOOTH
        BA = BluetoothAdapter.getDefaultAdapter();
        btnEnableBluetooth = (Button) getActivity().findViewById(R.id.btnEnableBluetooth);
        btnDisableBluetooth = (Button) getActivity().findViewById(R.id.btnDisableBluetooth);
        btnEnableBluetooth.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                BA.enable();
                Toast.makeText(getActivity(), "Turned On Bluetooth",Toast.LENGTH_LONG).show();
            }
        });
        btnSpeechToText = (Button) getActivity().findViewById(R.id.btnSpeechToText);
        btnTextToSpeech = (Button) getActivity().findViewById(R.id.btnTextToSpeech);
        btnTextToSpeech.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(getActivity(), TextToSpeechActivity.class);
                startActivity(i);
            }
        });
        btnSpeechToText.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(getActivity(), SpeechToTextActivity.class);
                startActivity(i);
            }
        });

//        Setting AUDIO
        btnVibrateAudio = (Button) getActivity().findViewById(R.id.btnVibrateAudio);
        btnNormalAudio = (Button) getActivity().findViewById(R.id.btnNormalAudio);
        btnSilentAudio = (Button) getActivity().findViewById(R.id.btnSilentAudio);
        myAudioManager = (AudioManager) getActivity().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        btnVibrateAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                Toast.makeText(getActivity(), "Now in Vibrate Mode", Toast.LENGTH_LONG).show();
            }
        });
        btnNormalAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                Toast.makeText(getActivity(), "Now in Ringing Mode", Toast.LENGTH_LONG).show();
            }
        });
        btnSilentAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                Toast.makeText(getActivity(), "Now in silent Mode", Toast.LENGTH_LONG).show();
            }
        });

//        Setting Air Plane Mode
        btnEnableAirPlane = (Button) getActivity().findViewById(R.id.btnEnableAirPlane);
        btnDisableAirPlane = (Button) getActivity().findViewById(R.id.btnDisableAirPlane);
        btnEnableAirPlane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(),Settings.Global.AIRPLANE_MODE_ON,1);
                Toast.makeText(getActivity(), "Air Plane Mode Enable", Toast.LENGTH_LONG).show();
            }
        });
        btnDisableAirPlane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(),Settings.Global.AIRPLANE_MODE_ON,0);
                Toast.makeText(getActivity(), "Air Plane Mode Disable", Toast.LENGTH_LONG).show();
            }
        });

//        Setting Mobile Data
        btnEnableMobileData = (Button) getActivity().findViewById(R.id.btnEnableMobileData);
        btnDisableMobileData = (Button) getActivity().findViewById(R.id.btnDisableMobileData);
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getActivity().getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        final NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        btnEnableMobileData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileInfo.getState();
                Toast.makeText(getActivity(), "Mobile Data Enable", Toast.LENGTH_LONG).show();
            }
        });
        btnDisableMobileData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileInfo.getReason();
                Toast.makeText(getActivity(), "Mobile Data Disable", Toast.LENGTH_LONG).show();
            }
        });

    }
}
