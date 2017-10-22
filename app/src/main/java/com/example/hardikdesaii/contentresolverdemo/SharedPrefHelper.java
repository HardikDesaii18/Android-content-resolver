package com.example.hardikdesaii.contentresolverdemo;

import android.content.Context;
import android.content.SharedPreferences;



public class SharedPrefHelper
{
    // Access Token
    // Sharedpref file name
    public static final String PREF_NAME = "BackItUp";

    private static SharedPrefHelper instance;
    // Shared Preferences
    private SharedPreferences sharedPreferences;
    // Editor for Shared preferences
    private SharedPreferences.Editor editor;

    /**
     * SharedPrefHelper constructor set in Myapplication class
     *
     * @param context
     */
    public SharedPrefHelper(Context context) {
        instance = this;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPrefHelper getPrefsHelper() {
        return instance;
    }

    public void deleteAll() {
        editor.clear().apply();

    }

    public void delete(String key) {
        if (sharedPreferences.contains(key)) {
            editor.remove(key).commit();
        }
    }

    public void setData(String key, Object value) {

        editor = sharedPreferences.edit();
        delete(key);
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Enum) {
            editor.putString(key, value.toString());
        } else if (value != null) {
            throw new RuntimeException("Attempting to save non-primitive preference");
        }

        editor.commit();
    }

    @SuppressWarnings("unchecked")
    public <T> T getPref(String key) {
        return (T) sharedPreferences.getAll().get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getPref(String key, T defValue) {
        T returnValue = (T) sharedPreferences.getAll().get(key);
        return returnValue == null ? defValue : returnValue;
    }

    public boolean isPrefExists(String key) {
        return sharedPreferences.contains(key);
    }
}
