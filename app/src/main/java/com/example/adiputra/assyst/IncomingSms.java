package com.example.adiputra.assyst;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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
                    sharedPref.saveData("kontak", messages[0].getMessageBody());//Message Body
                    sharedPref.saveData("sender", messages[0].getOriginatingAddress());//Message Body

                    Log.d("Pesan Originating", messages[0].getDisplayOriginatingAddress());
                    Log.d("Pesan Message", messages[0].getDisplayMessageBody());
                    Log.d("Pesan EmailFrom", messages[0].getEmailFrom());
                    Log.d("Pesan EmailBody", messages[0].getEmailBody());
                    Log.d("Pesan Pseudo", messages[0].getPseudoSubject());
                    Log.d("Pesan Service", messages[0].getServiceCenterAddress());
                    context.startActivity(i);
                }
            }
        }
    }

    public String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri,
                new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return contactName;
    }

}