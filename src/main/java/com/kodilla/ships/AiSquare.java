package com.kodilla.ships;

import javafx.scene.paint.Color;

public class AiSquare extends SingleSquare {
    private boolean isShip = false;

    public AiSquare(int width, int height) {
        super(width, height);
    }

    public void setShip() {
        isShip = true;
    }

    public boolean isShip() {
        return isShip;
    }
}
