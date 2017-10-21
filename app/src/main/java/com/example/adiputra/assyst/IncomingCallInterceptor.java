package com.example.adiputra.assyst;

/**
 * Created by rickReaper on 6/15/2017.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.adiputra.assyst.Activity.SpeechToTextActivity;

public class IncomingCallInterceptor extends BroadcastReceiver{
    private TextToSpeech tts;
    String number;
    Context coni;

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        String msg = "Phone state changed to " + state;
        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            number = incomingNumber;
            msg += ". Incoming number is " + incomingNumber;
            Log.d("Incoming: ", incomingNumber);
//            TTS tts = new TTS();
//            tts.start();
            // TODO This would be a good place to "Do something when the phone rings" ;-)
            coni = context;
            Intent i = new Intent(context, SpeechToTextActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            saveData("kontak","Code001 "+incomingNumber);
            context.startActivity(i);
        }
    }

    public void saveData(String name, String value){
        SharedPreferences prefs = coni.getSharedPreferences("UserData", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(name, value);
        Log.d(name + " masuk :", value);
        editor.commit();
    }
    public String loadData(String name){
        SharedPreferences prefs = coni.getSharedPreferences("UserData", 0);
        String data = prefs.getString(name,"");
        Log.d(name + " keluar:", data);
        return data;
    }
    public void deleteData(){
        SharedPreferences prefs = coni.getSharedPreferences("UserData", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", "");
        Log.d("Hapus Data:", "");
        editor.commit();
    }
}
