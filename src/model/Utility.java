package model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

import java.util.Optional;



public class Utility {



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

    /**
     * Method used to confirm deletion of item
     * @return boolean value to proceed or not
     */
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

    /**
     * Method used to call a warning message.
     */
    public static void lastItemDeleteMessage() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.NONE);
        alert.setTitle("CANNOT DELETE");
        alert.setHeaderText("CANNOT DELETE");
        alert.setContentText("This is the last item! Table cannot be empty.");
        alert.showAndWait();
    }

    /**
     * Method used to call an error message indicating that the user failed to select an item.
     */
    public static void noItemToDeleteMessage() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.NONE);
        alert.setTitle("Nothing selected");
        alert.setHeaderText("Nothing selected");
        alert.setContentText("Nothing was selected to delete");
        alert.showAndWait();
    }

    /**
     * Method used to call an error message indicating that product with an associated part can not be deleted.
     */
    public static void productHasAssociatedPartMessage() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.NONE);
        alert.setTitle("Associated part error");
        alert.setHeaderText("Product has an associated part");
        alert.setContentText("Can not delete selected product, this product has an associated part!");
        alert.showAndWait();
    }

    /**
     * Method used to call an error message indicating that the search produced no results.
     * @param searchInput utilizes the String input as part of the message.
     */
    public static void searchProducedNoResults(String searchInput) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.NONE);
        alert.setTitle("Item not found");
        alert.setHeaderText("Search produced no results.");
        alert.setContentText("\"" + searchInput +"\""  +" found no results.");
        alert.showAndWait();
    }


}
