package com.kodilla.ships.SingleSquare;

public class SquareKey {
    private final int X;
    private final int Y;

    public SquareKey(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    @Override
    public int hashCode(){
        return X*100 + Y;
    }

    @Override
    public boolean equals(Object o){
        if (getClass() != o.getClass())
            return false;
        SquareKey e = (SquareKey) o;
        return (getX() == e.getX() && getY() == e.getY());
    }
}
