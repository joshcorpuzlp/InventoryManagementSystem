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
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Inventory;
import model.Parts;
import model.Product;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.Inventory.searchByPartName;


public class AddProductWindow implements Initializable {
    @FXML private TextField productIDField;
    @FXML private TextField productNameField;
    @FXML private TextField inventoryLevelField;
    @FXML private TextField productPriceField;
    @FXML private TextField maxInventoryField;
    @FXML private TextField minInventoryField;

    @FXML private TableView partsTableView;
    @FXML private TableColumn<Parts, String> partIDColumn;
    @FXML private TableColumn<Parts, String> partNameColumn;
    @FXML private TableColumn<Parts, String> partInventoryLevelColumn;
    @FXML private TableColumn<Parts, String> partPriceColumn;

    @FXML private TableView associatedPartsTableView;
    @FXML private TableColumn<Parts, String> associatedPartIDColumn;
    @FXML private TableColumn<Parts, String> associatedPartNameColumn;
    @FXML private TableColumn<Parts, String> associatedPartInventoryLevelColumn;
    @FXML private TableColumn<Parts, String> associatedPartPriceColumn;

    private Product currentProduct;
    private ObservableList<Parts> associatedPartsTableViewHolder = FXCollections.observableArrayList();

    //configure the partSearchField
    @FXML TextField partSearchField;

    //configure the associatedPartSearchField
    @FXML TextField associatedPartSearchField;

    //Method that uses an ActionEvent(button press) to show the mainMenu.fxm
    private void returnToMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/mainMenu.fxml"));
        Scene mainControllerScene = new Scene(root);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainControllerScene);
        window.show();
    }

    public void cancelButtonPressed(ActionEvent actionEvent) throws IOException {
        returnToMainMenu(actionEvent);
    }

    public void saveButtonPressed(ActionEvent actionEvent) throws IOException{
        String productNameInput;
        int productInventoryLevel, maxInventoryLevel, minInventoryLevel;
        double productPriceInput;

        //retrieve textField inputs
        productNameInput = productNameField.getText();
        productInventoryLevel = Integer.parseInt(inventoryLevelField.getText());
        productPriceInput = Double.parseDouble(productPriceField.getText());
        maxInventoryLevel = Integer.parseInt(maxInventoryField.getText());
        minInventoryLevel = Integer.parseInt(minInventoryField.getText());

        //creates a new Product object with identifier currentProduct
        currentProduct = new Product(productNameInput, productInventoryLevel, productPriceInput, maxInventoryLevel, minInventoryLevel);

        //passes currentProduct as the argument for the .addMethod.
        Inventory.getAllProducts().add(currentProduct);

        //utilizes the associatedPartsTableviewHolder wiht a for loop to pass each element as an argument
        //for the .setAssociatedParts method.
        for (Parts part : associatedPartsTableViewHolder) {
            currentProduct.setAssociatedParts(part);
        }


        //calls the returnToMainMen() method.
        returnToMainMenu(actionEvent);

    }



    //method that adds a selected part from the Parts table view into a holder of AssociatedParts
    public void addAssociatedPart(ActionEvent actionEvent) {
        Parts selectedAssociatedPart;
        selectedAssociatedPart = (Parts) partsTableView.getSelectionModel().getSelectedItem();
        associatedPartsTableViewHolder.add(selectedAssociatedPart);

        associatedPartsTableView.setItems(associatedPartsTableViewHolder);
    }

    //method to remove a selected part from the associatedPartsTableView
    public void removeAssociatedPart(ActionEvent actionEvent) {
        Parts selectedAssociatedPart;
        selectedAssociatedPart = (Parts) partsTableView.getSelectionModel().getSelectedItem();
        associatedPartsTableViewHolder.remove(selectedAssociatedPart);

        associatedPartsTableView.setItems(associatedPartsTableViewHolder);
    }

    //handler that triggers the searchByPartName method
    public void partSearchFieldTrigger(ActionEvent actionEvent) {
        String searchInput = partSearchField.getText();

        ObservableList<Parts> foundParts = searchByPartName(searchInput);
        partsTableView.setItems(foundParts);

        //shows alert message if searchInput produced 0 results.
        if (partsTableView.getItems().size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.NONE);
            alert.setTitle("Part not found");
            alert.setHeaderText("Search produced no results.");
            alert.setContentText("\"" + searchInput +"\""  +" found no results.");
            alert.showAndWait();
        }
        partSearchField.setText("");

    }
    //handler that triggers the searchByPartName method
    public void associatePartSearchFieldTrigger(ActionEvent actionEvent) {
        String searchInput = associatedPartSearchField.getText();

        ObservableList<Parts> foundParts = searchByPartName(searchInput);
        associatedPartsTableView.setItems(foundParts);

        //shows alert message if searchInput produced 0 results.
        if (partsTableView.getItems().size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.NONE);
            alert.setTitle("Part not found");
            alert.setHeaderText("Search produced no results.");
            alert.setContentText("\"" + searchInput +"\""  +" found no results.");
            alert.showAndWait();
        }
        partSearchField.setText("");

    }




    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productIDField.setEditable(false);
        productIDField.setText("Auto-generated");
        productIDField.setDisable(true);

        //initialize the PartsTableView
        partIDColumn.setCellValueFactory(new PropertyValueFactory<Parts, String>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Parts, String>("partName"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Parts, String>("partInventoryLevel"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<Parts, String>("price"));

        //set PartsTable to allow edits
        partsTableView.setEditable(true);
        partsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        partsTableView.setItems(Inventory.getAllParts());

        //initialize the AssociatedPartsTableView
        associatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<Parts, String>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<Parts, String>("partName"));
        associatedPartInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Parts, String>("partInventoryLevel"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<Parts, String>("price"));

        //set associatedPartsTable to allow edits
        associatedPartsTableView.setEditable(true);
        associatedPartsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


    }
}
