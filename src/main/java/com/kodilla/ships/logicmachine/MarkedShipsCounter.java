package com.kodilla.ships.logicmachine;

import com.kodilla.ships.SingleSquare.PlayerSquare;
import com.kodilla.ships.SingleSquare.SquareKey;

import java.util.*;

public class MarkedShipsCounter {
    private final List<List<PlayerSquare>> playerSquares2DList;
    private final int BOARD_SIZE_X;
    private final int BOARD_SIZE_Y;
    private final int[] EXPECTED_SHIPS_QUANTITY;

    public MarkedShipsCounter(List<List<PlayerSquare>> playerSquares2DList, int[] expectedShipsQuantity) {
        this.playerSquares2DList = playerSquares2DList;
        this.EXPECTED_SHIPS_QUANTITY = expectedShipsQuantity;
        this.BOARD_SIZE_X = playerSquares2DList.size();
        this.BOARD_SIZE_Y = playerSquares2DList.get(0).size();
    }

    public int getGridOfSquare(char gridId, PlayerSquare square) {
        for (int i = 0; i < playerSquares2DList.size(); i++) {
            for (int j = 0; j < playerSquares2DList.get(i).size(); j++) {
                if (gridId == 'x' && playerSquares2DList.get(i).get(j).equals(square)) {
                    return i;
                } else if (gridId == 'y' && playerSquares2DList.get(i).get(j).equals(square)) {
                    return j;
                }
            }
        }
        return -1;
    }

    public Map<SquareKey, PlayerSquare> collectNeighboursToMap(PlayerSquare playerSquare) {
        final int PLAYER_SQUARE_X = getGridOfSquare('x', playerSquare);
        final int PLAYER_SQUARE_Y = getGridOfSquare('y', playerSquare);
        Map<SquareKey, PlayerSquare> neighboursMap = new HashMap<>();
        for (int x = PLAYER_SQUARE_X-1; x <= PLAYER_SQUARE_X+1; x++) {
            for (int y = PLAYER_SQUARE_Y-1; y <= PLAYER_SQUARE_Y+1; y++) {
                if (x >= 0 && y >= 0 && x < playerSquares2DList.size() && y < playerSquares2DList.get(0).size()) {
                    if (playerSquares2DList.get(x).get(y).isShip()) {
                        SquareKey neighbourId = new SquareKey(x, y);
                        neighboursMap.put(neighbourId, playerSquares2DList.get(x).get(y));
                    }
                    if (playerSquare.isShip()) {
                        playerSquares2DList.get(x).get(y).addNeighbour(playerSquare, PLAYER_SQUARE_X, PLAYER_SQUARE_Y);
                    } else {
                        playerSquares2DList.get(x).get(y).removeNeighbour(PLAYER_SQUARE_X, PLAYER_SQUARE_Y);
                    }
                }
            }
        }
        SquareKey neighbourId = new SquareKey(PLAYER_SQUARE_X, PLAYER_SQUARE_Y);
        neighboursMap.remove(neighbourId);
        return neighboursMap;
    }

    public ShipsCounterPanelDto countAndGroupMarkedSquares() {
        boolean[][] squareNotBelongToGroup = new boolean[BOARD_SIZE_X][BOARD_SIZE_Y];
        for (int i = 0; i < playerSquares2DList.size(); i++) {
            for (int j = 0; j < playerSquares2DList.get(i).size(); j++) {
                squareNotBelongToGroup[i][j] = true;
            }
        }
        List<Map<SquareKey, PlayerSquare>> groupsList = new ArrayList<>();
        for (int i = 0; i < playerSquares2DList.size(); i++) {
            for (int j = 0; j < playerSquares2DList.get(i).size(); j++) {
                if (playerSquares2DList.get(i).get(j).isShip() && squareNotBelongToGroup[i][j]) {
                    Map<SquareKey, PlayerSquare> singleGroup = new HashMap<>();
                    singleGroup.put(new SquareKey(i, j), playerSquares2DList.get(i).get(j));
                    int groupSizeBfrChkNeighbours = singleGroup.size();
                    boolean needMoreLoops = true;
                    while (needMoreLoops) {
                        List<PlayerSquare> squaresInGroup = new ArrayList<>(singleGroup.values());
                        for (PlayerSquare playerSquare : squaresInGroup) {
                            List<PlayerSquare> innerNeighbours = new ArrayList<>(playerSquare.getMyNeighboursMap().values());
                            for (PlayerSquare innerNeighbour : innerNeighbours) {
                                int x = getGridOfSquare('x', innerNeighbour);
                                int y = getGridOfSquare('y', innerNeighbour);
                                if (innerNeighbour.isShip() && squareNotBelongToGroup[x][y]) {
                                    singleGroup.put(new SquareKey(x, y), innerNeighbour);
                                    squareNotBelongToGroup[x][y] = false;
                                }
                            }
                        }
                        if (singleGroup.size() == groupSizeBfrChkNeighbours)
                            needMoreLoops = false;
                        groupSizeBfrChkNeighbours = singleGroup.size();
                    }
                    groupsList.add(singleGroup);
                }
            }
        }
        return convertGroupsListToDto(groupsList);
    }

    private ShipsCounterPanelDto convertGroupsListToDto(List<Map<SquareKey, PlayerSquare>> groupsList) {
        int redSquaresQuantity = 0;
        int[] settedShipsQuantity = new int[EXPECTED_SHIPS_QUANTITY.length];
        for (int i = 0; i < EXPECTED_SHIPS_QUANTITY.length; i++) {
            settedShipsQuantity[i] = 0;
        }
        for (int i = 0; i < groupsList.size(); i++) {
            List<PlayerSquare> group = new ArrayList<>(groupsList.get(i).values());
            redSquaresQuantity += group.size();
            if (haveNoNeighboursDiagonally(group)
                    && !(hasMultipleRedsInRow(group) && hasMultipleRedsInColumn(group))) {
                if (group.size() <= EXPECTED_SHIPS_QUANTITY.length) {
                    settedShipsQuantity[group.size()-1] = settedShipsQuantity[group.size()-1]+1;
                }

            }
        }
        return new ShipsCounterPanelDto(redSquaresQuantity, settedShipsQuantity);
    }

    private boolean haveNoNeighboursDiagonally(List<PlayerSquare> group) {
        int[] xModifier = {-1, 1, -1, 1};
        int[] yModifier = {1, 1, -1, -1};
        for (int i = 0; i < group.size(); i++) {
            for (int j = 0; j < xModifier.length; j++) {
                int testedSquareX = getGridOfSquare('x', group.get(i));
                int testedSquareY = getGridOfSquare('y', group.get(i));
                int testedNeighbourX = testedSquareX + xModifier[j];
                int testedNeighbourY = testedSquareY + yModifier[j];
                if(testedNeighbourX >= 0 && testedNeighbourY >= 0 && testedNeighbourX < playerSquares2DList.size() && testedNeighbourY < playerSquares2DList.get(0).size()) {
                    if (playerSquares2DList.get(testedNeighbourX).get(testedNeighbourY).isShip())
                        return false;
                }
            }
        }
        return true;
    }

    private boolean hasMultipleRedsInRow(List<PlayerSquare> group) {
        boolean has = false;
        for (int i = 0; i < group.size(); i++) {
            int testedSquareX = getGridOfSquare('x', group.get(i));
            int testedSquareY = getGridOfSquare('y', group.get(i));
            if (testedSquareX - 1 >= 0) {
                if (playerSquares2DList.get(testedSquareX - 1).get(testedSquareY).isShip()) {
                    has = true;
                }
            }
            if (testedSquareX + 1 < playerSquares2DList.size()) {
                if (playerSquares2DList.get(testedSquareX + 1).get(testedSquareY).isShip()) {
                    has = true;
                }
            }
        }
        return has;
    }

    private boolean hasMultipleRedsInColumn(List<PlayerSquare> group) {
        boolean has = false;
        for (int i = 0; i < group.size(); i++) {
            int testedSquareX = getGridOfSquare('x', group.get(i));
            int testedSquareY = getGridOfSquare('y', group.get(i));
            if (testedSquareY - 1 >= 0) {
                if (playerSquares2DList.get(testedSquareX).get(testedSquareY - 1).isShip()) {
                    has = true;
                }
            }
            if (testedSquareY + 1 < playerSquares2DList.get(0).size()) {
                if (playerSquares2DList.get(testedSquareX).get(testedSquareY + 1).isShip()) {
                    has = true;
                }
            }
        }
        return has;
    }
}