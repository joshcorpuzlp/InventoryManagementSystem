package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;

import java.util.Locale;

public class Inventory {
    private static ObservableList<Parts> allParts = FXCollections.observableArrayList();
    private static boolean isFirstTime = true;
    //private static ObservableList<Products> allProducts;

    //Method to add a part object into the private static data member allParts @param is a Parts object.
    public static void addPart(Parts part) {
        allParts.add(part);
    }

    public static void updatePart(int index, Parts selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static void deletePart(Parts part) {
        for (int i = 0; i < allParts.size(); ++i) {
            if (part == allParts.get(i))
            allParts.remove(i);
        }

    }

    public static ObservableList<Parts> getAllParts() {
        return allParts;
    }

    //essentially disables the initializeDataSet function after it is run.
    public static boolean disableInitializeDataSet() {
        isFirstTime = false;
        return isFirstTime;
    }

    //called to add the data set to allParts IF isFirstTime is true;
    public static void initializeDataSet() {
        if (isFirstTime) {
            addPart(new inHousePart("Screws", 10, 10.99, 10, 5, 1010));
            addPart(new inHousePart("Nails", 10, 10.99, 10, 5, 1011));
            addPart(new inHousePart("Hammer", 10, 10.99, 10, 5, 1012));
        }
    }

    //Method that first checks if the input is String or an int. Then, it will check each element of the allParts array and add the matches to
    //another ObservableList called foundPartNames and return that to be shown as the new contents of the partsTableView
    public static ObservableList<Parts> searchByPartName(String searchInput) {
        ObservableList<Parts> foundPartNames = FXCollections.observableArrayList();
        boolean isText = true;
        if (searchInput.matches(".*\\d.*")) {
            isText = false;
        }

        if (isText) {
            for (Parts foundParts : getAllParts()) {
                //conditional statement makes both the searchInput string and the partName String lowerCase so that it can disregard capitalization
                if (foundParts.getPartName().toLowerCase(Locale.ROOT).contains(searchInput.toLowerCase(Locale.ROOT))) {
                    foundPartNames.add(foundParts);
                } else if (foundParts.getPartName().equals("")) {
                    foundPartNames = getAllParts();
                }
            }
        }
        else {
            for (Parts foundParts : getAllParts()) {
                if (foundParts.getId() == Integer.parseInt(searchInput)) {
                    foundPartNames.add(foundParts);
                } else if (foundParts.getPartName().equals("")) {
                    foundPartNames = getAllParts();
                }
            }
        }

        return foundPartNames;
    }

    public Inventory() {

    }

}
