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
    // Button containers for simple,adv and nerd digits
    @FXML
    private GridPane simpleGrid, advancedGrid, nerdGrid;
    // Button containers for simple,adv and nerd operations
    @FXML
    private VBox simpleVbox, advancedVbox, nerdVbox;
    // Our calculate button
    @FXML
    private Button calculateBut;

    // list of all digit buttons
    private List<Button> buttonList = new ArrayList<>();
    // list of all action buttons
    private List<Button> actionButtons = new ArrayList<>();
    // first number we use in our equation
    private double firstNumber;
    // second number we use in our equation
    private double secondNumber;
    // default style of button (for calculate button listener)
    private String defaultStyle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // getting default style of our calc button
        defaultStyle = calculateBut.getStyle();

        // getting all nodes from grids
        List<Node> buttonsList_Node = new ArrayList<>();
        buttonsList_Node.addAll(simpleGrid.getChildren());
        buttonsList_Node.addAll(advancedGrid.getChildren());
        buttonsList_Node.addAll(nerdGrid.getChildren());

        // casting nodes into buttons
        buttonList = new ArrayList<>();
        for (Node b : buttonsList_Node) {
            if (b instanceof Button) {
                buttonList.add((Button) b);
            }
        }

        // doing the same for action buttons
        List<Node> actionButtonsList_Node = new ArrayList<>();
        actionButtonsList_Node.addAll(simpleVbox.getChildren());
        actionButtonsList_Node.addAll(advancedVbox.getChildren());
        actionButtonsList_Node.addAll(nerdVbox.getChildren());

        actionButtons = new ArrayList<>();
        for (Node b : actionButtonsList_Node) {
            if (b instanceof Button) {
                actionButtons.add((Button) b);
            }
        }

        // setting action on digit buttons depending on their value
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

        // setting a method disabling a coresponding action button
        for (Button b : actionButtons) {
            b.setOnAction((event) -> {
                prepareFirstNumber(b);
                System.out.println(firstNumber);
                System.out.println(secondNumber);
            });
        }

        // a little visual tip when we can calculate value
        inputField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (firstNumber != 0 && !"".equals(inputField.getText())) {
                calculateBut.setStyle("-fx-background-color:LIME");
            } else {
                calculateBut.setStyle(defaultStyle);
            }
        });
    }

    // useful method for clearing our textfield togglebutton and stored variables
    @FXML
    private void clearInput() {
        inputField.clear();
        firstNumber = 0;
        secondNumber = 0;
        for (Button b : actionButtons) {
            b.setDisable(false);
        }
    }

    // final method for computing output
    @FXML
    private void countNumber() {
        if (!inputField.getText().isEmpty()) {
            secondNumber = Double.parseDouble(inputField.getText());
            for (Button b : actionButtons) {
                if (b.isDisabled()) {
                    detectOperation(b);
                }
            }
        }

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

    // method preparing our first number
    // the type of operation will be based on which button is disabled
    private void prepareFirstNumber(Button b) {
        if (!inputField.getText().isEmpty()) {
            firstNumber = Double.parseDouble(inputField.getText());
            inputField.clear();
            for (Button but : actionButtons) {
                but.setDisable(false);
            }
            b.setDisable(true);
        }
    }

    // method to determine what mathematical operation should be used
    private void detectOperation(Button b) {
        String buttonId = b.getId();
        double result = 0;

        switch (buttonId) {
            case "addBut":
                result = firstNumber + secondNumber;
                break;
            case "subBut":
                result = firstNumber - secondNumber;
                break;
            case "multiBut":
                result = firstNumber * secondNumber;
                break;
            case "divBut":
                if (secondNumber != 0) {
                    result = firstNumber / secondNumber;
                } else {
                    classLogger.log(Level.SEVERE, "divided by 0");
                }
                break;
        }
        if (buttonId.equals("divBut") && secondNumber == 0) {
            inputField.setText("DIVISION BY ZERO");
            calculateBut.setStyle("-fx-background-color:RED");
        } else {
            inputField.setText(result + "");
        }

    }
}
