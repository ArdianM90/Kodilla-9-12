package com.kodilla.ships.logicmachine;

import com.kodilla.ships.SingleSquare;
import javafx.scene.paint.Color;

import java.util.List;

public class MarkedShipsCounter {
    private final List<List<SingleSquare>> playerSquares2DList;
    private final boolean[][] uncheckedSquares;
    private final int[] playerShipsQuantity = new int[4]; //może by to zmienić na ENUM?

    public MarkedShipsCounter(List<List<SingleSquare>> playerSquares2DList) {
        this.playerSquares2DList = playerSquares2DList;
        this.uncheckedSquares = new boolean[playerSquares2DList.size()][playerSquares2DList.get(0).size()];
    }

    public int[] countMarkedShips() {
        int redSquaresQuantity = 0;
        for (int i = 0; i < playerSquares2DList.size(); i++) {
            for (int j = 0; j < playerSquares2DList.get(i).size(); j++) {
                uncheckedSquares[i][j] = true;
            }
        }
        for (int i = 0; i < 4; i++) {
            playerShipsQuantity[i] = 0;
        }
        boolean isAShip;
        int shipSquares = 0;
        for (int colIndex = 0; colIndex < playerSquares2DList.size(); colIndex++) {
            for (int rowIndex = 0; rowIndex < playerSquares2DList.get(colIndex).size(); rowIndex++) {
                if (uncheckedSquares[colIndex][rowIndex] && redSquareCheck(colIndex, rowIndex)) {
                    redSquaresQuantity += 1;
                    if (squareHasNoForbiddenNeighbours(colIndex, rowIndex)) {
                        isAShip = true;
                        shipSquares += 1;
                        if (squareHasRedBelow(colIndex, rowIndex)) {
                            int i = 1;
                            boolean letsCheckNextSquare = true;
                            while (i <= 4 && letsCheckNextSquare) {
                                if (redSquareCheck(colIndex, rowIndex+i)) {
                                    redSquaresQuantity += 1;
                                    if (i < 4) {
                                        shipSquares += 1;
                                        if (!squareHasNoForbiddenNeighbours(colIndex, rowIndex+i)) {
                                            letsCheckNextSquare = false;
                                            isAShip = false;
                                        }
                                    } else {
                                        isAShip = false;
                                    }
                                } else {
                                    letsCheckNextSquare = false;
                                }
                                i++;
                            }
                        } else if (squareHasRedOnRight(colIndex, rowIndex)) {
                            int i = 1;
                            boolean letsCheckNextSquare = true;
                            while (i <= 4 && letsCheckNextSquare) {
                                if (redSquareCheck(colIndex+i, rowIndex)) {
                                    redSquaresQuantity += 1;
                                    if (i < 4) {
                                        shipSquares += 1;
                                        if (!squareHasNoForbiddenNeighbours(colIndex+i, rowIndex)) {
                                            letsCheckNextSquare = false;
                                            isAShip = false;
                                        }
                                    } else {
                                        isAShip = false;
                                    }
                                } else {
                                    letsCheckNextSquare = false;
                                }
                                i++;
                            }
                        }
                        if (isAShip && shipSquares <= 4) {
                            playerShipsQuantity[shipSquares-1] += 1;
                            shipSquares = 0;
                        }
                    }
                }
            }
        }
        System.out.println("Czerwonych kwadratow jest: "+redSquaresQuantity);
        System.out.println("Ilosc statkow: "+playerShipsQuantity[0]+", "+playerShipsQuantity[1]+", "+playerShipsQuantity[2]+", "+playerShipsQuantity[3]+", ");
        System.out.println("====");
        System.out.println();
        return playerShipsQuantity;
    }

    private boolean redSquareCheck(int colIndex, int rowIndex) {
        if (colIndex >= 0 && colIndex < playerSquares2DList.size() && rowIndex >= 0 && rowIndex < playerSquares2DList.get(0).size()) {
            uncheckedSquares[colIndex][rowIndex] = false;
            return playerSquares2DList.get(colIndex).get(rowIndex).getColor().equals(Color.RED);
        }
        return false;
    }

    private boolean squareHasNoForbiddenNeighbours(int colIndex, int rowIndex) {
        if (squareHasRedsInRow(colIndex, rowIndex) && squareHasRedsInColumn(colIndex, rowIndex)) {
            return false;
        }
        return squareHasNoRedsDiagonally(colIndex, rowIndex);
    }

    private boolean squareHasNoRedsDiagonally(int colIndex, int rowIndex) {
        boolean hasRedInLeftTopCorner = false;
        boolean hasRedInRightTopCorner = false;
        boolean hasRedInLeftBottomCorner = false;
        boolean hasRedInRightBottomCorner = false;
        if ((colIndex-1 >= 0) && (rowIndex+1 < playerSquares2DList.get(colIndex).size())) {
            hasRedInLeftTopCorner = playerSquares2DList.get(colIndex-1).get(rowIndex+1).getColor().equals(Color.RED);
        }
        if ((colIndex+1 < playerSquares2DList.size()) && (rowIndex+1 < playerSquares2DList.get(colIndex).size())) {
            hasRedInRightTopCorner = playerSquares2DList.get(colIndex+1).get(rowIndex+1).getColor().equals(Color.RED);
        }
        if ((colIndex-1 >= 0) && (rowIndex-1 >= 0)) {
            hasRedInLeftBottomCorner = playerSquares2DList.get(colIndex-1).get(rowIndex-1).getColor().equals(Color.RED);
        }
        if ((colIndex+1 < playerSquares2DList.size()) && (rowIndex-1 >= 0)) {
            hasRedInRightBottomCorner = playerSquares2DList.get(colIndex+1).get(rowIndex-1).getColor().equals(Color.RED);
        }
        return !(hasRedInLeftTopCorner || hasRedInRightTopCorner || hasRedInLeftBottomCorner || hasRedInRightBottomCorner);
    }

    private boolean squareHasRedsInRow(int colIndex, int rowIndex) {
        boolean hasRedOnLeft = false;
        boolean hasRedOnRight = false;
        if (colIndex-1 > 0) {
            hasRedOnLeft = playerSquares2DList.get(colIndex-1).get(rowIndex).getColor().equals(Color.RED);
        }
        if (colIndex+1 < playerSquares2DList.size()) {
            hasRedOnRight = playerSquares2DList.get(colIndex+1).get(rowIndex).getColor().equals(Color.RED);
        }
        return hasRedOnLeft || hasRedOnRight;
    }

    private boolean squareHasRedsInColumn(int colIndex, int rowIndex) {
        boolean hasRedAbove = false;
        boolean hasRedBelow = false;
        if (rowIndex+1 < playerSquares2DList.get(colIndex).size()) {
            hasRedAbove = playerSquares2DList.get(colIndex).get(rowIndex+1).getColor().equals(Color.RED);
        }
        if (rowIndex-1 >= 0) {
            hasRedBelow = playerSquares2DList.get(colIndex).get(rowIndex-1).getColor().equals(Color.RED);
        }
        return hasRedAbove || hasRedBelow;
    }

    private boolean squareHasRedBelow(int colIndex, int rowIndex) {
        if (rowIndex+1 < playerSquares2DList.get(colIndex).size())
            return playerSquares2DList.get(colIndex).get(rowIndex+1).getColor().equals(Color.RED);
        return false;
    }

    private boolean squareHasRedOnRight(int colIndex, int rowIndex) {
        if (colIndex+1 < playerSquares2DList.size())
            return playerSquares2DList.get(colIndex+1).get(rowIndex).getColor().equals(Color.RED);
        return false;
    }
}