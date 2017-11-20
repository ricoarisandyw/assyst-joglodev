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
import com.example.adiputra.assyst.Helper.SharedPref;

public class IncomingCallInterceptor extends BroadcastReceiver{
    private TextToSpeech tts;
    String number;
    Context coni;
    SharedPref sharedPref;

    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPref = new SharedPref(coni);
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
            sharedPref.saveData("kontak","Code001 "+incomingNumber);
            context.startActivity(i);
        }
    }
}
