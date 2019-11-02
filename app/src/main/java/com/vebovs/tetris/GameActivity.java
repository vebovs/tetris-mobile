package com.vebovs.tetris;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class GameActivity extends Activity {
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.gameView = findViewById(R.id.gameView);
    }

    public void Left(View view){
        this.gameView.Left();
    }

    public void Right(View view){
        this.gameView.Right();
    }

    public void Rotate(View view){ this.gameView.Rotate(); }
}
