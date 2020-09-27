package com.kodilla.ships.logicmachine;

import com.kodilla.ships.SingleSquare.PlayerSquare;

import java.util.List;
import java.util.Random;

public class FiringComputer {
    private List<List<PlayerSquare>> playerSquares2DList;
    private int[] floatingPlayerShipsQuantity;
    private final int MAX_X;
    private final int MAX_Y;
    private int prevShotX = -1; //wartość -1 oznacza, że nie był oddawany jeszcze strzał
    private int prevShotY = -1; //wartość -1 oznacza, że nie był oddawany jeszcze strzał
    private int prevAccurShotX = -1; //wartość -1 oznacza, że nie był oddawany jeszcze strzał
    private int prevAccurShotY = -1; //wartość -1 oznacza, że nie był oddawany jeszcze strzał
    private boolean prevShotWasAccurate = false;

    public FiringComputer(int[] SHIPS_QUANTITY, List<List<PlayerSquare>> playerSquares2DList) {
        this.playerSquares2DList = playerSquares2DList;
        this.floatingPlayerShipsQuantity = SHIPS_QUANTITY;
        MAX_X = playerSquares2DList.size() - 1;
        MAX_Y = playerSquares2DList.get(0).size() - 1;
    }

    public boolean fireAt() { //zwraca true jezeli strzal jest celny
        if (prevAccurShotX != -1) { //byl juz oddany celny strzal
            if (hasRedAndShotAround(prevAccurShotX, prevAccurShotY) && stillFloatShipsLongerThan(2)) {
                if (hasRedAndShotHorizontal(prevAccurShotX, prevAccurShotY)) {
                    System.out.println("Mysle jak strzelic w poziomie.");
                    System.out.println("Ostatnio trafilem w: x " + prevAccurShotX + ", y " + prevAccurShotY);
                    System.out.println("Jak do tej pory, dlugość trafionego statku wynosi: " + redAndShotHorizontalCounter(prevAccurShotX, prevAccurShotY));
                    System.out.println();
                    if (computeShotHorizontal(prevAccurShotX, prevAccurShotY)) {
                        return prevShotWasAccurate;
                    } else {
                        return blindShoot();
                    }
                } else if (hasRedAndShotVertical(prevAccurShotX, prevAccurShotY)) {
                    System.out.println("Mysle jak strzelic w pionie.");
                    System.out.println("Ostatnio trafilem w: x " + prevAccurShotX + ", y " + prevAccurShotY);
                    System.out.println("Jak do tej pory, długość trafionego statku wynosi: " + redAndShotVerticalCounter(prevAccurShotX, prevAccurShotY));
                    System.out.println();
                    if (computeShotVertical(prevAccurShotX, prevAccurShotY)) {
                        return prevShotWasAccurate;
                    } else {
                        return blindShoot();
                    }
                }
            } else if (stillFloatShipsLongerThan(1)) { //strzelam naokoło
                int x;
                int y;
                if (!playerSquares2DList.get(prevAccurShotX).get(prevAccurShotY+1).isHit()) {
                    x = prevAccurShotX;
                    y = prevAccurShotY+1;
                } else if (!playerSquares2DList.get(prevAccurShotX-1).get(prevAccurShotY).isHit()) {
                    x = prevAccurShotX-1;
                    y = prevAccurShotY;
                } else if (!playerSquares2DList.get(prevAccurShotX+1).get(prevAccurShotY).isHit()) {
                    x = prevAccurShotX+1;
                    y = prevAccurShotY;
                } else if (!playerSquares2DList.get(prevAccurShotX).get(prevAccurShotY-1).isHit()) {
                    x = prevAccurShotX;
                    y = prevAccurShotY-1;
                } else {
                    return blindShoot();
                }
                PlayerSquare playerSquare = playerSquares2DList.get(x).get(y);
                if (playerSquare.isShip()) {
                    prevAccurShotX = x;
                    prevAccurShotY = y;
                    prevShotWasAccurate = true;
                    playerSquare.cross();
                    return true;
                } else {
                    prevShotX = x;
                    prevShotY = y;
                    prevShotWasAccurate = false;
                    playerSquare.setColorBlack();
                    return false;
                }
            }
        }
        return blindShoot(); //wykonuje strzal na slepo
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

    private boolean computeShotHorizontal(int prevAccurShotX, int y) { //zwraca false, jezeli NIE wykona strzalu
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
        System.out.println("COUNTER wynosi: "+counter);
        if (x + 1 < playerSquares2DList.size()) { //probuje strzelic po prawej
            System.out.println("Probuje strzelic po prawej");
            PlayerSquare targetSquare = playerSquares2DList.get(x + 1).get(y);
            if (!targetSquare.isHit()) {
                if (targetSquare.isShip()) {
                    prevAccurShotX = x + 1;
                    prevAccurShotY = y;
                    prevShotWasAccurate = true;
                    targetSquare.cross();
                } else {
                    prevShotX = x + 1;
                    prevShotY = y;
                    prevShotWasAccurate = false;
                    targetSquare.setColorBlack();
                }
                return true;
            }
        }
        if (x - counter >= 0) { //probuje strzelic po lewej
            System.out.println("Probuje strzelic po lewej");
            PlayerSquare targetSquare = playerSquares2DList.get(x - counter).get(y);
            if (!targetSquare.isHit()) {
                if (targetSquare.isShip()) {
                    prevAccurShotX = x - counter;
                    prevAccurShotY = y;
                    prevShotWasAccurate = true;
                    targetSquare.cross();
                } else {
                    prevShotX = x - counter;
                    prevShotY = y;
                    prevShotWasAccurate = false;
                    targetSquare.setColorBlack();
                }
                return true;
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

    private boolean computeShotVertical(int x, int prevAccurShotY) { //zwraca false, jezeli NIE wykona strzalu
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
        System.out.println("COUNTER wynosi: "+counter);
        if (y+1 < playerSquares2DList.get(x).size()) { //probuje strzelic nizej
            System.out.println("Probuje strzelic nizej");
            PlayerSquare targetSquare = playerSquares2DList.get(x).get(y+1);
            if (!targetSquare.isHit()) {
                if (targetSquare.isShip()) {
                    prevAccurShotX = x;
                    prevAccurShotY = y + 1;
                    prevShotWasAccurate = true;
                    targetSquare.cross();
                } else {
                    prevShotX = x;
                    prevShotY = y + 1;
                    prevShotWasAccurate = false;
                    targetSquare.setColorBlack();
                }
                return true;
            }
        }
        if (y-counter >= 0) { //probuje strzelic wyzej
            System.out.println("Probuje strzelic wyzej");
            PlayerSquare targetSquare = playerSquares2DList.get(x).get(y-counter);
            if (!targetSquare.isHit()) {
                if (targetSquare.isShip()) {
                    prevAccurShotX = x;
                    prevAccurShotY = y-counter;
                    prevShotWasAccurate = true;
                    targetSquare.cross();
                } else {
                    prevShotX = x;
                    prevShotY = y-counter;
                    prevShotWasAccurate = false;
                    targetSquare.setColorBlack();
                }
                return true;
            }
        }
        return false;
    }

    private boolean stillFloatShipsLongerThan(int length) {
        if (length < floatingPlayerShipsQuantity.length) {
            for (int i = length; i < floatingPlayerShipsQuantity.length; i++) {
                if (floatingPlayerShipsQuantity[i] > 0) {
                    return true;
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
            return playerSquares2DList.get(x).get(y).isShip() && playerSquares2DList.get(x).get(y).isHit();
        } else {
            return false;
        }
    }

    private boolean blindShoot() {
        Random rnd = new Random();
        boolean noNeedToShootHere = true;
        while (noNeedToShootHere) {
            prevShotX = rnd.nextInt(MAX_X);
            prevShotY = rnd.nextInt(MAX_Y);
            noNeedToShootHere = hasRedAndShotAround(prevShotX, prevShotY) || playerSquares2DList.get(prevShotX).get(prevShotY).isHit();
        }
        PlayerSquare playerSquare = playerSquares2DList.get(prevShotX).get(prevShotY);
        if (playerSquare.isShip()) {
            System.out.println("PlayerSquare x: "+prevShotX+", y: "+prevShotY+" - IS SHIP");
            prevAccurShotX = prevShotX;
            prevAccurShotY = prevShotY;
            playerSquare.cross();
            prevShotWasAccurate = true;
        } else {
            System.out.println("PlayerSquare x: "+prevShotX+", y: "+prevShotY+" - IS NO SHIP");
            playerSquare.setColorBlack();
            prevShotWasAccurate = false;
        }
        return prevShotWasAccurate;
    }
}
