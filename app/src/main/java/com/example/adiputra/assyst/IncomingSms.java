package com.example.adiputra.assyst;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.adiputra.assyst.Activity.SpeechToTextActivity;

/**
 * Created by rickReaper on 6/15/2017.
 */

public class IncomingSms extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SMSBroadcastReceiver";
    Context coni;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Intent Recieved: " + intent.getAction());
        coni = context;
        if (intent.getAction().equals(SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                if (messages.length > -1) {
                    Log.d("Pesan", messages[0].getMessageBody());
                    Intent i = new Intent(context, SpeechToTextActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    saveData("kontak", "Code002 "+messages[0].getMessageBody());
                    context.startActivity(i);
                }
            }
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