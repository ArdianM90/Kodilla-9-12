package com.kodilla.ships.drawmachine;

import com.kodilla.ships.logicmachine.ShipsCounterPanelDto;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ShipsCounterPanel {
    private String labelStyleBlack = "-fx-font-weight: bold; -fx-fill: black";
    private String labelStyleRed = "-fx-font-weight: bold; -fx-fill: red";
    private String buttonStyleGreen = "-fx-font-weight: bold; -fx-text-fill: green";
    private String buttonStyleRed = "-fx-font-weight: bold; -fx-text-fill: red";

    private int[] shipsExpectedQuantity = new int[4];
    private int[] playerShipsActualQuantity = {0, 0, 0, 0};
    private Text[] shipLabelsArray = new Text[4];
    Text infoLabel;
    Text carrierLabel;
    Text cruiserLabel;
    Text frigateLabel;
    Text patrolBoatLabel;
    Button playButton;
    VBox vbox;

    public ShipsCounterPanel(int[] shipsExpectedQuantity) {
        this.shipsExpectedQuantity = shipsExpectedQuantity;
    }

    public VBox draw() {
        infoLabel = new Text();
        carrierLabel = new Text();
        cruiserLabel = new Text();
        frigateLabel = new Text();
        patrolBoatLabel = new Text();
        playButton = new Button();
        vbox = new VBox();

        infoLabel.setText("Umieść swoje statki na planszy");
        infoLabel.setStyle(labelStyleBlack);

        patrolBoatLabel.setText("Patrolowce: 0/");
        patrolBoatLabel.setStyle(labelStyleRed);
        shipLabelsArray[0] = patrolBoatLabel;
        frigateLabel.setText("Fregaty: 0/");
        frigateLabel.setStyle(labelStyleRed);
        shipLabelsArray[1] = frigateLabel;
        cruiserLabel.setText("Krążowniki: 0/");
        cruiserLabel.setStyle(labelStyleRed);
        shipLabelsArray[2] = cruiserLabel;
        carrierLabel.setText("Lotniskowce: 0/");
        carrierLabel.setStyle(labelStyleRed);
        shipLabelsArray[3] = carrierLabel;
        for (int i = 0; i < shipLabelsArray.length; i++) {
            shipLabelsArray[i].setText(shipLabelsArray[i].getText()+shipsExpectedQuantity[i]);
        }

        playButton.setText("PLAY");
        playButton.setStyle(buttonStyleRed);

        vbox.getChildren().add(infoLabel);
        vbox.getChildren().add(patrolBoatLabel);
        vbox.getChildren().add(frigateLabel);
        vbox.getChildren().add(cruiserLabel);
        vbox.getChildren().add(carrierLabel);
        vbox.getChildren().add(playButton);
        return vbox;
    }

//    public void updateCounter(int[] actualShips) {
//        boolean allShipsArePlaced = true;
//        for (int i = 0; i < playerShipsActualQuantity.length; i++) {
//            String newText = shipLabelsArray[i].getText().substring(0, shipLabelsArray[i].getText().length()-3)+actualShips[i]+"/"+shipLabelsArray[i].getText().charAt(shipLabelsArray[i].getText().length()-1);
//            shipLabelsArray[i].setText(newText);
//            int shipExpectedAmount = Integer.parseInt(shipLabelsArray[i].getText().substring(shipLabelsArray[i].getText().length()-1));
//            if (actualShips[i] == shipExpectedAmount) {
//                shipLabelsArray[i].setStyle(labelStyleBlack);
//            } else {
//                shipLabelsArray[i].setStyle(labelStyleRed);
//                allShipsArePlaced = false;
//            }
//        }
//        if (allShipsArePlaced) {
//            playButton.setStyle(buttonStyleGreen);
//            infoLabel.setText("Naciśnij PLAY aby rozpocząć grę");
//        } else {
//            playButton.setStyle(buttonStyleRed);
//            infoLabel.setText("Umieść swoje statki na planszy");
//        }
//    }

    public void updateCounterPanel(ShipsCounterPanelDto entryDto) {
        boolean allShipsArePlaced = true;
        for (int i = 0; i < playerShipsActualQuantity.length; i++) {
            String newText = shipLabelsArray[i].getText().substring(0, shipLabelsArray[i].getText().length()-3)+entryDto.getPlayerShipsQuantity()[i]+"/"+shipLabelsArray[i].getText().charAt(shipLabelsArray[i].getText().length()-1);
            shipLabelsArray[i].setText(newText);
            int shipExpectedAmount = Integer.parseInt(shipLabelsArray[i].getText().substring(shipLabelsArray[i].getText().length()-1));
            if (entryDto.getPlayerShipsQuantity()[i] == shipExpectedAmount) {
                shipLabelsArray[i].setStyle(labelStyleBlack);
            } else {
                shipLabelsArray[i].setStyle(labelStyleRed);
                allShipsArePlaced = false;
            }
        }
        if (allShipsArePlaced) {
            playButton.setStyle(buttonStyleGreen);
            infoLabel.setText("Naciśnij PLAY aby rozpocząć grę");
        } else {
            playButton.setStyle(buttonStyleRed);
            infoLabel.setText("Umieść swoje statki na planszy");
        }
    }

    public Button getPlayButton() {
        return playButton;
    }
}