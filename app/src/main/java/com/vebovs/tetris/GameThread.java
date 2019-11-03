package com.vebovs.tetris;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
    private SurfaceHolder holder;
    private GameView gameView;
    private boolean run = false;
    private boolean pause = false;
    private int time = 150; // Time between every drawing

    public GameThread(SurfaceHolder holder, GameView gameView){
        this.holder = holder;
        this.gameView = gameView;
    }

    @Override
    public void run() {
        Canvas canvas;
        while(isRunning()){
            canvas = null;
            try {
                canvas = this.holder.lockCanvas(null);
                synchronized (this.holder){
                    this.gameView.onDraw(canvas);
                }
            } finally {
                if(canvas != null){
                    this.holder.unlockCanvasAndPost(canvas);
                }
            }
            setSleep(this.time);
        }
    }

    public void setTime(int time){
        this.time = time;
    }

    public void setSleep(int duration){
        try {
            sleep(duration);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void setRunning(boolean run){
        this.run = run;
    }

    public boolean isRunning(){
        return this.run;
    }

    public void setPause(boolean pause){
        synchronized (this.holder){
            this.pause = pause;
        }
    }

    public boolean isPaused(){
        synchronized (this.holder){
            return this.pause;
        }
    }
}
