package com.rjesture.startupkit.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

//===
public class MemoryBox {

    private Context _context;
    private SharedPreferences _preferences;
    private Editor _editor;
    private String prefName = "cel";

    //=====
    public MemoryBox(Context context) {

        _context = context;
        _preferences = this._context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        _editor = this._preferences.edit();
    }

    //=====
    public MemoryBox commit() {
        _editor.commit();
        return this;
    }

    //=====
    public MemoryBox set(String key, String value) {

        _editor.putString(key, value);
        return this;
    }

    //=====
    public String get(String key) {
        return _preferences.getString(key, "");
    }

    //=====
    public MemoryBox set(String key, int value) {

        _editor.putInt(key, value);
        return this;
    }

    public MemoryBox set(String key, long value) {

        _editor.putLong(key, value);
        return this;
    }

    //=====
    public int getInt(String key) {
        return _preferences.getInt(key, 0);
    }

    public long getlong(String key) {
        return _preferences.getLong(key, -1);
    }

    //=====
    public MemoryBox setBoolean(String key, boolean value) {

        _editor.putBoolean(key, value);
        return this;
    }


    //=====
    public void removeKey(String key) {
        _editor.remove(key);
    }

    //=====
    public boolean getBoolean(String key) {
        return _preferences.getBoolean(key, false);
    }

}