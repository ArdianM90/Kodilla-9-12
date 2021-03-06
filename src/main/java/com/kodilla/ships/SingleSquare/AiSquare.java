package com.kodilla.ships.SingleSquare;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class AiSquare implements SingleSquare {
    private final StackPane STACK_PANE;
    private final Rectangle SQUARE;
    private boolean isHit = false;
    private boolean isShip = false;

    public AiSquare(int width, int height) {
        this.STACK_PANE = new StackPane();
        this.SQUARE = new Rectangle(width, height, Color.DARKBLUE);
        SQUARE.setStroke(Color.BLACK);
        STACK_PANE.getChildren().add(SQUARE);
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
        SQUARE.setFill(Color.RED);
        isHit = true;
    }

    public boolean isShip() {
        return isShip;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setShip() {
        isShip = true;
    }

    public void setColorBlack() {
        SQUARE.setFill(Color.BLACK);
        isHit = true;
    }

    public StackPane getSquare() {
        return STACK_PANE;
    }
}
