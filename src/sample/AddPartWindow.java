package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Parts;
import model.inHousePart;
import model.outsourcePart;

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

    //needs a static variable that will hold the observablelist inside the part table view
    private static ObservableList<Parts> dataHolder;

    //configure the radio buttons
    @FXML private ToggleGroup partSourceGroup;
    @FXML private RadioButton inHouseButton;
    @FXML private RadioButton outSourcedButton;
    //configure the MachineID or Source Company to change labels
    @FXML private Label inHouseOutSourcedPrompt;
    private boolean isPartInHouse = false;


    //Create a method that will add a new part to the tableview with the addpart window
    public void saveNewPart(ActionEvent actionEvent) throws IOException {
        boolean validInput = false;
        String partNameInput;
        int inventoryInput;
        double priceInput;
        int maxInventoryLevelInput;
        int minInventoryLevelInput;

        String errorMessage = "";
        partNameInput = nameField.getText();

        //error handling for the inputs THIS SHOULD BE MOVED to ITS OWN METHOD OR METHODS!!!!
        try {
            inventoryInput = Integer.parseInt(inventoryLevelField.getText());
            validInput = true;
        }
        catch (Exception exception1) {
            errorMessage += "Please enter a valid integer for inventory Level\n";
            inventoryLevelField.setText("");
            return;
        }

        try {
            priceInput = Double.parseDouble(priceField.getText());
            validInput = true;
        }
        catch (Exception exception) {
            errorMessage += "Please enter a valid integer for inventory Level\n";
            priceField.setText("");
            return;
        }

        try {
            maxInventoryLevelInput = Integer.parseInt(maxField.getText());
            validInput = true;
        }
        catch (Exception exception) {
            errorMessage += "Please enter a valid integer for inventory Level\n";
            maxField.setText("");
            return;
        }
        try {
            minInventoryLevelInput = Integer.parseInt(minField.getText());
            validInput = true;
        }
        catch (Exception exception) {
            errorMessage += "Please enter a valid integer for inventory Level";
            maxField.setText("");
            return;
        }

        errorMessageLabel.setText(errorMessage);
        //create a conditional statement to decide whether to use machineIDInput or companyName input
        if (isPartInHouse && validInput) {
            int machineIDInput = Integer.parseInt(machineIDField.getText());

            Parts newPart = new inHousePart(partNameInput, inventoryInput, priceInput, machineIDInput);
            newPart.setMaxInventory(maxInventoryLevelInput);
            newPart.setMinInventory(minInventoryLevelInput);
            //dataHolder is a reference to the ObservableList of Parts from the Controller.java
            dataHolder.add(newPart);

        }
        else if (!isPartInHouse && validInput) {
            String companyNameInput = machineIDField.getText();

            Parts newPart = new outsourcePart(partNameInput, inventoryInput, priceInput,companyNameInput);
            newPart.setMaxInventory(maxInventoryLevelInput);
            newPart.setMinInventory(minInventoryLevelInput);
            //dataHolder is a reference to the ObservableList of Parts from the Controller.java
            dataHolder.add(newPart);

        }

        //after adding the new part, we need to go back to the main controller
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene mainControllerScene = new Scene(root);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainControllerScene);
        window.show();
    }



    //create a method for the cancel button -- go back to the main menu without saving
    public void cancelNewPart(ActionEvent actionEvent) throws  IOException{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene mainControllerScene = new Scene(root);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainControllerScene);
        window.show();
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


    //Method called after initialization inside the MainController to retrieve the data so that it can be manipulated in the AddPartWindow
    public static void receiveDataset(ObservableList<Parts> currentData) {
        dataHolder = currentData;
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
