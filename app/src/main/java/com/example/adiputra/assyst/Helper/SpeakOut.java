package com.example.adiputra.assyst.Helper;

import android.app.Activity;
import android.content.Context;

import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

/**
 * Created by Reaper on 11/19/2017.
 */

public class SpeakOut {
    TextToSpeech tts;
    Context context;
    Activity activity;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    public SpeakOut(Context context) {
        this.context = context;
        fillTTS();
    }

    public SpeakOut(Activity activity) {
        this.activity = activity;
        fillTTS();
    }

    public void fillTTS() {
        if (context!=null) {
            tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {

                }
            });
        }else{
            tts = new TextToSpeech(activity, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {

                }
            });
        }
    }

    public void speakOut(String text) {
        if (text != null) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
