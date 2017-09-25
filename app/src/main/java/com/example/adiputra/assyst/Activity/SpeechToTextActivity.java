package com.example.adiputra.assyst.Activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.adiputra.assyst.Assyst;
import com.example.adiputra.assyst.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import com.android.internal.telephony.*;
public class SpeechToTextActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener {

    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private ImageButton btnSend;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private TextToSpeech tts;
    String messages;
    String s;
//    Assyst assyst = new Assyst();

    ArrayList<String> contactList;
    Cursor cursor;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_to_text);
        s = loadData("kontak");
        messages = s;
        messages = getContacts(s);
        tts = new TextToSpeech(this, this);
        speakOut(messages);
        saveData("kontak", "");

        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        txtSpeechInput.setText("");
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        btnSend = (ImageButton) findViewById(R.id.btnSend);

        txtSpeechInput.setText(s);

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
                    //Inside Result
//                    if(!(result.get(0).toLowerCase().contains("system end")||result.get(0).toLowerCase().contains("sistem end"))){
//                        txtSpeechInput.append(result.get(0)+" ");
//                        promptSpeechInput();
//                    }
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

    @Override
    public void onInit(int status) {
        Log.e("TTS", "Start TTS");
        if (status == TextToSpeech.SUCCESS) {
            tts.setOnUtteranceCompletedListener(this);
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                btnSpeak.setEnabled(true);
                speakOut(s);
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    private void speakOut(String text) {
        if(text != null) {
            if (text.contains("Code001")) {
                text.replace("Code001", "");
                text = "You ,got, called, from " + text;
            } else if (text.contains("Code002")) {
                text.replace("Code002", "");
                text = "You got message that said " + text;
            } else {
                text = "";
            }

            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            HashMap<String, String> myHashAlarm = new HashMap<String, String>();
            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_ALARM));
            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "SOME MESSAGE");
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, myHashAlarm);
        }
        deleteData();
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

    public void saveData(String name, String value) {
        SharedPreferences prefs = getSharedPreferences("UserData", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(name, value);
        Log.d(name + " masuk :", value);
        editor.commit();
    }

    public String loadData(String name) {
        SharedPreferences prefs = getSharedPreferences("UserData", 0);
        String data = prefs.getString(name, "");
        Log.d(name + " keluar:", data);
        return data;
    }

    public void deleteData() {
        SharedPreferences prefs = getSharedPreferences("UserData", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("kontak", "");
        Log.d("Hapus Data:", "");
        editor.commit();
    }

    public String getContacts(String number) {
        contactList = new ArrayList<String>();
        String phoneNumber = null;
        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
        Uri EmailCONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;
        StringBuffer output;
        ContentResolver contentResolver = getContentResolver();
        cursor = contentResolver.query(CONTENT_URI, null, null, null, null);
        // Iterate every contact in the phone
        if (cursor.getCount() > 0) {
            counter = 0;
            while (cursor.moveToNext()) {
                output = new StringBuffer();
                // Update the progress message
//                updateBarHandler.post(new Runnable() {
//                    public void run() {
//                        pDialog.setMessage("Reading contacts : "+ counter++ +"/"+cursor.getCount());
//                    }
//                });
                String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    //This is to read multiple phone numbers associated with the same contact
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);
                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        contactList.add(output.toString());
                        phoneNumber = phoneNumber.replace("-", "");
                        phoneNumber = phoneNumber.replace("+62", "0");
                        phoneNumber = phoneNumber.replace(" ", "");
                        if (number.equalsIgnoreCase(phoneNumber)) {
                            return name;
                        }
                    }
                    phoneCursor.close();
                }
            }
            // Dismiss the progressbar after 500 millisecondds
//            updateBarHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    pDialog.cancel();
//                }
//            }, 500);
        }
        return "No One has this contact";
    }

    @Override
    public void onUtteranceCompleted(String utteranceId) {
        this.promptSpeechInput();
    }

    public void googleNow(String res){
        String query = res;
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.setClassName("com.google.android.googlequicksearchbox", "com.google.android.googlequicksearchbox.SearchActivity");
        intent.putExtra("query", query);
        startActivity(intent);
    }
}
