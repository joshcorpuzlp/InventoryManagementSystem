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

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
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

    //configure the Add Part button
    @FXML private Button AddPartButton;


    //configure the search field
    @FXML private TextField searchField;



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
        Parts selectedPart;
        selectedPart = partsTableView.getSelectionModel().getSelectedItem();

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
                Parts partToDelete = partsTableView.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initModality(Modality.NONE);
                alert.setTitle("Delete Part");
                alert.setHeaderText("Deleting Part");
                alert.setContentText("Are you sure you want to delete " + partToDelete.getPartName() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    deletePart(partToDelete);
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
    public void searchFieldTrigger(ActionEvent actionEvent) {
        String searchInput = searchField.getText();

        ObservableList<Parts> foundParts = searchByPartName(searchInput);
        partsTableView.setItems(foundParts);
        searchField.setText("");

    }

}
