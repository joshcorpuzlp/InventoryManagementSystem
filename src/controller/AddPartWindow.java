package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.InputEvent;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Inventory;
import model.Utility;
import model.inHousePart;
import model.outsourcePart;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
    private boolean isPartInHouse = false;


    //Create a method that will add a new part to the tableview with the addPart window
    public void saveNewPart(ActionEvent actionEvent) throws IOException {
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


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Save new part");
        alert.setHeaderText("Saving new part");
        alert.setContentText("Are you sure you want to save " + partNameInput + "?");
        Optional<ButtonType> result = alert.showAndWait();

        //create a conditional statements to decide whether to use machineIDInput or companyName input
        if (isPartInHouse && (result.get() == ButtonType.OK)) {
            int machineIDInput = Integer.parseInt(machineIDField.getText());
            Inventory.getAllParts().add(new inHousePart(partNameInput, inventoryInput, priceInput, maxInventoryLevelInput, minInventoryLevelInput, machineIDInput));
        }

        else if (!(isPartInHouse) && result.get() == ButtonType.OK){
            String companyNameInput = machineIDField.getText();
            Inventory.getAllParts().add(new outsourcePart(partNameInput, inventoryInput, priceInput, maxInventoryLevelInput, maxInventoryLevelInput, companyNameInput));
        }

        //after adding the new part, we need to go back to the main controller
        mainMenuWindow.returnToMainMenu(actionEvent);
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

    }
}
