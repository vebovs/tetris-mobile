package com.vebovs.tetris;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class GameActivity extends Activity {
    private GameView gameView;
    private String resume;
    private String pause;
    private String exit;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.OnSharedPreferenceChangeListener spChanged = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            String time = sharedPreferences.getString(s, "-1");
            if(!time.equals("-1")) setThreadTime(Integer.parseInt(time));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.gameView = findViewById(R.id.gameView);
        this.resume = getResources().getString(R.string.resume);
        this.pause = getResources().getString(R.string.pause);
        this.exit = getResources().getString(R.string.exit);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String time = sharedPreferences.getString("difficulty", "-1");
        if(!time.equals("-1")) setThreadTime(Integer.parseInt(time));
        this.sharedPreferences.registerOnSharedPreferenceChangeListener(this.spChanged);
    }

    private void setThreadTime(int time){
        this.gameView.getGameThread().setTime(time);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(this.resume);
        menu.add(this.pause);
        menu.add(this.exit);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getTitle().equals(this.exit)){
            this.gameView.getGameThread().setRunning(false);
            this.finishAndRemoveTask();
        }
        else if (item.getTitle().equals(this.pause)) {
            this.gameView.getGameThread().setPause(true);
        }
        else if (item.getTitle().equals(this.resume)) {
            this.gameView.getGameThread().setPause(false);
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.gameView.getGameThread().setPause(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            this.finishAndRemoveTask();
            startActivity(new Intent(this, MainActivity.class));
        }
        return super.onKeyDown(keyCode, event);
    }

    public void Left(View view){
        this.gameView.Left();
    }

    public void Right(View view){
        this.gameView.Right();
    }

    public void Rotate(View view){ this.gameView.Rotate(); }
}
