package com.kodilla.ships.logicmachine;

import com.kodilla.ships.SingleSquare;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Random;

public class FiringComputer {
    private List<List<SingleSquare>> playerSquares2DList;
    private int[] floatingPlayerShipsQuantity;
    private final int MAX_X;
    private final int MAX_Y;
    private int prevShotX = -1; //wartość -1 oznacza, że nie był oddawany jeszcze strzał
    private int prevShotY = -1; //wartość -1 oznacza, że nie był oddawany jeszcze strzał
    private int prevAccurShotX = -1; //wartość -1 oznacza, że nie był oddawany jeszcze strzał
    private int prevAccurShotY = -1; //wartość -1 oznacza, że nie był oddawany jeszcze strzał
    private boolean prevShotWasAccurate = false;

    public FiringComputer(int[] SHIPS_QUANTITY, List<List<SingleSquare>> playerSquares2DList) {
        this.playerSquares2DList = playerSquares2DList;
        this.floatingPlayerShipsQuantity = SHIPS_QUANTITY;
        MAX_X = playerSquares2DList.size() - 1;
        MAX_Y = playerSquares2DList.get(0).size() - 1;
    }

    public boolean fireAt() { //zwraca true/false - jezeli strzal jest trafiony
        System.out.println("Komputer losuje kwadrat i strzela.");
        Random rnd = new Random();
        System.out.println("prevShotX: " + prevShotX + ", prevAccurShotX: " + prevAccurShotX);
        if (prevShotX == -1 || prevAccurShotX == -1) { //wykonuje strzal na slepo (pierwszy, lub po resecie)
            return blindShoot();
        } else { //kontynuję strzelanie po celnym strzale
            if (hasRedAndShotAround(prevAccurShotX, prevAccurShotY)) {
                if (hasRedAndShotHorizontal(prevAccurShotX, prevAccurShotY)) {
                    System.out.println("Mysle jak strzelic w poziomie.");
                    System.out.println();
                    System.out.println("Ostatnio trafilem w: x " + prevAccurShotX + ", y " + prevAccurShotY);
                    System.out.println("Jak do tej pory, dlugość trafionego statku wynosi: " + redAndShotHorizontalCounter(prevAccurShotX, prevAccurShotY));
                    computeShotHorizontal(prevAccurShotX, prevAccurShotY);
                    //sprawdzam w lewo
                    //jak moge strzelic to strzelam
                    //sprawdzam w prawo
                    //jak moge to strzelam
                } else if (hasRedAndShotVertical(prevAccurShotX, prevAccurShotY)) {
                    System.out.println("Mysle jak strzelic w pionie.");
                    System.out.println();
                    System.out.println("Ostatnio trafilem w: x " + prevAccurShotX + ", y " + prevAccurShotY);
                    System.out.println("Jak do tej pory, długość trafionego statku wynosi: " + redAndShotVerticalCounter(prevAccurShotX, prevAccurShotY));
                    computeShotVertical(prevAccurShotX, prevAccurShotY);
                    //sprawdzam w gore
                    //jak moge strzelic to strzelam
                    //sprawdzam w dol
                    //jak moge to strzelam
                }
            } else { //strzelam naokoło
                System.out.println("Trafilem. Mysle jak strzelic naokolo.");
                System.out.println();
                int[] xGridsAround = {prevAccurShotX, prevAccurShotX - 1, prevAccurShotX + 1, prevAccurShotX};
                int[] yGridsAround = {prevAccurShotY + 1, prevAccurShotY, prevAccurShotY, prevAccurShotY - 1};
                boolean cantShootHere = true;
                int choice = -1;
                while (cantShootHere) {
                    choice = rnd.nextInt(4);
                    cantShootHere = playerSquares2DList.get(xGridsAround[choice]).get(yGridsAround[choice]).isHit();
                    //co w przypadku gdy wszystkie 4 naokolo sa ustrzelone???
                }
                SingleSquare playerSquare = playerSquares2DList.get(xGridsAround[choice]).get(yGridsAround[choice]);
                if (playerSquare.getColor().equals(Color.RED)) {
                    prevAccurShotX = xGridsAround[choice];
                    prevAccurShotY = yGridsAround[choice];
                    prevShotWasAccurate = true;
                    playerSquare.cross();
                    return true;
                } else {
                    playerSquare.setColorBlack();
                    return false;
                }
            }
        }
        return false;
    }

    private int redAndShotHorizontalCounter(int prevAccurShotX, int y) {
        int x = prevAccurShotX;
        boolean goCheckNext = true;
        while (x - 1 >= 0 && goCheckNext) {
            if (squareIsRedAndShot(x - 1, y)) {
                x = x - 1;
            } else {
                goCheckNext = false;
            }
        }
        int counter = 1;
        goCheckNext = true;
        while (x + 1 < playerSquares2DList.size() && goCheckNext) {
            if (squareIsRedAndShot(x + 1, y)) {
                x = x + 1;
                counter++;
            } else {
                goCheckNext = false;
            }
        }
        return counter;
    }

    private boolean computeShotHorizontal(int prevAccurShotX, int y) {
        int x = prevAccurShotX;
        boolean goCheckNext = true;
        while (x - 1 >= 0 && goCheckNext) {
            if (squareIsRedAndShot(x - 1, y)) {
                x = x - 1;
            } else {
                goCheckNext = false;
            }
        }
        int counter = 1;
        goCheckNext = true;
        while (x + 1 < playerSquares2DList.size() && goCheckNext) {
            if (squareIsRedAndShot(x + 1, y)) {
                x = x + 1;
                counter++;
            } else {
                goCheckNext = false;
            }
        }
        if (x + 1 < playerSquares2DList.size()) { //probuje strzelic po prawej
            SingleSquare targetSquare = playerSquares2DList.get(x + 1).get(y);
            if (!targetSquare.isHit()) {
                if (targetSquare.getColor().equals(Color.RED)) {
                    prevAccurShotX = x;
                    prevAccurShotY = y + 1;
                    prevShotWasAccurate = true;
                    targetSquare.cross();
                    return true;
                } else {
                    targetSquare.setColorBlack();
                    return false;
                }
            }
        }
        if (x - counter >= 0) { //probuje strzelic po lewej
            SingleSquare targetSquare = playerSquares2DList.get(x - counter).get(y);
            if (!targetSquare.isHit()) {
                if (targetSquare.getColor().equals(Color.RED)) {
                    prevAccurShotX = x;
                    prevAccurShotY = y + 1;
                    prevShotWasAccurate = true;
                    targetSquare.cross();
                    return true;
                } else {
                    targetSquare.setColorBlack();
                    return false;
                }
            }
        }
        return false;
    }

    private int redAndShotVerticalCounter(int x, int prevAccurShotY) {
        int y = prevAccurShotY;
        boolean goCheckNext = true;
        while (y-1 >= 0 && goCheckNext) {
            if (squareIsRedAndShot(x, y-1)) {
                y = y-1;
            } else {
                goCheckNext = false;
            }
        }
        int counter = 1;
        goCheckNext = true;
        while (y+1 < playerSquares2DList.get(x).size() && goCheckNext) {
            if (squareIsRedAndShot(x, y+1)) {
                y = y+1;
                counter ++;
            } else {
                goCheckNext = false;
            }
        }
        return counter;
    }

    private boolean computeShotVertical(int x, int prevAccurShotY) {
        int y = prevAccurShotY;
        boolean goCheckNext = true;
        while (y-1 >= 0 && goCheckNext) { //przesuwam X maksymalnie w lewo
            if (squareIsRedAndShot(x, y-1)) {
                y = y-1;
            } else {
                goCheckNext = false;
            }
        }
        int counter = 1;
        goCheckNext = true;
        while (y+1 < playerSquares2DList.get(x).size() && goCheckNext) { //mierze ustrzelony statek
            if (squareIsRedAndShot(x, y+1)) {
                y = y+1;
                counter ++;
            } else {
                goCheckNext = false;
            }
        }
        if (y+1 < playerSquares2DList.get(x).size()) { //probuje strzelic wyzej
            SingleSquare targetSquare = playerSquares2DList.get(x).get(y+1);
            if (!targetSquare.isHit()) {
                if (targetSquare.getColor().equals(Color.RED)) {
                    prevAccurShotX = x;
                    prevAccurShotY = y+1;
                    prevShotWasAccurate = true;
                    targetSquare.cross();
                    return true;
                } else {
                    targetSquare.setColorBlack();
                    return false;
                }
            }
        }
        if (y-counter >= 0) { //probuje strzelic nizej
            SingleSquare targetSquare = playerSquares2DList.get(x).get(y-counter);
            if (!targetSquare.isHit()) {
                if (targetSquare.getColor().equals(Color.RED)) {
                    prevAccurShotX = x;
                    prevAccurShotY = y+1;
                    prevShotWasAccurate = true;
                    targetSquare.cross();
                    return true;
                } else {
                    targetSquare.setColorBlack();
                    return false;
                }
            }
        }
        return false;
    }

    private boolean hasRedAndShotAround(int x, int y) {
        return squareIsRedAndShot(x-1, y-1)
                || squareIsRedAndShot(x, y-1)
                || squareIsRedAndShot(x+1, y-1)
                || squareIsRedAndShot(x-1, y)
                || squareIsRedAndShot(x+1, y)
                || squareIsRedAndShot(x-1, y+1)
                || squareIsRedAndShot(x, y+1)
                || squareIsRedAndShot(x+1, y+1);
    }

    private boolean hasRedAndShotHorizontal(int x, int y) {
        return squareIsRedAndShot(x-1, y) || squareIsRedAndShot(x+1, y);
    }

    private boolean hasRedAndShotVertical(int x, int y) {
        return squareIsRedAndShot(x, y-1) || squareIsRedAndShot(x, y+1);
    }

    private boolean squareIsRedAndShot(int x, int y) {
        if (x >= 0 && x < playerSquares2DList.size() && y >= 0 && y < playerSquares2DList.get(0).size()) {
            return playerSquares2DList.get(x).get(y).getColor().equals(Color.RED) && playerSquares2DList.get(x).get(y).isHit();
        } else {
            return false;
        }
    }

    private boolean blindShoot() {
        System.out.println("Wykonuję strzal na slepo (pierwszy, lub po resecie).");
        System.out.println();
        //dodać przypadek trafienia w trafione miejsce
        Random rnd = new Random();
        boolean cantShootHere = true;
        while (cantShootHere) {
            prevShotX = rnd.nextInt(MAX_X);
            prevShotY = rnd.nextInt(MAX_Y);
            cantShootHere = hasRedAndShotAround(prevShotX, prevShotY);
        }
        SingleSquare playerSquare = playerSquares2DList.get(prevShotX).get(prevShotY);
        if (playerSquare.getColor().equals(Color.RED)) {
            prevAccurShotX = prevShotX;
            prevAccurShotY = prevShotY;
            prevShotWasAccurate = true;
            playerSquare.cross();
            return true;
        } else {
            playerSquare.setColorBlack();
            return false;
        }
    }
}
