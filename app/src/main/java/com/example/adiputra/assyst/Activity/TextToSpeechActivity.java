package com.example.adiputra.assyst.Activity;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.adiputra.assyst.R;
import com.example.adiputra.assyst.TestReceiver;

import java.util.Locale;

public class TextToSpeechActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    private TextToSpeech tts;
    Button btnSpeak;
    EditText txtText;
    Intent i = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);
//        tts.setLanguage(new Locale("id","ID"));
        tts = new TextToSpeech(this, this);
        String s = i.getStringExtra("nomor");
        if(s==null){
            s="anonymous";
        }

        btnSpeak = (Button) findViewById(R.id.btnStartSpeak);
        txtText = (EditText) findViewById(R.id.etTextToSpeech);
        s = "you ,got, incoming, call, from," + s;
        txtText.setText(s);
        speakOut();

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                speakOut();
            }
        });
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
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
                btnSpeak.setEnabled(true);
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    private void speakOut() {
        String text = txtText.getText().toString();
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}
