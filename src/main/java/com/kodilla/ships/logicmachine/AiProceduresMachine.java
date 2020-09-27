package com.kodilla.ships.logicmachine;

import com.kodilla.ships.SingleSquare.AiSquare;
import com.kodilla.ships.SingleSquare.PlayerSquare;

import java.util.List;
import java.util.Random;

public class AiProceduresMachine {
    private final int[] SHIPS_EXPECTED_QUANTITY;
    private final List<List<AiSquare>> AI_SQUARES_2D_LIST;

    public AiProceduresMachine(int[] shipsExpectedQuantity, List<List<PlayerSquare>> playerSquares2DList, List<List<AiSquare>> aiSquares2DList) {
        this.SHIPS_EXPECTED_QUANTITY = shipsExpectedQuantity;
        this.AI_SQUARES_2D_LIST = aiSquares2DList;
    }

    public void setAiShips() {
        Random rnd = new Random();
        for (int i = SHIPS_EXPECTED_QUANTITY.length-1; i >= 0; i--) { //Pętla zmieniająca typ ustawianego statku. Wykonuje sie od tylu bo latwiej jest ustawic duzy statek na poczatku (na pustej planszy), a najwiekszy statek jest na koncu tablicy
            for (int j = 0; j < SHIPS_EXPECTED_QUANTITY[i]; j++) { //Pętla ustawiająca kolejne statki danego typu
                boolean horizontalOrient = rnd.nextBoolean();
                boolean gridsAreReady = false;
                int xGrid = -1;
                int yGrid = -1;
                while (!gridsAreReady) {
                    xGrid = findGrid(i + 1, horizontalOrient, -1);
                    yGrid = findGrid(i + 1, horizontalOrient, xGrid);
                    gridsAreReady = shipIsAllowedHere(xGrid, yGrid, i + 1, horizontalOrient);
                }
                setShip(xGrid, yGrid, i + 1, horizontalOrient);
            }
        }
    }

    private int findGrid(int shipLength, boolean horizontalOrient, int xGrid) {
        Random rnd = new Random();
        int grid = -1;
        boolean gridIsNotCorrect = true;
        while (gridIsNotCorrect) {
            if (xGrid == -1) { //-1 oznacza ze losowana jest wsp. X
                grid = rnd.nextInt(AI_SQUARES_2D_LIST.size());
                if (horizontalOrient) {
                    gridIsNotCorrect = !(grid + shipLength < AI_SQUARES_2D_LIST.size());
                } else {
                    gridIsNotCorrect = false;
                }
            } else { //wsp. X jest podana, wiec losuje wsp. Y
                grid = rnd.nextInt(AI_SQUARES_2D_LIST.get(0).size());
                if (!horizontalOrient) {
                    gridIsNotCorrect = !(grid + shipLength < AI_SQUARES_2D_LIST.get(0).size());
                } else {
                    gridIsNotCorrect = false;
                }
            }
        }
        return grid;
    }

    private boolean shipIsAllowedHere(int xGrid, int yGrid, int length, boolean horizontalOrient) {
        boolean isAllowed = true;
        int counter = 0;
        while (isAllowed && counter < length) {
            if (horizontalOrient) {
                if (squareIsRedOrHasRedAround(xGrid+counter, yGrid)) {
                    isAllowed = false;
                }
            } else {
                if (squareIsRedOrHasRedAround(xGrid, yGrid+counter)) {
                    isAllowed = false;
                }
            }
            counter++;
        }
        return isAllowed;
    }

    private boolean squareIsRedOrHasRedAround(int xGrid, int yGrid) {
        boolean redHasBeenFound = false;
        int i = xGrid-1;
        while ((!redHasBeenFound) && (i < xGrid+2)) {
            int j = yGrid-1;
            while ((!redHasBeenFound) && (j < yGrid+2)) {
                if (i >= 0 && j >= 0 && i < AI_SQUARES_2D_LIST.size() && j < AI_SQUARES_2D_LIST.get(0).size()) {
                    if (AI_SQUARES_2D_LIST.get(i).get(j).isShip()) {
                        redHasBeenFound = true;
                    }
                }
                j++;
            }
            i++;
        }
        return redHasBeenFound;
    }

    private void setShip(int xGrid, int yGrid, int length, boolean horizontalOrient) {
        for (int i = 0; i < length; i++) {
            if (horizontalOrient) {
                AI_SQUARES_2D_LIST.get(xGrid+i).get(yGrid).setShip();
            } else {
                AI_SQUARES_2D_LIST.get(xGrid).get(yGrid+i).setShip();
            }
        }
    }
}