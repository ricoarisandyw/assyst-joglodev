package com.example.adiputra.assyst;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.adiputra.assyst.Activity.SpeechToTextActivity;
import com.example.adiputra.assyst.Helper.SharedPref;

/**
 * Created by rickReaper on 6/15/2017.
 */

public class IncomingSms extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SMSBroadcastReceiver";
    Context coni;
    SharedPref sharedPref;

    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPref = new SharedPref(context);
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
                    sharedPref.saveData("kontak", "S.M.S Receiver  "+messages[0].getMessageBody());
                    context.startActivity(i);
                }
            }
        }
    }
}