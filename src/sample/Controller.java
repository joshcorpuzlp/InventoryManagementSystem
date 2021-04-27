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
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    //these will configure the PartsTableView
    @FXML private TableView<Parts> partsTableView;
    //creates an initial observable list of Parts class
    private static ObservableList<Parts> initialParts = FXCollections.observableArrayList();

    @FXML private TableColumn<Parts, String> partIDColumn;
    @FXML private TableColumn<Parts, String> partNameColumn;
    @FXML private TableColumn<Parts, String> inventoryLevelColumn;
    @FXML private TableColumn<Parts, String> priceColumn;

    //configure the Add Part button
    @FXML private Button AddPartButton;
    public static ObservableList<Parts> currList;

    //configure the search field
    @FXML private TextField searchField;


    /**
     * Constuctor
     */
    public Controller(){
       // adds new Parts objects
//        initialParts.add(new inHousePart("Screws", 10, 10.99, 0001));
//        initialParts.add(new inHousePart("Nails", 10, 10.99, 0002));
//        initialParts.add(new inHousePart("Hammer", 10, 10.99,0003));

        //MAJOR BUILD ISSUE! You need to figure out how to have an initial set of data that does not get reinitialized every time we go back to the main Controller

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
        partsTableView.setItems(initialParts);


    }


    public void addPartButtonPressed(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("AddPartWindow.fxml"));
        Scene addPartWindowScene = new Scene(root);

        AddPartWindow.receiveDataset(initialParts);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(addPartWindowScene);
        window.show();

    }

    //handler that triggers the searchByPartName method
    public void resultsHandle(ActionEvent actionEvent) {
        String searchInput = searchField.getText();

        ObservableList<Parts> foundParts = searchByPartName(searchInput);
        partsTableView.setItems(foundParts);
        System.out.println("Searching");
        searchField.setText("");

    }
    //search method running in the background as private method
    private ObservableList<Parts> searchByPartName(String searchInput) {
        ObservableList<Parts> foundPartNames = FXCollections.observableArrayList();
        ObservableList<Parts> allParts = partsTableView.getItems();

        for(Parts foundParts : allParts) {
            if (foundParts.getPartName().contains(searchField.getText())) {
                foundPartNames.add(foundParts);
            }

            else if (foundParts.getPartName().equals("")) {
                foundPartNames = allParts;
            }
        }

        return foundPartNames;
    }



}
