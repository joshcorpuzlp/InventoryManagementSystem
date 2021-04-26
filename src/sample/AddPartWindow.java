package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
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

    //needs a static variable that will hold the observablelist inside the part table view
    private static ObservableList<Parts> dataHolder;


    //Create a method that will add a new part to the tableview with the addpart window
    public void saveNewPart(ActionEvent actionEvent) throws IOException {
        int IDInput = Integer.parseInt(partIDField.getText());
        int inventoryInput = Integer.parseInt(inventoryLevelField.getText());
        double priceInput = Double.parseDouble(priceField.getText());
        int machineIDInput = Integer.parseInt(machineIDField.getText());

        Parts newPart = new inHousePart(IDInput, nameField.getText(), inventoryInput, priceInput, machineIDInput);
        newPart.setMaxInventory(Integer.parseInt(maxField.getText()));
        newPart.setMinInventory(Integer.parseInt(minField.getText()));

        //dataHolder is a reference to the ObservableList of Parts from the Controller.java
        dataHolder.add(newPart);

        //after adding the new part, we need to go back to the main controller
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene mainControllerScene = new Scene(root);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainControllerScene);
        window.show();

    }

    //Method called after initialization inside  to receive the data from the main controller
    public static void receiveDataset(ObservableList<Parts> currentData) {
        dataHolder = currentData;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //

    }
}
