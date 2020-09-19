package com.kodilla.ships.logicmachine;

import com.kodilla.ships.SingleSquare;
import com.kodilla.ships.AiSquare;
import javafx.scene.paint.Color;

public class UserInterface {
    public void markMyShip(SingleSquare playerSquare) {
        if (playerSquare.getColor().equals(Color.DARKBLUE)) {
            playerSquare.setColorRed();
        } else {
            playerSquare.setColorBlue();
        }
    }

    public boolean fireAt(AiSquare aiSquare) {
        aiSquare.getSquare().setOnMouseClicked(event -> {});
        if (aiSquare.isShip()) {
            aiSquare.cross();
            aiSquare.setColorRed();
            return true;
        } else {
            aiSquare.setColorBlack();
            return false;
        }
    }
}
