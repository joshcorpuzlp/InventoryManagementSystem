package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    //these will configure the PartsTableView
    @FXML private TableView<Parts> partsTableView;;
    @FXML private TableColumn<Parts, String> partIDColumn;
    @FXML private TableColumn<Parts, String> partNameColumn;
    @FXML private TableColumn<Parts, String> inventoryLevelColumn;
    @FXML private TableColumn<Parts, String> priceColumn;




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

        //creates an initial observable list of Parts class
        ObservableList<Parts> initialParts = FXCollections.observableArrayList();
        //adds new Parts objects
        initialParts.add(new inHousePart(001, "Screws", 10, 10.99, 0001));
        initialParts.add(new inHousePart(002, "Nails", 10, 10.99, 0002));
        initialParts.add(new inHousePart(003, "Hammer", 10, 10.99,0003));
        //adds the Observable List of Parts class into the partsTableView
        partsTableView.setItems(initialParts);


    }

}
