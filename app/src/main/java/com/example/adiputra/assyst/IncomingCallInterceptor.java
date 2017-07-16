package com.example.adiputra.assyst;

/**
 * Created by rickReaper on 6/15/2017.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.adiputra.assyst.Activity.SpeechToTextActivity;
import com.example.adiputra.assyst.Activity.TextToSpeechActivity;
import com.example.adiputra.assyst.Activity.whonumberActivity;

import java.util.Locale;

public class IncomingCallInterceptor extends BroadcastReceiver{
    private TextToSpeech tts;
    String number;

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
            Intent i = new Intent(context, SpeechToTextActivity.class);
            intent.putExtra("Nomor", incomingNumber);
            context.startActivity(i);
        }
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
    public class TTS extends Thread implements TextToSpeech.OnInitListener{
        String text;
        @Override
        public void run() {
            if(!number.equals(null)){
                tts.speak(number, TextToSpeech.QUEUE_FLUSH
                        , null);
            } else{
                tts.speak("Data is null", TextToSpeech.QUEUE_FLUSH
                        , null);
            }
        }

        private void speakOut(String text) {
            this.text = text;
        }

        @Override
        public void onInit(int status) {
            Log.e("TTS", "Start TTS");
            if (status == TextToSpeech.SUCCESS) {

                int result = tts.setLanguage(Locale.US);

                if (result == TextToSpeech.LANG_MISSING_DATA
                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "This Language is not supported");
                } else {
                    speakOut(number);
                }

            } else {
                Log.e("TTS", "Initilization Failed!");
            }

        }
    }
}
