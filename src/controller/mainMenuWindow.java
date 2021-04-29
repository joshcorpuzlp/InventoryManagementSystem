package controller;


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
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.*;

public class mainMenuWindow implements Initializable {

    //these will configure the PartsTableView
    @FXML private TableView<Parts> partsTableView;

    @FXML private TableColumn<Parts, String> partIDColumn;
    @FXML private TableColumn<Parts, String> partNameColumn;
    @FXML private TableColumn<Parts, String> inventoryLevelColumn;
    @FXML private TableColumn<Parts, String> priceColumn;

    //these will configure ProductTableView
    @FXML private TableView<Product> productTableView;
    @FXML private TableColumn<Product, String> productIDColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, String> productStockColumn;
    @FXML private TableColumn<Product, String> productPriceColumn;


    //configure the part search field
    @FXML private TextField partSearchField;
    //configures the product search field
    @FXML private TextField productSearchField;




    /**
     * Constuctor
     */
    public mainMenuWindow(){
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

        partsTableView.setItems(Inventory.getAllParts());

        //initializes the ProductTableView
        productIDColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("productID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
        productStockColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("productStock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("productPrice"));

        //set ProductTableView to allow edits
        productTableView.setEditable(true);
        productTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //calls the initializeProductDataSet to add initial product dat to the private static ObservableList allProducts
        initializeProductDataSet();

        //adds the observablelist allProducts to the productTableView
        productTableView.setItems(Inventory.getAllProducts());



        //sets a flag variable to false so it does not run any initialization code and diverts to the else block of the initialize dataset
        disableInitializeDataSet();


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
        Parts selectedPart;
        selectedPart = partsTableView.getSelectionModel().getSelectedItem();


        //alert message
        if (partsTableView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.NONE);
            alert.setTitle("Nothing selected");
            alert.setHeaderText("Nothing selected");
            alert.setContentText("Nothing was selected to modify");
            alert.showAndWait();
        }
        else {
            if (getAllParts().size() > 1) {
                //confirm deletion
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initModality(Modality.NONE);
                alert.setTitle("Delete Part");
                alert.setHeaderText("Deleting Part");
                alert.setContentText("Are you sure you want to delete " + selectedPart.getPartName() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    deletePart(selectedPart);
                }
            }
            else {
                //cannot delete the last part
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.NONE);
                alert.setTitle("CANNOT DELETE");
                alert.setHeaderText("CANNOT DELETE");
                alert.setContentText("This is the last item! Part table cannot be empty.");
                alert.showAndWait();
            }
        }

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
    public void partSearchFieldTrigger(ActionEvent actionEvent) {
        String searchInput = partSearchField.getText();

        ObservableList<Parts> foundParts = searchByPartName(searchInput);
        partsTableView.setItems(foundParts);

        //shows alert message if searchinput produced 0 results.
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
    public void productSearchFieldTrigger(ActionEvent actionEvent) {
        String searchInput = productSearchField.getText();

        ObservableList<Product> foundProducts = searchByProductName(searchInput);
        productTableView.setItems(foundProducts);

        //shows alert message if searchinput produced 0 results.
        if (productTableView.getItems().size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.NONE);
            alert.setTitle("Product not found");
            alert.setHeaderText("Search produced no results.");
            alert.setContentText("\"" + searchInput +"\""  +" found no results.");
            alert.showAndWait();
        }
        productSearchField.setText("");

    }


}
