package com.floki.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.floki.entity.ListPreferences;
import com.google.gson.Gson;

public class SettingPreferences {

    private static final String PERSON_PREFERENCE_KEY = "PERSON_PREFERENCE_KEY";
    private final Context context;

    public SettingPreferences(Context context) {
        this.context = context;
    }

    public void save(ListPreferences listPreferences) {
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        String json = new Gson().toJson(listPreferences);
        edit.putString(PERSON_PREFERENCE_KEY,json);
        edit.commit();
    }

    public void delete() {
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        edit.putString(PERSON_PREFERENCE_KEY,null);
        edit.commit();
    }

    public ListPreferences getListPreferences() {
        String json = getSharedPreferences().getString(PERSON_PREFERENCE_KEY,null);
        if(json == null){
            return new ListPreferences();
        }
        ListPreferences listPreferences = new Gson().fromJson(json, ListPreferences.class);
        return listPreferences;
    }

    private SharedPreferences getSharedPreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences;
    }

}