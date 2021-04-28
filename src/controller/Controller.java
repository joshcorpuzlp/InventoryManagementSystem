package controller;

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
import model.Inventory;
import model.Parts;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import static model.Inventory.*;

public class Controller implements Initializable {

    //these will configure the PartsTableView
    @FXML private TableView<Parts> partsTableView;

    @FXML private TableColumn<Parts, String> partIDColumn;
    @FXML private TableColumn<Parts, String> partNameColumn;
    @FXML private TableColumn<Parts, String> inventoryLevelColumn;
    @FXML private TableColumn<Parts, String> priceColumn;

    //configure the Add Part button
    @FXML private Button AddPartButton;


    //configure the search field
    @FXML private TextField searchField;



    /**
     * Constuctor
     */
    public Controller(){
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //initialize the PartsTableView
        partIDColumn.setCellValueFactory(new PropertyValueFactory<Parts, String>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Parts, String>("partName"));
        inventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Parts, String>("partInventoryLevel"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Parts, String>("price"));

        //set PartsTable to allow edits
        partsTableView.setEditable(true);
        partsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //calls the initializeDataSet to add initialData to the private static ObservableList allParts
        initializeDataSet();
        //sets a flag variable to false so it does not run any initialization code and diverts to the else block of the initialize dataset
        disableInitializeDataSet();
        partsTableView.setItems(Inventory.getAllParts());

    }



    //Opens that addPartWindow
    public void addPartButtonPressed(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("../view/AddPartWindow.fxml"));
        Scene addPartWindowScene = new Scene(root);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(addPartWindowScene);
        window.show();
    }

    //selects the item from the partsTableView and then deletes
    public void deletePartButton(ActionEvent actionEvent) {
        int selectedIndex;
        selectedIndex = partsTableView.getSelectionModel().getFocusedIndex();
        getAllParts().remove(selectedIndex);
    }

    //method to retrieve the selected Parts object from the table view then calls the modifyPart button
    public void modifyPartButton(ActionEvent actionEvent) throws IOException {
        int selectedIndex;
        selectedIndex = partsTableView.getSelectionModel().getFocusedIndex();

        ModifyPartWindow.setPartIndexNumber(selectedIndex);

        Parent root = FXMLLoader.load(getClass().getResource("../view/ModifyPartWindow.fxml"));
        Scene modifyPartWindowScene = new Scene(root);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(modifyPartWindowScene);
        window.show();

    }


    //handler that triggers the searchByPartName method
    public void searchFieldTrigger(ActionEvent actionEvent) {
        String searchInput = searchField.getText();

        ObservableList<Parts> foundParts = searchByPartName(searchInput);
        partsTableView.setItems(foundParts);
        searchField.setText("");

    }

}
