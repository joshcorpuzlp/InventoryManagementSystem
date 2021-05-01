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

    //configure currentProduct to be able to
    private Product currentProduct;
    private ObservableList<Part> associatedPartTableViewHolder = FXCollections.observableArrayList();

    //configure the partSearchField
    @FXML private TextField partSearchField;

    //configure the associatedPartSearchField
    @FXML private TextField associatedPartSearchField;

    //configure error message label
    @FXML private Label errorMessageLabel;
    private String errorMessageContainer = "";
    boolean isInputValid = true;

    //variables to hold textField inputs
    private String productNameInput;
    private int productInventoryLevel, maxInventoryLevelInput, minInventoryLevelInput;
    private double productPriceInput;

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

        try {
            productInventoryLevel = Integer.parseInt(inventoryLevelField.getText());
            if (inventoryLevelField.getText().equals("")) {
                throw new myExceptions("Inventory level: enter a number greater than 0.\n");
            }
            if (productInventoryLevel <= 0) {
                throw new myExceptions("Inventory level: enter a number greater than 0.\n");
            }
            if (productInventoryLevel > maxInventoryLevelInput || productInventoryLevel < minInventoryLevelInput) {
                throw new myExceptions("Inventory level: value must be between max and min.\n");
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

        if (isInputValid) {
            //creates a new Product object with identifier currentProduct
            currentProduct = new Product(productNameInput, productInventoryLevel, productPriceInput, maxInventoryLevelInput, minInventoryLevelInput);

            //passes currentProduct as the argument for the .addMethod.
            Inventory.getAllProducts().add(currentProduct);

            //utilizes the associatedPartsTableviewHolder wiht a for loop to pass each element as an argument
            //for the .addAssociatedPart method.
            for (Part part : associatedPartTableViewHolder) {
                currentProduct.addAssociatedPart(part);
            }

            //calls the returnToMainMen() method.
            mainMenuWindow.returnToMainMenu(actionEvent);
        }
        else {
            isInputValid = true;
        }

    }

    /**
     * Handles the cancel button. Returns the mainMenuWindow.
     * @param actionEvent configured as the cancel button pressed
     * @throws IOException
     */
    //Method that uses an ActionEvent(button press) to show the mainMenu.fxml
    public void cancelButtonPressed(ActionEvent actionEvent) throws IOException {
        mainMenuWindow.returnToMainMenu(actionEvent);
    }

    /**
     * method that adds a selected part from the Parts table view into a holder of AssociatedParts
     * @param actionEvent configured to run when Add associated part button is pressed.
     */
    public void addAssociatedPart(ActionEvent actionEvent) {
        Part selectedAssociatedPart;
        selectedAssociatedPart = (Part) partsTableView.getSelectionModel().getSelectedItem();
        associatedPartTableViewHolder.add(selectedAssociatedPart);

        associatedPartsTableView.setItems(associatedPartTableViewHolder);
    }

    /**
     * method to remove a selected part from the associatedPartsTableView
     * @param actionEvent
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
     * Initializes the elements within the Add Product Window.
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

        errorMessageLabel.setText("");
    }
}
