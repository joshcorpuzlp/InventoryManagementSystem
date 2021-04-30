package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import model.Inventory;
import model.Part;
import model.Product;
import model.Utility;


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
    @FXML private TableColumn<Part, String> partIDColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, String> partInventoryLevelColumn;
    @FXML private TableColumn<Part, String> partPriceColumn;

    @FXML private TableView associatedPartsTableView;
    @FXML private TableColumn<Part, String> associatedPartIDColumn;
    @FXML private TableColumn<Part, String> associatedPartNameColumn;
    @FXML private TableColumn<Part, String> associatedPartInventoryLevelColumn;
    @FXML private TableColumn<Part, String> associatedPartPriceColumn;

    private Product currentProduct;
    private ObservableList<Part> associatedPartTableViewHolder = FXCollections.observableArrayList();

    //configure the partSearchField
    @FXML TextField partSearchField;

    //configure the associatedPartSearchField
    @FXML TextField associatedPartSearchField;

    //Method that uses an ActionEvent(button press) to show the mainMenu.fxm


    public void cancelButtonPressed(ActionEvent actionEvent) throws IOException {
        mainMenuWindow.returnToMainMenu(actionEvent);
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

        //calls the alert confirmationMessage and stores the response to determine whether program should continue
        boolean confirmationMessageResponse = Utility.saveConfirmationMessage();

        if (confirmationMessageResponse) {
            //creates a new Product object with identifier currentProduct
            currentProduct = new Product(productNameInput, productInventoryLevel, productPriceInput, maxInventoryLevel, minInventoryLevel);

            //passes currentProduct as the argument for the .addMethod.
            Inventory.getAllProducts().add(currentProduct);

            //utilizes the associatedPartsTableviewHolder wiht a for loop to pass each element as an argument
            //for the .setAssociatedParts method.
            for (Part part : associatedPartTableViewHolder) {
                currentProduct.setAssociatedParts(part);
            }

            //calls the returnToMainMen() method.
            mainMenuWindow.returnToMainMenu(actionEvent);
        }

        else {
            return;
        }


    }



    //method that adds a selected part from the Parts table view into a holder of AssociatedParts
    public void addAssociatedPart(ActionEvent actionEvent) {
        Part selectedAssociatedPart;
        selectedAssociatedPart = (Part) partsTableView.getSelectionModel().getSelectedItem();
        associatedPartTableViewHolder.add(selectedAssociatedPart);

        associatedPartsTableView.setItems(associatedPartTableViewHolder);
    }

    //method to remove a selected part from the associatedPartsTableView
    public void removeAssociatedPart(ActionEvent actionEvent) {
        Part selectedAssociatedPart;
        selectedAssociatedPart = (Part) partsTableView.getSelectionModel().getSelectedItem();
        associatedPartTableViewHolder.remove(selectedAssociatedPart);

        associatedPartsTableView.setItems(associatedPartTableViewHolder);
    }

    //handler that triggers the searchByPartName method
    public void partSearchFieldTrigger(ActionEvent actionEvent) {
        String searchInput = partSearchField.getText();

        ObservableList<Part> foundParts = searchByPartName(searchInput);
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

        ObservableList<Part> foundParts = searchByPartName(searchInput);
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
        partIDColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("partName"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("partInventoryLevel"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("price"));

        //set PartsTable to allow edits
        partsTableView.setEditable(true);
        partsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        partsTableView.setItems(Inventory.getAllParts());

        //initialize the AssociatedPartsTableView
        associatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("partName"));
        associatedPartInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("partInventoryLevel"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("price"));

        //set associatedPartsTable to allow edits
        associatedPartsTableView.setEditable(true);
        associatedPartsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


    }
}
