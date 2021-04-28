package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Inventory;
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


    //configure the radio buttons
    @FXML private ToggleGroup partSourceGroup;
    @FXML private RadioButton inHouseButton;
    @FXML private RadioButton outSourcedButton;
    //configure the MachineID or Source Company to change labels
    @FXML private Label inHouseOutSourcedPrompt;
    private boolean isPartInHouse = false;


    //Create a method that will add a new part to the tableview with the addpart window
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

        //create a conditional statements to decide whether to use machineIDInput or companyName input
        if (isPartInHouse) {
            int machineIDInput = Integer.parseInt(machineIDField.getText());
            Inventory.getAllParts().add(new inHousePart(partNameInput, inventoryInput, priceInput, maxInventoryLevelInput, minInventoryLevelInput, machineIDInput));
        }

        else {
            String companyNameInput = machineIDField.getText();
            Inventory.getAllParts().add(new outsourcePart(partNameInput, inventoryInput, priceInput, maxInventoryLevelInput, maxInventoryLevelInput, companyNameInput));
        }

        //after adding the new part, we need to go back to the main controller
        Parent root = FXMLLoader.load(getClass().getResource("../view/mainMenu.fxml"));
        Scene mainControllerScene = new Scene(root);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainControllerScene);
        window.show();
    }



    //create a method for the cancel button -- go back to the main menu without saving
    public void cancelNewPart(ActionEvent actionEvent) throws  IOException{
        Parent root = FXMLLoader.load(getClass().getResource("../view/mainMenu.fxml"));
        Scene mainControllerScene = new Scene(root);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainControllerScene);
        window.show();
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
