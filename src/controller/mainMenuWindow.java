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
import model.Part;
import model.Product;
import model.Utility;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.Inventory.*;

public class mainMenuWindow implements Initializable {

    //these will configure the PartsTableView
    @FXML private TableView<Part> partsTableView;

    @FXML private TableColumn<Part, String> partIDColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, String> inventoryLevelColumn;
    @FXML private TableColumn<Part, String> priceColumn;

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
        partIDColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("partName"));
        inventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("partInventoryLevel"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("price"));

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
        Part selectedPart;
        selectedPart = partsTableView.getSelectionModel().getSelectedItem();


        //alert message
        if (partsTableView.getSelectionModel().isEmpty()) {
            Utility.noItemToDeleteMessage();
        }
        else {
            if (getAllParts().size() > 1) {
                boolean confirmationResponse = Utility.deleteConfirmationMessage();
                if (confirmationResponse) {
                    deletePart(selectedPart);
                }
            }
            else {
                //cannot delete the last part
                Utility.lastItemDeleteMessage();
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


    //handler that triggers the lookUpPart method
    public void partSearchFieldTrigger(ActionEvent actionEvent) {
        String searchInput = partSearchField.getText();

        ObservableList<Part> foundParts = lookUpPart(searchInput);
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

    //handler that triggers the lookUpPart method
    public void productSearchFieldTrigger(ActionEvent actionEvent) {
        String searchInput = productSearchField.getText();

        ObservableList<Product> foundProducts = lookUpProduct(searchInput);
        productTableView.setItems(foundProducts);

        //shows alert message if searchinput produced 0 results.
        if (productTableView.getItems().size() == 0) {
            Utility.searchProducedNoResults(searchInput);
        }
        productSearchField.setText("");

    }

    //selects the item from the partsTableView and then deletes
    public void deleteProductButton(ActionEvent actionEvent) throws IOException {
        Product selectedProduct;
        selectedProduct = productTableView.getSelectionModel().getSelectedItem();

        //checks whether the selected product has an associated part.
        if (selectedProduct.getAssociatedParts().size() > 0) {
            Utility.productHasAssociatedPartMessage();
        }
        else {
            //alert message
            if (productTableView.getSelectionModel().isEmpty()) {
                Utility.noItemToDeleteMessage();
            }
            else {
                if (getAllProducts().size() > 1) {
                    //confirm deletion
                    boolean confirmDeleteResponse = Utility.deleteConfirmationMessage();
                    if (confirmDeleteResponse) {
                        deleteProduct(selectedProduct);
                    }
                }
                else {
                    //cannot delete the last part
                    Utility.lastItemDeleteMessage();
                }
            }
        }

    }

    //method triggered by addProduct Button
    public void addProductButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/addProductWindow.fxml"));
        Scene addPartWindowScene = new Scene(root);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(addPartWindowScene);
        window.show();
    }

    //method to retrieve the selected Product object from the table view then calls the modifyPart button
    public void modifyProductButton(ActionEvent actionEvent) throws IOException {
        int selectedIndex;
        selectedIndex = productTableView.getSelectionModel().getFocusedIndex();

        ModifyProductWindow.setProductIndexNumber(selectedIndex);

        Parent root = FXMLLoader.load(getClass().getResource("../view/ModifyProductWindow.fxml"));
        Scene modifyPartWindowScene = new Scene(root);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(modifyPartWindowScene);
        window.show();

    }

    //method to return to main menu, created to be reusable in other controllers.
    public static void returnToMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(mainMenuWindow.class.getResource("../view/mainMenu.fxml"));
        Scene mainControllerScene = new Scene(root);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainControllerScene);
        window.show();
    }


}
