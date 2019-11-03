package com.vebovs.tetris;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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
            String str = sharedPreferences.getString(s, "-1");
            Log.i("changed: ", str);
            if(str.equals("Easy") || str.equals("Enkelt")){
                setThreadTime(650);
            } else if(str.equals("Normal") || str.equals("Normalt")){
                setThreadTime(450);
            } else {
                setThreadTime(250);
            }
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
        String str = this.sharedPreferences.getString("difficulty", "-1");
        Log.i("Got: ", str);
        if(str.equals("Easy") || str.equals("Enkelt")){
            setThreadTime(650);
        } else if(str.equals("Normal") || str.equals("Normalt")){
            setThreadTime(450);
        } else {
            setThreadTime(250);
        }
        this.sharedPreferences.registerOnSharedPreferenceChangeListener(this.spChanged);
    }

    public void setThreadTime(int time){
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

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getTitle().equals(this.exit)){
            this.gameView.getGameThread().setRunning(false);
            finish();
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

    public void Left(View view){
        this.gameView.Left();
    }

    public void Right(View view){
        this.gameView.Right();
    }

    public void Rotate(View view){ this.gameView.Rotate(); }
}
