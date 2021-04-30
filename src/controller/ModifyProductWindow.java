package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Inventory;
import model.Part;
import model.Product;
import model.Utility;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.Inventory.lookUpPart;

public class ModifyProductWindow implements Initializable{

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

    //configure variables to determine current Product object and to hold associatedparts to be added to the Product object
    private Product currentProduct;
    private ObservableList<Part> associatedPartTableViewHolder = FXCollections.observableArrayList();

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

    //configure error message label
    @FXML private Label errorMessageLabel;
    private String errorMessageContainer = "";
    boolean isInputValid = true;

    //variables to hold textField inputs
    private String productNameInput;
    private int productInventoryLevel, maxInventoryLevelInput, minInventoryLevelInput;
    private double productPriceInput;


    /**
     * Handles the cancel button. Returns the mainMenuWindow.
     * @param actionEvent configured as the cancel button pressed
     * @throws IOException
     */
    public void cancelButtonPressed(ActionEvent actionEvent) throws IOException {
        mainMenuWindow.returnToMainMenu(actionEvent);
    }

    /**
     * Method validates the user inputs of the TextFields.
     * @param actionEvent configured to be run on when save button is pressed.
     */
    public void validateInputs(ActionEvent actionEvent) {
        //retrieve the inputs
        try {
            productNameInput = productNameField.getText();
            if (productNameInput.equals("")) {
                throw new myExceptions("Product Name: Enter a string.\n");
            }
        }
        catch (myExceptions ex) {
            errorMessageContainer += ex.getMessage();
            isInputValid = false;
        }

        try {
            productInventoryLevel = Integer.parseInt(inventoryLevelField.getText());
            if (inventoryLevelField.getText().equals("")) {
                throw new myExceptions("Inventory level: enter a number greater than 0.\n");
            }
            if (productInventoryLevel <= 0) {
                throw new myExceptions("Inventory level: enter a number greater than 0.\n");
            }
        }
        catch (NumberFormatException ex) {
            errorMessageContainer += "Inventory level: enter a positive whole number.\n";
            isInputValid = false;

        }
        catch (myExceptions stockValidation) {
            errorMessageContainer += stockValidation.getMessage();
            isInputValid = false;

        }

        try {
            productPriceInput = Double.parseDouble(productPriceField.getText());
            if (productPriceField.getText().equals("")) {
                throw new myExceptions("Price field: enter a value..\n");
            }
            if (productPriceInput <= 0) {
                throw new myExceptions("Price field: enter a value greater than 0.\n");
            }
        }
        catch (myExceptions priceValidation) {
            errorMessageContainer += priceValidation.getMessage();
            isInputValid = false;

        }
        catch (NumberFormatException ex) {
            errorMessageContainer += "Price field: enter a positive number. Your input can contain decimals. \n";
            isInputValid = false;

        }

        try {
            maxInventoryLevelInput = Integer.parseInt(maxInventoryField.getText());
            minInventoryLevelInput = Integer.parseInt(minInventoryField.getText());
            if (maxInventoryField.getText().equals("") || minInventoryField.getText().equals("") || maxInventoryLevelInput <= 0 || minInventoryLevelInput <= 0) {
                throw new myExceptions("Min and Max fields: enter values for the minimum and maximum inventory fields.\n");
            }
            if (maxInventoryLevelInput < minInventoryLevelInput) {
                throw new myExceptions("Max inventory MUST be larger than the minimum inventory.\n");
            }

        }
        catch (NumberFormatException ex) {
            errorMessageContainer += "Min and Max fields: enter a positive whole number.\n";
            isInputValid = false;

        }
        catch (myExceptions minMaxValidation) {
            errorMessageContainer += minMaxValidation.getMessage();
            isInputValid = false;

        }

        errorMessageLabel.setText(errorMessageContainer);
        errorMessageContainer = "";
    }

    /**
     * Method first calls the validateInputs() method and if inputs are validated, it will save the changes made by the user.
     * @param actionEvent configured to run when saved button is pressed.
     * @throws IOException
     */
    public void saveButtonPressed(ActionEvent actionEvent) throws IOException{

        validateInputs(actionEvent);
        Product modifiedProduct = new Product(productNameInput, productInventoryLevel, productPriceInput, maxInventoryLevelInput, minInventoryLevelInput);

        if (isInputValid) {
            Inventory.updateProduct(getProductIndexNumber(), modifiedProduct);

            //calls on a temporaryHolder of associated views to add and uses an enhanced for loop to add the contents to the current product
            modifiedProduct.getAssociatedParts().removeAll();
            for (Part part : associatedPartTableViewHolder) {
                modifiedProduct.addAssociatedPart(part);
            }
            //returns to mainMenuWindow
            mainMenuWindow.returnToMainMenu(actionEvent);
        }
        else {
            isInputValid = true;
        }
    }

    /**
     * Method that adds a selected part from the Parts table view into a holder of AssociatedParts
     * @param actionEvent configured to run when the add button is pressed.
     */
    public void addAssociatedPart(ActionEvent actionEvent) {
        Part selectedAssociatedPart;
        selectedAssociatedPart = (Part) partsTableView.getSelectionModel().getSelectedItem();
        associatedPartTableViewHolder.add(selectedAssociatedPart);

        associatedPartsTableView.setItems(associatedPartTableViewHolder);
    }

    /**
     * Method that removes a selected part from the associatedPartsTableView
     * @param actionEvent configured to run when the remove button is pressed.
     */
    public void removeAssociatedPart(ActionEvent actionEvent) {
        Part selectedAssociatedPart;
        selectedAssociatedPart = (Part) partsTableView.getSelectionModel().getSelectedItem();
        associatedPartTableViewHolder.remove(selectedAssociatedPart);

        associatedPartsTableView.setItems(associatedPartTableViewHolder);
    }

    /**
     * handler that triggers the lookUpPart method using the value within the search field as input
     * @param actionEvent configured to run after the user completes search input and presses enter.
     */
    public void partSearchFieldTrigger(ActionEvent actionEvent) {
        String searchInput = partSearchField.getText();

        ObservableList<Part> foundParts = lookUpPart(searchInput);
        partsTableView.setItems(foundParts);

        //shows alert message if searchInput produced 0 results.
        if (partsTableView.getItems().size() == 0) {
            Utility.searchProducedNoResults(searchInput);
        }
        partSearchField.setText("");

    }
    /**
     * handler that triggers the lookUpPart method using the value within the search field as input
     * @param actionEvent configured to run after the user completes search input and presses enter.
     */
    public void associatePartSearchFieldTrigger(ActionEvent actionEvent) {
        String searchInput = associatedPartSearchField.getText();

        ObservableList<Part> foundParts = lookUpPart(searchInput);
        associatedPartsTableView.setItems(foundParts);

        //shows alert message if searchInput produced 0 results.
        if (associatedPartsTableView.getItems().size() == 0) {
            Utility.searchProducedNoResults(searchInput);
        }
        associatedPartSearchField.setText("");

    }


    /**
     * Initializes the elements within the Modify Product Window.
     * @param url
     * @param rb
     */
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

        errorMessageLabel.setText("");
    }

}
