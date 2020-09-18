package com.kodilla.ships.logicmachine;

import com.kodilla.ships.SingleSquare;
import javafx.scene.paint.Color;

public class UserInterface {
    public void markMyShip(SingleSquare playerSquare) {
        if (playerSquare.getColor().equals(Color.DARKBLUE)) {
            playerSquare.setColorRed();
        } else {
            playerSquare.setColorBlue();
        }
    }

    public boolean fireAt(SingleSquare aiSquare) {
        aiSquare.getSquare().setOnMouseClicked(event -> {});
        if (aiSquare.getColor().equals(Color.RED)) {
            aiSquare.cross();
            return true;
        } else {
            aiSquare.setColorBlack();
            return false;
        }
    }
}
