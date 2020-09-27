package com.kodilla.ships.SingleSquare;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

public class PlayerSquare implements SingleSquare {
    private final StackPane STACK_PANE;
    private final Rectangle SQUARE;
    private boolean isHit = false;
    private Map<SquareKey, PlayerSquare> myNeighboursList;
    //=============================
    //TEST
    private Text infoLabel = new Text();
    //=============================

    public PlayerSquare(int width, int height) {
        this.STACK_PANE = new StackPane();
        this.SQUARE = new Rectangle(width, height, Color.DARKBLUE);
        this.myNeighboursList = new HashMap<>();
        SQUARE.setStroke(Color.BLACK);
        STACK_PANE.getChildren().add(SQUARE);
        //=============================
        //TEST
        infoLabel.setText(Integer.toString(myNeighboursList.size()));
        infoLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 40; -fx-fill: black");
        STACK_PANE.getChildren().add(infoLabel);
        //=============================
    }

    public void cross() {
        int LINE_WIDTH = 5;
        Line line1 = new Line(LINE_WIDTH * 2, LINE_WIDTH * 2, SQUARE.getWidth(), SQUARE.getHeight());
        line1.setStroke(Color.BLACK);
        line1.setStrokeWidth(LINE_WIDTH);
        Line line2 = new Line(LINE_WIDTH * 2, SQUARE.getHeight() - (LINE_WIDTH * 2), SQUARE.getWidth(), 0);
        line2.setStroke(Color.BLACK);
        line2.setStrokeWidth(LINE_WIDTH);
        STACK_PANE.getChildren().addAll(line1, line2);
        isHit = true;
    }

    public boolean isShip() {
        return SQUARE.getFill().equals(Color.RED);
    }

    public boolean isHit() {
        return isHit;
    }

    public void addNeighbour(PlayerSquare newNeighbour, int neighbourX, int neighbourY) {
        SquareKey neighbourId = new SquareKey(neighbourX, neighbourY);
        myNeighboursList.put(neighbourId, newNeighbour);
    }

    public void removeNeighbour(int neighbourX, int neighbourY) {
        System.out.println("Przed odjęciem rozmiar wynosi "+myNeighboursList.size());
        myNeighboursList.remove(neighbourX+"-"+neighbourY);
        System.out.println("Po odjęciu rozmiar wynosi "+myNeighboursList.size());
        System.out.println();
    }

    public void setShip() {
        if (SQUARE.getFill().equals(Color.RED)) {
            SQUARE.setFill(Color.DARKBLUE);
        } else {
            SQUARE.setFill(Color.RED);
        }
    }

    public void setColorBlack() {
        SQUARE.setFill(Color.BLACK);
        isHit = true;
    }

    public void setMyNeighboursMap(Map<SquareKey, PlayerSquare> newNeighboursList) {
        myNeighboursList = newNeighboursList;
    }

    public StackPane getSquare() {
        return STACK_PANE;
    }

    public Map<SquareKey, PlayerSquare> getMyNeighboursMap() {
        return myNeighboursList;
    }

    //=============================
    //TEST
    public void TestNumberLabel() {
        infoLabel.setText(Integer.toString(myNeighboursList.size()));
    }
    //=============================
}
