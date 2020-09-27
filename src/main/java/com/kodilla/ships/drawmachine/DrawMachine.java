package com.kodilla.ships.drawmachine;

import com.kodilla.ships.SingleSquare.AiSquare;
import com.kodilla.ships.SingleSquare.PlayerSquare;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class DrawMachine {
    private final int RECTS_IN_ROW;
    private final int RECTS_IN_COLUMN;
    List<List<PlayerSquare>> playerSquares2DList = new ArrayList<>();
    List<List<AiSquare>> aiSquares2DList = new ArrayList<>();

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

//        PlayerSquare testPlayerSquare = new PlayerSquare(50, 50);

        for (int i = 0; i < RECTS_IN_COLUMN; i++) {
            List<PlayerSquare> playerSquaresInRowList = new ArrayList<>();
            List<AiSquare> aiSquaresInRowList = new ArrayList<>();
            for (int j = 0; j < RECTS_IN_ROW; j++) {
                PlayerSquare playerSquare = new PlayerSquare(50, 50);
                playerSquaresInRowList.add(playerSquare);
//                if (i == 7 && j == 5) {
//                    testPlayerSquare = playerSquare;
//                }
                playerBoard.add(playerSquare.getSquare(), i, j);
                AiSquare aiSquare = new AiSquare(50, 50);
                aiSquaresInRowList.add(aiSquare);
                aiBoard.add(aiSquare.getSquare(), i, j);
            }
            playerSquares2DList.add(playerSquaresInRowList);
            aiSquares2DList.add(aiSquaresInRowList);
        }

//        System.out.println("ZNALAZLEM!!! x: "+getGridOfSquare('x', testPlayerSquare));
//        System.out.println("ZNALAZLEM!!! y: "+getGridOfSquare('y', testPlayerSquare));

        HBox board = new HBox();
        board.getChildren().add(playerBoard);
        board.getChildren().add(aiBoard);
        board.setSpacing(20);
        return board;
    }

    public List<List<PlayerSquare>> getPlayerSquares2DList() {
        return this.playerSquares2DList;
    }

    public List<List<AiSquare>> getAiSquares2DList() {
        return this.aiSquares2DList;
    }
}
