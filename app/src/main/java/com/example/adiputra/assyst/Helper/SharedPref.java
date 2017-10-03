package com.example.adiputra.assyst.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Reaper on 8/13/2017.
 */

public class SharedPref {

    public Activity activity;
    public Context context;

    //SharedPref sharedPref = new SharedPref(this);
	//sharedPref.saveData(id, value);
	//sharedPref.saveData(token, value);
	//sharedPref.loadData(token);

    public SharedPref() {
    }

    // @important!!!
    // YOU SHOULD INIT THIS FIRST !!! IMPORTANT
    public SharedPref(Context context) {
        this.context = context;
    }

    public SharedPref(Activity activity) {
        this.activity = activity;
    }

    public boolean saveData(String name, String value) {
        SharedPreferences prefs;
        if (context == null) {
            prefs = activity.getSharedPreferences("SharedPref", 0);
        } else {
            prefs = context.getSharedPreferences("SharedPref", 0);
        }

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(name, value);
        Log.d("Reponse", name + " masuk :" + value);
        if (editor.commit()) {
            return true;
        } else {
            return false;
        }
    }

    public String loadData(String name) {
        SharedPreferences prefs;
        if (context == null) {
            prefs = activity.getSharedPreferences("SharedPref", 0);
        } else {
            prefs = context.getSharedPreferences("SharedPref", 0);
        }
        String data = prefs.getString(name, "");
        Log.d("Reponse", name + " keluar:" + data);
        return data;
    }
}
