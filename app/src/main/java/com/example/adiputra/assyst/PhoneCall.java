package com.example.adiputra.assyst;

import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by rickReaper on 6/15/2017.
 */

public class PhoneCall {
    public void onReceive(Context context, Intent intent)
    {
        String state=intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if(state==null)
        {
            //Outgoing call
            String number=intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.i("tag","Outgoing number : "+number);
        }
        else if (state.equals(TelephonyManager.EXTRA_STATE_RINGING))
        {
            //Incoming call
            String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Log.i("tag","Incoming number : "+number);
        }
    }
}
