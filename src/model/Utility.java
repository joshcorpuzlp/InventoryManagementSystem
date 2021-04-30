package model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

import java.awt.font.NumericShaper;
import java.util.Optional;

public class Utility {
    private static String errorMessage = "";

    public static boolean saveConfirmationMessage() {
        boolean isOK = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm");
        alert.setHeaderText("Saving changes");
        alert.setContentText("Would you like to save changes?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            isOK = true;
        }

        return isOK;
    }

    public static boolean deleteConfirmationMessage() {
        boolean isOK = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm delete");
        alert.setHeaderText("Deleting item");
        alert.setContentText("Would you like to delete selected item?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            isOK = true;
        }

        return isOK;
    }

    public static void lastItemDeleteMessage() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.NONE);
        alert.setTitle("CANNOT DELETE");
        alert.setHeaderText("CANNOT DELETE");
        alert.setContentText("This is the last item! Table cannot be empty.");
        alert.showAndWait();
    }

    public static void noItemToDeleteMessage() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.NONE);
        alert.setTitle("Nothing selected");
        alert.setHeaderText("Nothing selected");
        alert.setContentText("Nothing was selected to delete");
        alert.showAndWait();
    }

    public static void productHasAssociatedPartMessage() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.NONE);
        alert.setTitle("Associated part error");
        alert.setHeaderText("Product has an associated part");
        alert.setContentText("Can not delete selected product, this product has an associated part!");
        alert.showAndWait();
    }


    public static void validIntInput(String input)  {
        try {
            int inputToInt = Integer.parseInt(input);
            if (inputToInt <= 0) {
                errorMessage += "Input must be larger than 0.\n";
            }
        }
        catch (NumberFormatException ex) {
            errorMessage += "Please enter a positive whole number.\n";
        }
    }

    public static void validDoubleInput(String input) {
        try {
            double inputToDouble = Double.parseDouble(input);
            if (inputToDouble <= 0) {
                errorMessage += "Enter value larger than 0.00\n";
            }
        }
        catch (NumberFormatException ex) {
            errorMessage += "Please enter value in the following format: 0.00";
        }
    }

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static void resetErrorMessage() {
        errorMessage = "";
    }
}