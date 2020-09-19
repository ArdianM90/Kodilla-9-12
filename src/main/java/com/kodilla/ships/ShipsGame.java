package com.kodilla.ships;

import com.kodilla.ships.drawmachine.DrawMachine;
import com.kodilla.ships.drawmachine.ShipsCounterPanel;

import com.kodilla.ships.logicmachine.AiProceduresMachine;
import com.kodilla.ships.logicmachine.FiringComputer;
import com.kodilla.ships.logicmachine.MarkedShipsCounter;
import com.kodilla.ships.logicmachine.UserInterface;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;

public class ShipsGame extends Application {
    private final int RECTS_IN_ROW;
    private final int RECTS_IN_COLUMN;
    private final int[] SHIPS_QUANTITY;
    private List<List<SingleSquare>> playerSquares2DList;
    private List<List<AiSquare>> aiSquares2DList;
    private AiProceduresMachine aiMachine;
    private int playerFloatingSquares;
    private int aiFloatingSquares;

    public ShipsGame() {
        this.RECTS_IN_ROW = 10;
        this.RECTS_IN_COLUMN = 10;
        this.SHIPS_QUANTITY = new int[]{4, 3, 2, 1};
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        DrawMachine drawMachine = new DrawMachine(RECTS_IN_ROW, RECTS_IN_COLUMN);
        ShipsCounterPanel shipsCounterPanel = new ShipsCounterPanel(SHIPS_QUANTITY);
        BorderPane myPane = new BorderPane();
        myPane.setLeft(shipsCounterPanel.draw());
        myPane.setCenter(drawMachine.boards());
        Scene scene = new Scene(myPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gra w statki by Mienkovsky");
        primaryStage.show();
        playerSquares2DList = drawMachine.getPlayerSquares2DList();
        aiSquares2DList = drawMachine.getAiSquares2DList();
        UserInterface userInterface = new UserInterface();
        MarkedShipsCounter markedShipsCounter = new MarkedShipsCounter(playerSquares2DList);
        for (int i = 0; i < RECTS_IN_ROW; i++) {
            for (int j = 0; j < RECTS_IN_COLUMN; j++) {
                SingleSquare playerSquare = playerSquares2DList.get(i).get(j);
                playerSquare.getSquare().setOnMouseClicked(event -> {
                    userInterface.markMyShip(playerSquare);
                    shipsCounterPanel.updateShipsCounter(markedShipsCounter.countMarkedShips());
                });
            }
        }

        aiMachine = new AiProceduresMachine(SHIPS_QUANTITY, playerSquares2DList, aiSquares2DList);
        aiMachine.setAiShips();
        shipsCounterPanel.getPlayButton().setOnMouseClicked(event -> {
            if (shipsCounterPanel.getPlayButton().getTextFill().equals(Color.GREEN)) {
                playTheGame();
            } else {
                System.out.println("Nie mozemy jeszcze grac :(\nUstaw statki.");
            }
        });
    }

    private void playTheGame() {
        playerFloatingSquares = 20;
        aiFloatingSquares = 20;
        UserInterface userInterface = new UserInterface();
        FiringComputer firingMachine = new FiringComputer(SHIPS_QUANTITY, playerSquares2DList);
        Alert whoFirstInfo = new Alert(Alert.AlertType.INFORMATION);
        Random rnd = new Random();

        whoFirstInfo.setTitle("Pierwszy ruch");
        whoFirstInfo.setHeaderText(null);
        boolean playerIsFirst = rnd.nextBoolean();
        if (playerIsFirst) {
            whoFirstInfo.setContentText("Zaczynasz pierwszy.");
            whoFirstInfo.showAndWait();
        } else {
            whoFirstInfo.setContentText("Komputer zaczyna pierwszy.");
            whoFirstInfo.showAndWait();
            if (firingMachine.fireAt()) {
                playerFloatingSquares -= 1;
            }
        }
        for (int i = 0; i < RECTS_IN_ROW; i++) {
            for (int j = 0; j < RECTS_IN_COLUMN; j++) {
                playerSquares2DList.forEach(e -> e.forEach(f -> f.getSquare().setOnMouseClicked(event -> {})));
                AiSquare aiSquare = aiSquares2DList.get(i).get(j);
                aiSquare.getSquare().setOnMouseClicked(event -> {
                    if (userInterface.fireAt(aiSquare)) { //strzela gracz. FireAt() zwraca true, przy tafieniu w statek
                        aiFloatingSquares -= 1;
                        if (aiFloatingSquares == 0) {
                            aiSquares2DList.forEach(e -> e.forEach(f -> f.getSquare().setOnMouseClicked(event2 -> {})));
                            Alert endInfo = new Alert(Alert.AlertType.INFORMATION);
                            endInfo.setTitle("Koniec gry");
                            endInfo.setHeaderText(null);
                            endInfo.setContentText("WYGRYWASZ!!!");
                            endInfo.showAndWait();
                            System.exit(0);
                        }
                    }
                    if (aiFloatingSquares > 0) {
                        if (firingMachine.fireAt()) {
                            playerFloatingSquares -= 1;
                            System.out.println("==========");
                            System.out.println("Zostalo ci "+playerFloatingSquares+" statkow.");
                            System.out.println("==========");
                            if (playerFloatingSquares == 0) {
                                aiSquares2DList.forEach(e -> e.forEach(f -> f.getSquare().setOnMouseClicked(event2 -> {})));
                                Alert endInfo = new Alert(Alert.AlertType.INFORMATION);
                                endInfo.setTitle("Koniec gry");
                                endInfo.setHeaderText(null);
                                endInfo.setContentText("PRZEGRYWASZ!!!");
                                endInfo.showAndWait();
                                System.exit(0);
                            }
                        }
                    }
                });
            }
        }
    }
}