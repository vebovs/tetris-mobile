package com.vebovs.tetris;

import java.util.ArrayList;

public enum Piece {
    L(0, 0, 1, 0, 0, 1, 0, 2),
    I(0, 0, 0, 1, 0, 2, 0, 3),
    S(0, 0, 1, 0, 1, 1, 2, 1),
    C(0, 0, 0, 1, 1, 0, 1, 1);

    Piece(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4){
        positions.add(new Position(x1, y1));
        positions.add(new Position(x2, y2));
        positions.add(new Position(x3, y3));
        positions.add(new Position(x4, y4));
    }

    ArrayList<Position> positions = new ArrayList<>();
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

    public int getY(){
        return this.y;
    }
}
