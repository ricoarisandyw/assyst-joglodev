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

public class SpeakOut extends Activity{
    TextToSpeech tts;
    Context context;
    Activity activity;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    public SpeakOut(Context context,String text) {
        this.context = context;
        fillTTS(text);
    }

    public SpeakOut(Activity activity,String text) {
        this.activity = activity;
        fillTTS(text);
    }

    public void fillTTS(final String text) {
        if (context!=null) {//Activity
            tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {

                        int result = tts.setLanguage(Locale.US);

                        if (result == TextToSpeech.LANG_MISSING_DATA
                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTS", "This Language is not supported");
                        } else {
                            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                            tts.setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() {
                                @Override
                                public void onUtteranceCompleted(String utteranceId) {
                                    tts.shutdown();
                                }
                            });
                        }

                    } else {
                        Log.e("TTS", "Initilization Failed!");
                    }

                }
            });
        }else{//Context
            tts = new TextToSpeech(activity, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {

                        int result = tts.setLanguage(Locale.US);

                        if (result == TextToSpeech.LANG_MISSING_DATA
                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTS", "This Language is not supported");
                        } else {
                            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null,null);
                            tts.setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() {
                                @Override
                                public void onUtteranceCompleted(String utteranceId) {
                                    tts.shutdown();
                                }
                            });
                        }

                    } else {
                        Log.e("TTS", "Initilization Failed!");
                    }

                }
            });
        }
    }
}
