package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TotalParts {

    private static ObservableList<Part> totalParts = FXCollections.observableArrayList();

    public static void addPart(Part part) {
        totalParts.add(part);
    }

    public static ObservableList<Part> returnAllData() {
        return totalParts;
    }
}
