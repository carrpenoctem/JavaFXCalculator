package com.dmodzelewski.calculator.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class FXMLController implements Initializable {

    // Logger for logging errors
    private static final Logger classLogger = Logger.getLogger(FXMLController.class.getCanonicalName());

    // TextField where our numbers appear
    @FXML
    private TextField inputField = new TextField();
    // Button container for simple calculator
    @FXML
    private GridPane simpleGrid;
    // Button container for advanced calc
    @FXML
    private GridPane advancedGrid;
    // Button container for nerdy calc
    @FXML
    private GridPane nerdGrid;
    // Operation buttons
    @FXML
    private Button addBut, subBut, multiBut, divBut;

    // list of all action buttons since I was too lazy to set id for panes 
    // they are stored in
    private List<Button> actionButtons = new ArrayList<>();
    // first number we use in our equation
    private double firstNumber;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // populating actionButtons list
        actionButtons.addAll(Arrays.asList(addBut, subBut, multiBut, divBut));

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
            if ("piBut".equals(b.getId()) || "piBut1".equals(b.getId()) || "piBut2".equals(b.getId())) {
                b.setOnAction((event) -> {
                    inputField.setText(Math.PI + "");
                });
            } else {
                b.setOnAction((event) -> {
                    setInputField(b);
                });
            }
        }
    }

    // useful method for clearing our textfield togglebutton and stored variable
    @FXML
    private void clearInput() {
        inputField.clear();
        firstNumber = 0;
        for (Button b : actionButtons) {
            b.setDisable(false);
        }
    }

    // methods for operations
    // type of operation will be based on which button is disabled
    @FXML
    private void addNumber() {
        if (!inputField.getText().isEmpty()) {
            prepareFirstNumber();
            addBut.setDisable(true);
        }
    }
    @FXML
    private void subtractNumber() {
        if (!inputField.getText().isEmpty()) {
            prepareFirstNumber();
            subBut.setDisable(true);
        }
    }
    @FXML
    private void multiplyNumber() {
        if (!inputField.getText().isEmpty()) {
            prepareFirstNumber();
            multiBut.setDisable(true);
        }
    }
    @FXML
    private void divideNumber() {
        if (!inputField.getText().isEmpty()) {
            prepareFirstNumber();
            divBut.setDisable(true);
        }
    }
    
    // final method for computing output
    @FXML
    private void countNumber(){
        
    }

    // method for setting value for our inputField
    private void setInputField(Button b) {

        if (".".equals(b.getText()) && inputField.getText().contains(".")) {
            classLogger.log(Level.WARNING, ". is already in there");
        } else if (!".".equals(b.getText()) && "0".equals(inputField.getText())) {
            classLogger.log(Level.WARNING, "0 can be in front for decimals only");
            inputField.setText(b.getText());
        } else if (".".equals(b.getText()) && inputField.getText().length() < 1) {
            classLogger.log(Level.WARNING, "number cannot start with .");
        } else {
            inputField.setText(inputField.getText().concat(b.getText()));
        }
    }

    private void prepareFirstNumber() {
        firstNumber = Double.parseDouble(inputField.getText());
        inputField.clear();
        for (Button b : actionButtons) {
            b.setDisable(false);
        }
    }
}
