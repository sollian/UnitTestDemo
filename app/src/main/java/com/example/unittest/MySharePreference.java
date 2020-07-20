package com.example.unittest;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharePreference {
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    public MySharePreference(SharedPreferences sharedPref) {
        this.sharedPref = sharedPref;
        this.editor = sharedPref.edit();
    }

    public MySharePreference() {
        this(MyApplication.getInstance().getSharedPreferences("myShare", Context.MODE_PRIVATE));
    }

    public void put(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String get(String key) {
        return sharedPref.getString(key, "");
    }
}
