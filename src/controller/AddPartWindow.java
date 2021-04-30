package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import model.*;

import java.io.EOFException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AddPartWindow implements Initializable {
    //configure the fields in Add Part Window
    @FXML private TextField partIDField;
    @FXML private TextField nameField;
    @FXML private TextField inventoryLevelField;
    @FXML private TextField priceField;
    @FXML private TextField maxField;
    @FXML private TextField minField;
    @FXML private TextField machineIDField;
    @FXML private Label errorMessageLabel;


    //configure the radio buttons
    @FXML private ToggleGroup partSourceGroup;
    @FXML private RadioButton inHouseButton;
    @FXML private RadioButton outSourcedButton;
    //configure the MachineID or Source Company to change labels
    @FXML private Label inHouseOutSourcedPrompt;

    private boolean isPartInHouse;

    String errorMessageContainer = "";
    boolean isInputValid = true;

    private String partNameInput;
    private int inventoryInput;
    private double priceInput;
    private int maxInventoryLevelInput;
    private int minInventoryLevelInput;
    private int machineIDInput;
    private String companyNameInput;

    public void inputValidation(ActionEvent actionEvent) throws  IOException {
        //retrieve the inputs
        try {
            partNameInput = nameField.getText();
            if (partNameInput.equals("")) {
                throw new myExceptions("Part Name: Enter a string.\n");
            }
        }
        catch (myExceptions ex) {
            errorMessageContainer += ex.getMessage();
            isInputValid = false;
        }

        try {
            inventoryInput = Integer.parseInt(inventoryLevelField.getText());
            if (inventoryLevelField.getText().equals("")) {
                throw new myExceptions("Inventory field: enter a number greater than 0.\n");
            }
            if (inventoryInput <= 0) {
                throw new myExceptions("Inventory field: enter a number greater than 0.\n");
            }
        }
        catch (NumberFormatException ex) {
            errorMessageContainer += "Inventory field: enter a positive whole number.\n";
            isInputValid = false;

        }
        catch (myExceptions stockValidation) {
            errorMessageContainer += stockValidation.getMessage();
            isInputValid = false;

        }

        try {
            priceInput = Double.parseDouble(priceField.getText());
            if (priceField.getText().equals("")) {
                throw new myExceptions("Price field: enter a value..\n");
            }
            if (priceInput <= 0) {
                throw new myExceptions("Price field: enter a value greater than 0.\n");
            }
        }
        catch (myExceptions priceValidation) {
            errorMessageContainer += priceValidation.getMessage();
            isInputValid = false;

        }
        catch (NumberFormatException ex) {
            errorMessageContainer += "Price field: enter a positive number. Your input can contain decimals. \n";
            isInputValid = false;

        }

        try {
            maxInventoryLevelInput = Integer.parseInt(maxField.getText());
            minInventoryLevelInput = Integer.parseInt(minField.getText());
            if (maxField.getText().equals("") || minField.getText().equals("")) {
                throw new myExceptions("Min and Max fields: enter values for the minimum and maximum inventory fields.\n");
            }
            if (maxInventoryLevelInput < minInventoryLevelInput) {
                throw new myExceptions("Max inventory MUST be larger than the minimum inventory.\n");
            }

        }
        catch (NumberFormatException ex) {
            errorMessageContainer += "Min and Max fields: enter a positive whole number.\n";
            isInputValid = false;

        }
        catch (myExceptions minMaxValidation) {
            errorMessageContainer += minMaxValidation.getMessage();
            isInputValid = false;

        }

        if (isPartInHouse) {
            try {
                machineIDInput = Integer.parseInt(machineIDField.getText());
                if (machineIDField.getText().equals("")) {
                    throw new myExceptions("Machine ID: Enter machine ID number.\n");
                }
            }
            catch (NumberFormatException ex) {
                errorMessageContainer += "Machine ID: Enter a machine ID number.\n";
                isInputValid = false;
            }
            catch (myExceptions machineIDValidation) {
                errorMessageContainer += machineIDValidation.getMessage();
                isInputValid = false;
            }
        }
        if (!isPartInHouse) {
            try{
                companyNameInput = machineIDField.getText();
                if (machineIDField.getText().equals("")) {
                    throw new myExceptions("Enter the source company.\n");
                }

            }
            catch (myExceptions companyNameValidation) {
                errorMessageContainer += companyNameValidation.getMessage();
            }
        }
        errorMessageLabel.setText(errorMessageContainer);
        errorMessageContainer = "";
    }
    //Create a method that will add a new part to the tableview with the addPart window
    public void saveNewPart(ActionEvent actionEvent) throws IOException {
        inputValidation(actionEvent);

        if (isPartInHouse && isInputValid) {
            Inventory.getAllParts().add(new inHousePart(partNameInput, inventoryInput, priceInput, maxInventoryLevelInput, minInventoryLevelInput, machineIDInput));
            //after adding the new part, we need to go back to the main controller
            mainMenuWindow.returnToMainMenu(actionEvent);
        }
        else if (!isPartInHouse && isInputValid) {
            Inventory.getAllParts().add(new outsourcePart(partNameInput, inventoryInput, priceInput, maxInventoryLevelInput, maxInventoryLevelInput, companyNameInput));
            //after adding the new part, we need to go back to the main controller
            mainMenuWindow.returnToMainMenu(actionEvent);
        }
        else {
            isInputValid = true;
        }



    }



    //create a method for the cancel button -- go back to the main menu without saving
    public void cancelNewPart(ActionEvent actionEvent) throws  IOException{
        mainMenuWindow.returnToMainMenu(actionEvent);
    }

    //create a method that will change the addPartWindow depending on whether the input is "inHouse" or outsourced?
    public void inHouseOutSource() {
        if (this.partSourceGroup.getSelectedToggle().equals(this.inHouseButton)) {
            inHouseOutSourcedPrompt.setText("Machine ID");
            isPartInHouse = true;
        }
        if (this.partSourceGroup.getSelectedToggle().equals(this.outSourcedButton)) {
            inHouseOutSourcedPrompt.setText("Company Name");
            isPartInHouse = false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partIDField.setEditable(false);
        partIDField.setText("Auto-generated");
        partIDField.setDisable(true);

        //these will initialize the radio buttons to exist in a partSourceGroup object.
        partSourceGroup = new ToggleGroup();
        this.inHouseButton.setToggleGroup(partSourceGroup);
        this.outSourcedButton.setToggleGroup(partSourceGroup);
        partSourceGroup.selectToggle(inHouseButton);
        isPartInHouse = true;

        errorMessageLabel.setText("");
    }
}
