package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Part;
import model.Inventory;
import model.inHousePart;
import model.outsourcePart;

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
    private boolean isPartInHouse = false;


    //configure variable and methods on how to set and return the index number from the partsTableView
    private static int partIndexNumber;
    public static void setPartIndexNumber(int indexNumber) {
        partIndexNumber = indexNumber;
    }
    public static int getPartIndexNumber() {
        return partIndexNumber;
    }


    public void saveModifications(ActionEvent actionEvent) throws IOException{
        //declaring variables to hold inputs, redundant but done to maintain readability
        Part updatedInfo;
        String partNameInput;
        int inventoryInput;
        double priceInput;
        int maxInventoryLevelInput;
        int minInventoryLevelInput;

        //retrieve the inputs
        partNameInput = nameField.getText();
        inventoryInput = Integer.parseInt(inventoryLevelField.getText());
        priceInput = Double.parseDouble(priceField.getText());
        maxInventoryLevelInput = Integer.parseInt(maxField.getText());
        minInventoryLevelInput = Integer.parseInt(minField.getText());

        if (isPartInHouse) {
            int machineIDInput = Integer.parseInt(machineIDField.getText());
            updatedInfo = new inHousePart(partNameInput, inventoryInput, priceInput, maxInventoryLevelInput, minInventoryLevelInput, machineIDInput);
        }

        else {
            String companyNameInput = machineIDField.getText();
            updatedInfo = new outsourcePart(partNameInput, inventoryInput, priceInput, maxInventoryLevelInput, maxInventoryLevelInput, companyNameInput);
        }


        Inventory.updatePart(getPartIndexNumber(), updatedInfo);


        //return to mainMenuWindow
        mainMenuWindow.returnToMainMenu(actionEvent);
    }



    //create a method for the cancel button -- go back to the main menu without saving part modification
    public void cancelModificationPart(ActionEvent actionEvent) throws IOException {
        mainMenuWindow.returnToMainMenu(actionEvent);
    }

    //create a method that will change the addPartWindow depending on whether the input is "inhouse" or outsourced?
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
        }
        if (selectedPart.getClass() == outsourcePart.class) {
            nameField.setText(selectedPart.getPartName());
            inventoryLevelField.setText(Integer.toString(selectedPart.getPartInventoryLevel()));
            priceField.setText(Double.toString(selectedPart.getPrice()));
            maxField.setText(Integer.toString(selectedPart.getMaxInventory()));
            minField.setText(Integer.toString(selectedPart.getMinInventory()));
            machineIDField.setText(((outsourcePart) selectedPart).getCompanyName());
            partSourceGroup.selectToggle(outSourcedButton);
        }


    }
}