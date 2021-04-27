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
import model.Parts;
import model.inHousePart;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    //these will configure the PartsTableView
    @FXML private TableView<Parts> partsTableView;
    //creates an initial observable list of Parts class
    private static ObservableList<Parts> allParts = FXCollections.observableArrayList();

    @FXML private TableColumn<Parts, String> partIDColumn;
    @FXML private TableColumn<Parts, String> partNameColumn;
    @FXML private TableColumn<Parts, String> inventoryLevelColumn;
    @FXML private TableColumn<Parts, String> priceColumn;

    //configure the Add Part button
    @FXML private Button AddPartButton;


    //configure the search field
    @FXML private TextField searchField;

    //configure boolean flag variable to prevent setTestData to run more than once
    private static boolean isFirstTime = true;


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



        //adds the Observable List of Parts class into the partsTableView
        setTestData();
        partsTableView.setItems(allParts);
    }


    private void setTestData() {

        if (!isFirstTime){
            return;
        }
        isFirstTime = false;
        allParts.add(new inHousePart("Screws", 10, 10.99, 0001));
        allParts.add(new inHousePart("Nails", 10, 10.99, 0002));
        allParts.add(new inHousePart("Hammer", 10, 10.99,0003));
    }


    public void addPartButtonPressed(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("AddPartWindow.fxml"));
        Scene addPartWindowScene = new Scene(root);

        AddPartWindow.receiveDataset(allParts);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(addPartWindowScene);
        window.show();

    }

    //handler that triggers the searchByPartName method
    public void resultsHandle(ActionEvent actionEvent) {
        String searchInput = searchField.getText();

        ObservableList<Parts> foundParts = searchByPartName(searchInput);
        partsTableView.setItems(foundParts);
        searchField.setText("");

    }
    //search method running in the background as private method
    private ObservableList<Parts> searchByPartName(String searchInput) {
        ObservableList<Parts> foundPartNames = FXCollections.observableArrayList();
        ObservableList<Parts> allParts = Controller.allParts;

        //conditional statement and boolean variable added to switch between doing an (int) ID search or a (String) partName search.
        boolean isText = true;
        if (searchInput.matches(".*\\d.*")) {
            isText = false;
        }
        if (isText) {
            for(Parts foundParts : allParts) {
                //conditional statement makes both the searchInput string and the partName String lowerCase so that it can disregard capitalization
                if (foundParts.getPartName().toLowerCase(Locale.ROOT).contains(searchField.getText().toLowerCase(Locale.ROOT))) {
                    foundPartNames.add(foundParts);
                }

                else if (foundParts.getPartName().equals("")) {
                    foundPartNames = Controller.allParts;
                }
            }
        }
        else {
            for(Parts foundParts : allParts) {
                if (foundParts.getId() == Integer.parseInt(searchField.getText())) {
                    foundPartNames.add(foundParts);
                }

                else if (foundParts.getPartName().equals("")) {
                    foundPartNames = Controller.allParts;
                }
            }
        }


        return foundPartNames;
    }



}
