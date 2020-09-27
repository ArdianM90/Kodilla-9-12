package com.kodilla.ships.logicmachine;

public class ShipsCounterPanelDto {
    private final int redSquaresQuantity;
    private final int[] playerShipsQuantity; //może by to zmienić na ENUM?

    public ShipsCounterPanelDto(int redSquaresQuantity, int[] playerShipsQuantity) {
        this.redSquaresQuantity = redSquaresQuantity;
        this.playerShipsQuantity = playerShipsQuantity;
    }

    public int getRedSquaresQuantity() {
        return redSquaresQuantity;
    }

    public int[] getPlayerShipsQuantity() {
        return playerShipsQuantity;
    }
}
