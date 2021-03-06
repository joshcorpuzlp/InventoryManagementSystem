package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ModifyPartWindow implements Initializable {
    //configure the fields in Add Part Window
    @FXML
    private TextField partIDField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField inventoryLevelField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField minField;
    @FXML
    private TextField machineIDField;
    @FXML
    private Label errorMessageLabel;


    //configure the radio buttons
    @FXML
    private ToggleGroup partSourceGroup;
    @FXML
    private RadioButton inHouseButton;
    @FXML
    private RadioButton outSourcedButton;
    //configure the MachineID or Source Company to change labels
    @FXML
    private Label inHouseOutSourcedPrompt;
    private boolean isPartInHouse;

    //configure variable and methods on how to set and return the index number from the partsTableView
    private static int partIndexNumber;
    public static void setPartIndexNumber(int indexNumber) {
        partIndexNumber = indexNumber;
    }
    public static int getPartIndexNumber() {
        return partIndexNumber;
    }

    private String partNameInput;
    private int inventoryInput;
    private double priceInput;
    private int maxInventoryLevelInput;
    private int minInventoryLevelInput;
    private int machineIDInput;
    private String companyNameInput;


    private boolean isInputValid = true;
    private String errorMessageContainer = "";

    /**
     * Method validates the user inputs of the TextFields.
     * @param actionEvent configured to be run on when save button is pressed.
     * @throws IOException
     */
    public void inputValidation(ActionEvent actionEvent) throws  IOException {
        //retrieve the inputs
        try {
            partNameInput = nameField.getText();
            if (partNameInput.equals("")) {
                throw new myExceptions("Part Name: Enter a string.\n");
            }
        } catch (myExceptions ex) {
            errorMessageContainer += ex.getMessage();
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
        } catch (myExceptions priceValidation) {
            errorMessageContainer += priceValidation.getMessage();
            isInputValid = false;

        } catch (NumberFormatException ex) {
            errorMessageContainer += "Price field: enter a positive number. Your input can contain decimals. \n";
            isInputValid = false;

        }

        try {
            maxInventoryLevelInput = Integer.parseInt(maxField.getText());
            minInventoryLevelInput = Integer.parseInt(minField.getText());
            if (maxField.getText().equals("") || minField.getText().equals("") || maxInventoryLevelInput <= 0 || minInventoryLevelInput <= 0) {
                throw new myExceptions("Min and Max fields: enter values for the minimum and maximum inventory fields.\n");
            }
            if (maxInventoryLevelInput < minInventoryLevelInput) {
                throw new myExceptions("Max inventory MUST be larger than the minimum inventory.\n");
            }


        } catch (NumberFormatException ex) {
            errorMessageContainer += "Min and Max fields: enter a positive whole number.\n";
            isInputValid = false;

        } catch (myExceptions minMaxValidation) {
            errorMessageContainer += minMaxValidation.getMessage();
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
            if (inventoryInput > maxInventoryLevelInput || inventoryInput < minInventoryLevelInput) {
                throw new myExceptions("Inventory field: value must be between min and max.\n");
            }
        } catch (NumberFormatException ex) {
            errorMessageContainer += "Inventory field: enter a positive whole number.\n";
            isInputValid = false;

        } catch (myExceptions stockValidation) {
            errorMessageContainer += stockValidation.getMessage();
            isInputValid = false;

        }

        if (isPartInHouse) {
            try {
                machineIDInput = Integer.parseInt(machineIDField.getText());
                if (machineIDField.getText().equals("")) {
                    throw new myExceptions("Machine ID: Enter machine ID number.\n");
                }
            } catch (NumberFormatException ex) {
                errorMessageContainer += "Machine ID: Enter a machine ID number.\n";
                isInputValid = false;
            } catch (myExceptions machineIDValidation) {
                errorMessageContainer += machineIDValidation.getMessage();
                isInputValid = false;
            }
        }
        if (!isPartInHouse) {
            try {
                companyNameInput = machineIDField.getText();
                if (machineIDField.getText().equals("")) {
                    throw new myExceptions("Enter the source company.\n");
                }

            } catch (myExceptions companyNameValidation) {
                errorMessageContainer += companyNameValidation.getMessage();
            }
        }
        errorMessageLabel.setText(errorMessageContainer);
        errorMessageContainer = "";
    }

    /**
     * Method first calls the validateInputs() method and if inputs are validated, it will save the changes made by the user.
     * @param actionEvent configured to run when saved button is pressed.
     * @throws IOException
     */
    public void saveModifications(ActionEvent actionEvent) throws IOException{
        //declaring variables to hold inputs, redundant but done to maintain readability
        Part updatedInfo;

        //call on inputValidation method that will verify if inputs in the textFields are ok.
        inputValidation(actionEvent);

            //conditional statements that decide which type of class instantiation will occur
            if (isPartInHouse && isInputValid) {
                updatedInfo = new inHousePart(partNameInput, inventoryInput, priceInput, maxInventoryLevelInput, minInventoryLevelInput, machineIDInput);

                //updates a specific element with the updatedInfo
                Inventory.updatePart(getPartIndexNumber(), updatedInfo);
                //return to mainMenuWindow
                mainMenuWindow.returnToMainMenu(actionEvent);
            }

            else if (!(isPartInHouse) && isInputValid) {
                updatedInfo = new outsourcePart(partNameInput, inventoryInput, priceInput, maxInventoryLevelInput, minInventoryLevelInput, companyNameInput);

                //updates a specific element with the updatedInfo
                Inventory.updatePart(getPartIndexNumber(), updatedInfo);
                //return to mainMenuWindow
                mainMenuWindow.returnToMainMenu(actionEvent);
            }
            else {
                isInputValid = true;
            }
    }



    /**
     * Handles the cancel button. Returns the mainMenuWindow.
     * @param actionEvent configured as the cancel button pressed
     * @throws IOException
     */
    public void cancelModificationPart(ActionEvent actionEvent) throws IOException {
        mainMenuWindow.returnToMainMenu(actionEvent);
    }

    /**
     * Method that determines whether the addPartWindow will add an inHouse Part or outSourced Part class.
     *
     */
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

    /**
     * Initializes the elements within the Add Part Window.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partIDField.setEditable(false);
        partIDField.setText("Auto-generated");
        partIDField.setDisable(true);

        //these will initialize the radio buttons to exist in a partSourceGroup object.
        partSourceGroup = new ToggleGroup();
        this.inHouseButton.setToggleGroup(partSourceGroup);
        this.outSourcedButton.setToggleGroup(partSourceGroup);


        //initialize text fields with the selected part identified by the index
        Part selectedPart = Inventory.getAllParts().get(getPartIndexNumber());
        if (selectedPart.getClass() == inHousePart.class) {
            nameField.setText(selectedPart.getPartName());
            inventoryLevelField.setText(Integer.toString(selectedPart.getPartInventoryLevel()));
            priceField.setText(Double.toString(selectedPart.getPrice()));
            maxField.setText(Integer.toString(selectedPart.getMaxInventory()));
            minField.setText(Integer.toString(selectedPart.getMinInventory()));
            machineIDField.setText(Integer.toString(((inHousePart) selectedPart).getMachineID()));
            partSourceGroup.selectToggle(inHouseButton);
            inHouseOutSource();

        }
        if (selectedPart.getClass() == outsourcePart.class) {
            nameField.setText(selectedPart.getPartName());
            inventoryLevelField.setText(Integer.toString(selectedPart.getPartInventoryLevel()));
            priceField.setText(Double.toString(selectedPart.getPrice()));
            maxField.setText(Integer.toString(selectedPart.getMaxInventory()));
            minField.setText(Integer.toString(selectedPart.getMinInventory()));
            machineIDField.setText(((outsourcePart) selectedPart).getCompanyName());
            partSourceGroup.selectToggle(outSourcedButton);
            inHouseOutSource();

        }

        errorMessageLabel.setText("");
    }
}