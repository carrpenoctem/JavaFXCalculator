package com.dmodzelewski.calculator.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class FXMLController implements Initializable {

    @FXML
    private TextField inputField;
    @FXML
    private GridPane simpleGrid;
    @FXML
    private GridPane advancedGrid;
    @FXML
    private GridPane nerdGrid;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // getting all nodes from grids
        List<Node> buttonsList_Node = new ArrayList<>();
        buttonsList_Node.addAll(simpleGrid.getChildren());
        buttonsList_Node.addAll(advancedGrid.getChildren());
        buttonsList_Node.addAll(nerdGrid.getChildren());
        
        // casting nodes into buttons
        List<Button> buttonList = new ArrayList<>();
        for (Node b : buttonsList_Node) {
            if (b instanceof Button) {
                buttonList.add((Button) b);
            }
        }
        
        // setting action on buttons depending on their value
        for (Button b : buttonList) {
            if ("piBut".equals(b.getId())||"piBut1".equals(b.getId())||"piBut2".equals(b.getId())) {
                b.setOnAction((event) -> {
                    System.out.println(Math.PI);
                });
            } else {
                b.setOnAction((event) -> {
                    System.out.println(b.getText());
                });
            }
        }

    }
}
