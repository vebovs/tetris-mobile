package com.vebovs.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private Piece piece;
    private ArrayList<Piece> pieces = new ArrayList<>();
    private int deviceWidth;
    private int deviceHeight;
    private int base;
    private int bottom;
    private int size = new Piece().getSize();
    private Random random = new Random();

    public GameView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        this.gameThread = new GameThread(holder, this);
        this.piece = new Piece(0, 0, 0, 50, 50, 0, 50, 50); // Dummie cube for testing
    }

    public GameThread getGameThread(){
        return this.gameThread;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!gameThread.isPaused()){
            updatePiece();
        }

        canvas.drawColor(Color.WHITE);
        drawPiece(canvas);
        for(int i = 0; i < this.pieces.size(); i++){
            drawShape(canvas, this.pieces.get(i).getPositions());
        }
        canvas.drawLine(0, this.bottom, deviceWidth , this.bottom, new Paint(Color.RED));
    }

    private boolean CollisionY(){
        for(int i = 0; i < this.pieces.size(); i++){
            ArrayList<Position> positions = this.pieces.get(i).getPositions();
            for(int j = 0; j < positions.size(); j++){
                for(int k = 0; k < positions.size(); k++){
                    if(this.piece.getPositions().get(j).getY() == positions.get(k).getY() - this.size && this.piece.getPositions().get(j).getX() == positions.get(k).getX()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void updatePiece(){
        boolean hit = false;
        ArrayList<Position> positions = this.piece.getPositions();
        for(int i = 0; i < positions.size(); i++){
            if(positions.get(i).getY() < this.bottom - this.size){
                positions.get(i).setY(positions.get(i).getY() + this.size);
                if(!hit){
                    if(positions.get(i).getY() == this.bottom - this.size || CollisionY()){
                        hit = true;
                    }
                }
            }
        }
        if(hit){
            this.pieces.add(this.piece);
            this.piece = newPiece();
        }
    }

    private void drawPiece(Canvas canvas){
        ArrayList<Position> positions = this.piece.getPositions();
        drawShape(canvas, positions);
    }

    private void drawShape(Canvas canvas, ArrayList<Position> positions){
        Paint paint = new Paint();
        for(int i = 0; i < positions.size(); i++){
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLACK);
            Rect r = new Rect(positions.get(i).getX() + this.base, positions.get(i).getY(), positions.get(i).getX() + this.size + this.base, positions.get(i).getY() + this.size);
            canvas.drawRect(r, paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.GRAY);
            canvas.drawRect(r, paint);
        }
    }

    private Piece newPiece(){
        /*int result = random.nextInt(100);
        if(result < 25){
            return this.piece.newI();
        } else if(result >= 25 && result < 50){
            return this.piece.newL();
        } else if(result >= 50 && result < 75){
            return this.piece.newS();
        } else {
            return this.piece.newC();
        }*/
        return this.piece.newC(); // Returns same piece type and avoids logic for testing
    }

    public void Left(){
        boolean collide = false;
        for(int i = 0; i < this.pieces.size(); i++){
            ArrayList<Position> positions = this.pieces.get(i).getPositions();
            for(int j = 0; j < positions.size(); j++){
                for(int k = 0; k < positions.size(); k++){
                    if(this.piece.getPositions().get(j).getY() == positions.get(k).getY() && (this.piece.getPositions().get(j).getX() + this.size == positions.get(k).getX() || this.piece.getPositions().get(j).getX() - this.size == positions.get(k).getX())){
                        collide = true;
                    }
                }
            }
        }
        if(!collide) {
            ArrayList<Position> positions = this.piece.getPositions();
            for (int i = 0; i < positions.size(); i++) {
                positions.get(i).setX(positions.get(i).getX() - this.size);
            }
        }
    }

    public void Right(){
        boolean collide = false;
        for(int i = 0; i < this.pieces.size(); i++){
            ArrayList<Position> positions = this.pieces.get(i).getPositions();
            for(int j = 0; j < positions.size(); j++){
                for(int k = 0; k < positions.size(); k++){
                    if(this.piece.getPositions().get(j).getY() == positions.get(k).getY() && (this.piece.getPositions().get(j).getX() + this.size == positions.get(k).getX() || this.piece.getPositions().get(j).getX() - this.size == positions.get(k).getX())){
                        collide = true;
                    }
                }
            }
        }
        if(!collide) {
            ArrayList<Position> positions = this.piece.getPositions();
            for (int i = 0; i < positions.size(); i++) {
                positions.get(i).setX(positions.get(i).getX() + this.size);
            }
        }
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
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int width, int height) {
        this.deviceWidth = width;
        this.deviceHeight = height;
        this.base = (this.deviceWidth - this.size)/2;
        this.bottom = this.deviceHeight - 18; // TODO: Specify bottom border. Currently hardcoded to the Nexus 5X API 29 emulated model
    }
}
