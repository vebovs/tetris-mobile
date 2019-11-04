package com.vebovs.tetris;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;

public class SettingsActivity extends Activity {
    SharedPreferences sharedPreferences;
    SharedPreferences.OnSharedPreferenceChangeListener spChanged = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            Changed();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getFragmentManager().beginTransaction().add(R.id.settings, new Preference()).commit();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.sharedPreferences.registerOnSharedPreferenceChangeListener(this.spChanged);
    }

    public void Changed(){
        this.finishAndRemoveTask();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            this.finishAndRemoveTask();
            startActivity(new Intent(this, MainActivity.class));
        }
        return super.onKeyDown(keyCode, event);
    }
}
