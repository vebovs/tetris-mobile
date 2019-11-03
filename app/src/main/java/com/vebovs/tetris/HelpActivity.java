package com.vebovs.tetris;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class HelpActivity extends Activity {
    private TextView tvTutorial;
    private boolean tutorial;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        this.tvTutorial = findViewById(R.id.tvTutorial);
        this.tvTutorial.setMovementMethod(new ScrollingMovementMethod());
        this.tutorial = false;
    }

    public void viewTutorial(View view){
        if(!tutorial) {
            ViewGroup.LayoutParams params = this.tvTutorial.getLayoutParams();
            params.height = 400;
            this.tvTutorial.setLayoutParams(params);
            this.tutorial = true;
        } else {
            ViewGroup.LayoutParams params = this.tvTutorial.getLayoutParams();
            params.height = 0;
            this.tvTutorial.setLayoutParams(params);
            this.tutorial = false;
        }
    }
}
