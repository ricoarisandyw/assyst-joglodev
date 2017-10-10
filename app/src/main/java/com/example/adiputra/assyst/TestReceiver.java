package com.example.adiputra.assyst;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.adiputra.assyst.Activity.MainActivity;

/**
 * Created by rickReaper on 6/15/2017.
 */

public class TestReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent.getAction().equals(MyCalReceiver.CUSTOM_INTENT)) {
            System.out.println("GOT THE INTENT");
            final String mobileNumber = intent.getExtras().getString("number");
            Thread thread = new Thread(){
                private int sleepTime = 400;

                @Override
                public void run() {
                    super.run();
                    try {
                        int wait_Time = 0;

                        while (wait_Time < sleepTime ) {
                            sleep(100);
                            wait_Time += 100;
                        }
                    }catch (Exception e) {
                        Toast.makeText(context,
                                "Error Occured Because:" + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                    finally {

                    }

                    context.startActivity(new Intent(context, MainActivity.class).putExtra("number", mobileNumber)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            };
            thread.run();
        }
    }
    public class MyCalReceiver {
        public static final String CUSTOM_INTENT = "jason.wei.custom.intent.action.TEST";
        Context context = null;
        private static final String TAG = "Phone call";



        public void onReceive(Context context, Intent intent) {

            Bundle extras = intent.getExtras();
            if (extras != null) {
                String state = extras.getString(TelephonyManager.EXTRA_STATE);
                Log.w("DEBUG", state);
                if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {

                    Intent i = new Intent();
                    i.setAction(CUSTOM_INTENT);
                    context.sendBroadcast(i);

                }
            }
        }
    }
}
