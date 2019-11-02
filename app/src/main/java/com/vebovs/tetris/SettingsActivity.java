package com.vebovs.tetris;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Locale;

public class SettingsActivity extends Activity {
    SharedPreferences sharedPreferences;
    SharedPreferences.OnSharedPreferenceChangeListener spChanged = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            String str = sharedPreferences.getString("language", "-1");
            Log.i("Str: ", str);
            Log.i("State: ", "Changed");
            if(str.equals("English") || str.equals("Engelsk")){
                Locale locale = new Locale("en");
                Locale.setDefault(locale);
                Configuration configuration = new Configuration();
                configuration.setLocale(locale);
                getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
                setContentView(R.layout.activity_settings);
                getFragmentManager().beginTransaction().add(R.id.settings, new Preference()).commit();
            } else if(str.equals("Norwegian") || str.equals("Norsk")){
                Locale locale = new Locale("no");
                Locale.setDefault(locale);
                Configuration configuration = new Configuration();
                configuration.setLocale(locale);
                getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
                setContentView(R.layout.activity_settings);
                getFragmentManager().beginTransaction().add(R.id.settings, new Preference()).commit();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getFragmentManager().beginTransaction().add(R.id.settings, new Preference()).commit();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.sharedPreferences.registerOnSharedPreferenceChangeListener(this.spChanged);
    }
}
