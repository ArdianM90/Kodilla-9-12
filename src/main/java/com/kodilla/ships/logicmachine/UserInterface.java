package com.kodilla.ships.logicmachine;

import com.kodilla.ships.SingleSquare.PlayerSquare;
import com.kodilla.ships.SingleSquare.AiSquare;

public class UserInterface {
    private final MarkedShipsCounter markedShipsCounter;

    public UserInterface(MarkedShipsCounter markedShipsCounter) {
        this.markedShipsCounter = markedShipsCounter;
    }

    public void markMyShip(PlayerSquare playerSquare) {
        playerSquare.setShip();
        playerSquare.setMyNeighboursMap(markedShipsCounter.collectNeighboursToMap(playerSquare));
    }

    public boolean fireAt(AiSquare aiSquare) {
        aiSquare.getSquare().setOnMouseClicked(event -> {});
        if (aiSquare.isShip()) {
            aiSquare.cross();
            return true;
        } else {
            aiSquare.setColorBlack();
            return false;
        }
    }
}
