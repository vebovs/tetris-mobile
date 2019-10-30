package com.vebovs.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;

    public GameView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        this.gameThread = new GameThread(holder, this);
    }

    public GameThread getGameThread(){
        return this.gameThread;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!gameThread.isPaused()){
            //TODO
        }

        canvas.drawColor(Color.BLACK);


    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        this.gameThread.setRunning(false);
        while(retry){
            try {
                this.gameThread.join();
                retry = false;
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }
}
