package com.kodilla.ships.drawmachine;

import com.kodilla.ships.SingleSquare;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class DrawMachine {
    private final int RECTS_IN_ROW;
    private final int RECTS_IN_COLUMN;
    List<List<SingleSquare>> playerSquares2DList = new ArrayList<>();
    List<List<SingleSquare>> aiSquares2DList = new ArrayList<>();

    public DrawMachine(int rectsInRow, int rectsInColumn) {
        this.RECTS_IN_ROW = rectsInRow;
        this.RECTS_IN_COLUMN = rectsInColumn;
    }

    public HBox boards() {
        GridPane playerBoard = new GridPane();
        GridPane aiBoard = new GridPane();
        playerBoard.setHgap(10);
        playerBoard.setVgap(10);
        playerBoard.setPadding(new Insets(10, 10, 10, 10));
        aiBoard.setHgap(10);
        aiBoard.setVgap(10);
        aiBoard.setPadding(new Insets(10, 10, 10, 10));

        for (int i = 0; i < RECTS_IN_COLUMN; i++) {
            List<SingleSquare> playerSquaresInRowList = new ArrayList<>();
            List<SingleSquare> aiSquaresInRowList = new ArrayList<>();
            for (int j = 0; j < RECTS_IN_ROW; j++) {
                SingleSquare playerSquare = new SingleSquare(50, 50);
                playerSquaresInRowList.add(playerSquare);
                playerBoard.add(playerSquare.getSquare(), i, j);
                SingleSquare aiSquare = new SingleSquare(50, 50);
                aiSquaresInRowList.add(aiSquare);
                aiBoard.add(aiSquare.getSquare(), i, j);
            }
            playerSquares2DList.add(playerSquaresInRowList);
            aiSquares2DList.add(aiSquaresInRowList);
        }
        HBox board = new HBox();
        board.getChildren().add(playerBoard);
        board.getChildren().add(aiBoard);
        board.setSpacing(20);
        return board;
    }

    public List<List<SingleSquare>> getPlayerSquares2DList() {
        return this.playerSquares2DList;
    }

    public List<List<SingleSquare>> getAiSquares2DList() {
        return this.aiSquares2DList;
    }
}
