package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TotalParts {

    private static ObservableList<Parts> totalParts = FXCollections.observableArrayList();

    public static void addPart(Parts part) {
        totalParts.add(part);
    }

    public static ObservableList<Parts> returnAllData() {
        return totalParts;
    }
}
