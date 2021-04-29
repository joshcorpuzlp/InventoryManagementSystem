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

public class ModifyProductWindow implements Initializable{

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

    //configure variables to determine current Product object and to hold associatedparts to be added to the Product object
    private Product currentProduct;
    private ObservableList<Parts> associatedPartsTableViewHolder = FXCollections.observableArrayList();

    //configure variable and methods on how to set and return the index number from the productTableView
    private static int productIndexNumber;
    public static void setProductIndexNumber(int indexNumber) {
        productIndexNumber = indexNumber;
    }
    public static int getProductIndexNumber() {
        return productIndexNumber;
    }

    //configure the partSearchField
    @FXML TextField partSearchField;

    //configure the associatedPartSearchField
    @FXML TextField associatedPartSearchField;


    public void cancelButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/mainMenu.fxml"));
        Scene addPartWindowScene = new Scene(root);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(addPartWindowScene);
        window.show();
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

        //edits the currentProduct
        currentProduct.setProductName(productNameInput);
        currentProduct.setProductStock(productInventoryLevel);
        currentProduct.setProductPrice(productPriceInput);
        currentProduct.setProductMaxInventory(maxInventoryLevel);
        currentProduct.setProductMinInventory(minInventoryLevel);

        //adds the inputs into the Inventory ObservableList of Products
//        Inventory.getAllProducts().add(currentProduct);

        //calls on a temporaryHolder of associated views to add and uses an enhanced for loop to add the contents to the current product
        currentProduct.getAssociatedParts().removeAll();
        for (Parts part : associatedPartsTableViewHolder) {
            currentProduct.setAssociatedParts(part);
        }


        //returns to mainMenuWindow
        Parent root = FXMLLoader.load(getClass().getResource("../view/mainMenu.fxml"));
        Scene mainControllerScene = new Scene(root);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainControllerScene);
        window.show();

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

        //initialize the currentProduct to refer to the selected product
        currentProduct = Inventory.getAllProducts().get(productIndexNumber);
        //initialize the associatedPartsTableView to show the currentProduct's associatedParts
        associatedPartsTableView.setItems(currentProduct.getAssociatedParts());

        //initialize TextFields to show the current fields from the currentProduct
        productIDField.setText("Auto-generated");
        productIDField.setEditable(false);
        productIDField.setDisable(true);
        productNameField.setText(currentProduct.getProductName());
        inventoryLevelField.setText(Integer.toString(currentProduct.getProductStock()));
        productPriceField.setText(Double.toString(currentProduct.getProductPrice()));
        maxInventoryField.setText(Integer.toString(currentProduct.getProductMaxInventory()));
        minInventoryField.setText(Integer.toString(currentProduct.getProductMinInventory()));

    }

}
