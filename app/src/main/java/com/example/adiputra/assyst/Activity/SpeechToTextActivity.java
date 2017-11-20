package com.example.adiputra.assyst.Activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.adiputra.assyst.Assyst;
import com.example.adiputra.assyst.Helper.ContactReader;
import com.example.adiputra.assyst.Helper.SharedPref;
import com.example.adiputra.assyst.Helper.SpeakOut;
import com.example.adiputra.assyst.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class SpeechToTextActivity extends AppCompatActivity {

    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private ImageButton btnSend;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private TextToSpeech tts;
    SharedPref sharedPref;
    String messages;
    String text;
//    Assyst assyst = new Assyst();

    ArrayList<String> contactList;
    Cursor cursor;
    int counter;
    SpeakOut speakOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_to_text);
        sharedPref = new SharedPref(this);

//        text = sharedPref.loadData("kontak");
//        messages = text;
//        messages = new ContactReader(this).findByPhone(text).getName();

        speakOut = new SpeakOut(this);
        speakOut.speakOut("Welcome, to the mobile legend");

        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        txtSpeechInput.setText("");
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        btnSend = (ImageButton) findViewById(R.id.btnSend);

        txtSpeechInput.setText(text);

        // hide the action bar

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                send();
            }
        });

        TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
        if (mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        }
    }

    private void send() {

    }

    public void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String res = result.get(0).toLowerCase();
                    if (res.contains("Bejo")||res.contains("bejo")) {
                        res.replace("Bejo ", "");
                        res.replace("bejo ", "");
                        googleNow(res);
                        res = "Google Start" + res;
                    }else if(res.contains("yes")||res.contains("Yes")){

                    }else if(res.contains("no")||res.contains("No")){

                    }else{

                    }
                    txtSpeechInput.setText(result.get(0));
                }
                break;
            }
        }
    }

    PhoneStateListener phoneStateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            if (state == TelephonyManager.CALL_STATE_RINGING) {
                //Incoming call: Pause music
            } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                //Not in call: Play music
            } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                //A call is dialing, active or on hold
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    };

    public void googleNow(String res){
        String query = res;
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.setClassName("com.google.android.googlequicksearchbox", "com.google.android.googlequicksearchbox.SearchActivity");
        intent.putExtra("query", query);
        startActivity(intent);
    }
}
