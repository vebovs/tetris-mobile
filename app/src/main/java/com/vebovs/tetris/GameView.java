package com.vebovs.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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
        this.piece = newPiece();
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
            drawShape(canvas, this.pieces.get(i).getPositions(), this.pieces.get(i).getColor());
        }
        canvas.drawLine(0, this.bottom, deviceWidth , this.bottom, new Paint(Color.BLACK));
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
        drawShape(canvas, this.piece.getPositions(), this.piece.getColor());
    }

    private void drawShape(Canvas canvas, ArrayList<Position> positions, int color){
        Paint paint = new Paint();
        for(int i = 0; i < positions.size(); i++){
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(color);
            Rect r = new Rect(positions.get(i).getX() + this.base, positions.get(i).getY(), positions.get(i).getX() + this.size + this.base, positions.get(i).getY() + this.size);
            canvas.drawRect(r, paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            canvas.drawRect(r, paint);
        }
    }

    private Piece newPiece(){
        int result = random.nextInt(100);
        if(result < 25){
            //I shape
            return new Piece(0, 0, 0, 50, 0, 100, 0, 150, Color.RED);
        } else if(result >= 25 && result < 50){
            //L shape
            return new Piece(0, 0, 0, 50, 0, 100, 50, 100, Color.BLUE);
        } else if(result >= 50 && result < 75){
            //S shape
            return new Piece(0, 0, 50, 0, 50, 50, 100, 50, Color.GREEN);
        } else {
            //Cube shape
            return new Piece(0, 0, 0, 50, 50, 0, 50, 50, Color.YELLOW);
        }
    }

    public void Left(){
        boolean collide = false;
        for(int i = 0; i < this.pieces.size(); i++){
            ArrayList<Position> positions = this.pieces.get(i).getPositions();
            for(int j = 0; j < positions.size(); j++){
                for(int k = 0; k < positions.size(); k++){
                    if(this.piece.getPositions().get(j).getY() == positions.get(k).getY() - this.size && (this.piece.getPositions().get(j).getX() + this.size == positions.get(k).getX() || this.piece.getPositions().get(j).getX() - this.size == positions.get(k).getX())){
                        collide = true;
                    }
                }
            }
        }
        boolean border = false;
        if(!collide) {
            ArrayList<Position> positions = this.piece.getPositions();
            for (int i = 0; i < positions.size(); i++) {
                if(positions.get(i).getX() <= -this.base + this.size){
                    border = true;
                }
            }
            if(!border) {
                for (int i = 0; i < positions.size(); i++) {
                    positions.get(i).setX(positions.get(i).getX() - this.size);
                }
            }
        }
    }

    public void Right(){
        boolean collide = false;
        for(int i = 0; i < this.pieces.size(); i++){
            ArrayList<Position> positions = this.pieces.get(i).getPositions();
            for(int j = 0; j < positions.size(); j++){
                for(int k = 0; k < positions.size(); k++){
                    if(this.piece.getPositions().get(j).getY() == positions.get(k).getY() - this.size && (this.piece.getPositions().get(j).getX() + this.size == positions.get(k).getX() || this.piece.getPositions().get(j).getX() - this.size == positions.get(k).getX())){
                        collide = true;
                    }
                }
            }
        }
        boolean border = false;
        if(!collide) {
            ArrayList<Position> positions = this.piece.getPositions();
            for (int i = 0; i < positions.size(); i++) {
                if(positions.get(i).getX() >= this.base - this.size){
                    border = true;
                }
            }
            if(!border) {
                for (int i = 0; i < positions.size(); i++) {
                    positions.get(i).setX(positions.get(i).getX() + this.size);
                }
            }
        }
    }

    public void Rotate(){
        boolean collide = false;
        boolean border = false;
        int distance_y = this.piece.getPositions().get(0).getY();
        int distance_x = this.piece.getPositions().get(0).getX();
        for(int i = 0; i < this.pieces.size(); i++){
            ArrayList<Position> positions = this.pieces.get(i).getPositions();
            for(int j = 0; j < positions.size(); j++){
                int y = this.piece.getPositions().get(j).getY() - distance_y;
                int x = this.piece.getPositions().get(j).getX() - distance_x;
                int new_x = - y + distance_x;
                int new_y = x + distance_y;
                for(int k = 0; k < positions.size(); k++){
                    if((positions.get(k).getX() == new_x + this.size || positions.get(k).getX() == new_x - this.size) && (positions.get(k).getY() == new_y - this.size || positions.get(k).getY() == new_y + this.size)){
                        collide = true;
                    }
                    if(new_x >= this.base - this.size || new_x <= - this.base || new_y == this.bottom + this.size){
                        border = true;
                    }
                }
            }
        }
        if(!collide && !border) {
            ArrayList<Position> positions = this.piece.getPositions();
            for (int i = 0; i < positions.size(); i++) {
                int y = positions.get(i).getY() - distance_y;
                int x = positions.get(i).getX() - distance_x;
                int new_x = -y + distance_x;
                int new_y = x + distance_y;
                positions.get(i).setX(new_x);
                positions.get(i).setY(new_y);
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
