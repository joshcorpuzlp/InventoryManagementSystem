package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class AddPartWindow implements Initializable {
    //configure the fields in Add Part Window
    @FXML private TextField partIDField;
    @FXML private TextField nameField;
    @FXML private TextField inventoryLevelField;
    @FXML private TextField priceField;
    @FXML private TextField maxField;
    @FXML private TextField minField;
    @FXML private TextField machineIDField;

    //Create a method that will add a new part to the tableview with the addpart window
    public void saveNewPart() {
        int IDInput = Integer.parseInt(partIDField.getText());
        int inventoryInput = Integer.parseInt(inventoryLevelField.getText());
        double priceInput = Double.parseDouble(priceField.getText());
        int machineIDInput = Integer.parseInt(machineIDField.getText());

        Parts newPart = new inHousePart(IDInput, nameField.getText(), inventoryInput, priceInput, machineIDInput);
        newPart.setMaxInventory(Integer.parseInt(maxField.getText()));
        newPart.setMinInventory(Integer.parseInt(minField.getText()));


    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
