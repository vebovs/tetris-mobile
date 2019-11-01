package com.vebovs.tetris;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Piece {
    private ArrayList<Position> positions = new ArrayList<>();
    private int size = 50; // Width and height of each square making up every piece

    Piece(){}

    Piece(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4){
        positions.add(new Position(x1, y1));
        positions.add(new Position(x2, y2));
        positions.add(new Position(x3, y3));
        positions.add(new Position(x4, y4));
    }

    public ArrayList<Position> getPositions() { return  this.positions; }

    public int getSize() { return this.size; }

    public Piece newI(){
        return new Piece(0, 0, 0, 50, 0, 100, 0, 150);
    }

    public Piece newL(){
        return new Piece(0, 0, 0, 50, 0, 100, 50, 100);
    }

    public Piece newS(){
        return new Piece(0, 0, 50, 0, 50, 50, 100, 50);
    }

    public Piece newC(){
        return new Piece(0, 0, 0, 50, 50, 0, 50, 50);
    }
}

class Position {
    private int x;
    private int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY() { return this.y; }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;

        Position position = (Position) obj;
        return this.x == position.getX() && this.y == position.getY();
    }
}
