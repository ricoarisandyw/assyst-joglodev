package com.example.adiputra.assyst;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.adiputra.assyst.Activity.IntroActivity;
import com.example.adiputra.assyst.Activity.SpeechToTextActivity;
import com.example.adiputra.assyst.Activity.TextToSpeechActivity;

/**
 * Created by rickReaper on 6/15/2017.
 */

public class IncomingSms extends BroadcastReceiver {

    final SmsManager sms = SmsManager.getDefault();

    String body = null;
    String no = "15555215558";


    @Override
    public void onReceive(Context context, Intent intent) {
        // Parse the SMS.
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "";
        if (bundle != null) {
            // Retrieve the SMS.
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

                if (msgs[i].getOriginatingAddress().equals(no)) {
                    body = msgs[i].getMessageBody();

                    str += "SMS from " + msgs[i].getOriginatingAddress();
                    str += " :";
                    str += msgs[i].getMessageBody().toString();
                    str += "\n";
                    Log.d("Pesan Masuk",str);
                    Intent open = new Intent(context, SpeechToTextActivity.class);
                    open.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    open.putExtra("body", body);
                    context.startActivity(open);
                }
            }
            // Display the SMS as Toast.
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }
    }
}