package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    //these will configure the PartsTableView
    @FXML private TableView partsTable;
    @FXML private TableColumn partID;
    @FXML private TableColumn partName;
    @FXML private TableColumn inventoryLevel;
    @FXML private TableColumn price;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //initialize the PartsTableView
        partsTable = new TableView();
        partsTable.setEditable(true);

    }
}
