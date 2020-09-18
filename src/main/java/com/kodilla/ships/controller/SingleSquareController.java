package com.kodilla.ships.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;


public class SingleSquareController {
    @FXML
    void initialize() {
        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Akcja!");
            }
        };
    }
}
