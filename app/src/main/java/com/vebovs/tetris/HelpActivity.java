package com.vebovs.tetris;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HelpActivity extends Activity {
    private TextView tvTutorial;
    private TextView tvLanguage;
    private TextView tvDifficulty;
    private boolean tutorial;
    private boolean language;
    private boolean difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        this.tvTutorial = findViewById(R.id.tvTutorial);
        this.tvLanguage = findViewById(R.id.tvLanguage);
        this.tvDifficulty = findViewById(R.id.tvDifficulty);
        this.tvTutorial.setMovementMethod(new ScrollingMovementMethod());
        this.tvLanguage.setMovementMethod(new ScrollingMovementMethod());
        this.tvDifficulty.setMovementMethod(new ScrollingMovementMethod());
        this.tutorial = false;
        this.language = false;
        this.difficulty = false;
    }

    public void viewTutorial(View view){
        if(!this.tutorial) {
            ViewGroup.LayoutParams params = this.tvTutorial.getLayoutParams();
            params.height = 250;
            this.tvTutorial.setLayoutParams(params);
            this.tutorial = true;
        } else {
            ViewGroup.LayoutParams params = this.tvTutorial.getLayoutParams();
            params.height = 0;
            this.tvTutorial.setLayoutParams(params);
            this.tutorial = false;
        }
    }

    public void viewLanguage(View view){
        if(!this.language) {
            ViewGroup.LayoutParams params = this.tvLanguage.getLayoutParams();
            params.height = 250;
            this.tvLanguage.setLayoutParams(params);
            this.language = true;
        } else {
            ViewGroup.LayoutParams params = this.tvLanguage.getLayoutParams();
            params.height = 0;
            this.tvLanguage.setLayoutParams(params);
            this.language = false;
        }
    }

    public void viewDifficulty(View view){
        if(!this.difficulty) {
            ViewGroup.LayoutParams params = this.tvDifficulty.getLayoutParams();
            params.height = 250;
            this.tvDifficulty.setLayoutParams(params);
            this.difficulty = true;
        } else {
            ViewGroup.LayoutParams params = this.tvDifficulty.getLayoutParams();
            params.height = 0;
            this.tvDifficulty.setLayoutParams(params);
            this.difficulty = false;
        }
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
