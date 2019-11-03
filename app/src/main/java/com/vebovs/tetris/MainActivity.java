package com.vebovs.tetris;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import java.util.Locale;

public class MainActivity extends Activity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.OnSharedPreferenceChangeListener spChanged = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                String countryCode = sharedPreferences.getString(s, "-1");
                setLanguage(countryCode);
            }
        };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String countryCode = this.sharedPreferences.getString("language", "-1");
        setLanguage(countryCode);
        this.sharedPreferences.registerOnSharedPreferenceChangeListener(this.spChanged);
    }

    private void setLanguage(String countryCode){
        if(!countryCode.equals("-1")){
            Locale locale = new Locale(countryCode);
            Locale.setDefault(locale);
            Configuration configuration = new Configuration();
            configuration.setLocale(locale);
            getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
            setContentView(R.layout.activity_main);
        }
    }

    public void startGame(View view){
        startActivity(new Intent(this, GameActivity.class));
    }

    public void startSettings(View view){
        startActivity(new Intent(this, SettingsActivity.class));
    }

    public void startHelp(View view){
        startActivity(new Intent(this, HelpActivity.class));
    }

    public void Quit(View view){
        this.finishAndRemoveTask();
    }
}
