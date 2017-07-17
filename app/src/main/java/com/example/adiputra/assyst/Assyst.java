package com.example.adiputra.assyst;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adiputra.assyst.Activity.SpeechToTextActivity;
import com.example.adiputra.assyst.R;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * Created by rickReaper on 6/16/2017.
 */

public class Assyst extends AppCompatActivity{
    String message = "";
    int Rid;
    TextView text;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    public void promptSpeechInput(int Rid) {
        this.Rid = Rid;
        text = (TextView) findViewById(Rid);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                super.getApplication().getString(R.string.speech_prompt));
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
                    if(!result.get(0).contains("System End")){
                        text.append(result.get(0)+" ");
                        promptSpeechInput(Rid);
                    }
                }
                break;
            }

        }
    }

    public String getMessage(){
        return message;
    }

    public void saveData(String name, String value){
        SharedPreferences prefs = getSharedPreferences("UserData", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(name, value);
        Log.d(name + " masuk :", value);
        editor.commit();
    }
    public String loadData(String name){
        SharedPreferences prefs = getSharedPreferences("UserData", 0);
        String data = prefs.getString(name,"");
        Log.d(name + " keluar:", data);
        return data;
    }
    public void deleteData(){
        SharedPreferences prefs = getSharedPreferences("UserData", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", "");
        Log.d("Hapus Data:", "");
        editor.commit();
    }

    ArrayList<String> contactList;
    Cursor cursor;
    int counter;
    //Contact
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
        Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;
        StringBuffer output;

        ContentResolver contentResolver = getContentResolver();
        cursor = contentResolver.query(CONTENT_URI, null,null, null, null);
        // Iterate every contact in the phone
        if (cursor.getCount() > 0) {
            counter = 0;
            while (cursor.moveToNext()) {
                output = new StringBuffer();
                // Update the progress message
                String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
                String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));
                if (hasPhoneNumber > 0) {
                    //This is to read multiple phone numbers associated with the same contact
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);
                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        contactList.add(output.toString());
                        phoneNumber = phoneNumber.replace("-","");
                        phoneNumber = phoneNumber.replace("+62","0");
                        phoneNumber = phoneNumber.replace(" ","");
                        if(number.equalsIgnoreCase(phoneNumber)){
                            return name;
                        }
                    }
                    phoneCursor.close();
                }
            }
        }
        return "No One has this contact";
    }
}
