package com.kodilla.ships.SingleSquare;

import javafx.scene.layout.StackPane;

public interface SingleSquare {

    void cross();

    boolean isShip();

    boolean isHit();

    void setShip();

    void setColorBlack();

    StackPane getSquare();
}
