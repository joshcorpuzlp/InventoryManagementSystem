package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    //flag variable needed for disableInitializeDataSet()
    private static boolean isFirstTime = true;

    //Method to add a part object into the private static data member allParts @param is a Parts object.
    public static void addPart(Part part) {
        allParts.add(part);
    }

    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    public static void deletePart(Part part) {
        for (int i = 0; i < allParts.size(); ++i) {
            if (part == allParts.get(i))
            allParts.remove(i);
        }
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    //essentially disables the initializeDataSet function after it is run.
    public static boolean disableInitializeDataSet() {
        return isFirstTime = false;
    }

    //called to add the data set to allParts IF isFirstTime is true;
    public static void initializeDataSet() {
        if (isFirstTime) {
            addPart(new outsourcePart("Magic Keyboard", 100, 69.99, 10, 5, "Apple"));
            addPart(new outsourcePart("Magic Trackpad", 10, 59.99, 10, 5, "Apple"));
            addPart(new inHousePart("USB C Charger", 10, 10.99, 10, 5, 1012));
        }
    }


    //Method that first checks if the input is String or an int. Then, it will check each element of the allParts array and add the matches to
    //another ObservableList called foundPartNames and return that to be shown as the new contents of the partsTableView
    public static ObservableList<Part> lookUpPart(String searchInput) {
        ObservableList<Part> foundPartNames = FXCollections.observableArrayList();
        boolean isText = true;
        if (searchInput.matches(".*\\d.*")) {
            isText = false;
        }

        if (isText) {
            for (Part foundPart : getAllParts()) {
                //conditional statement makes both the searchInput string and the partName String lowerCase so that it can disregard capitalization
                if (foundPart.getPartName().toLowerCase(Locale.ROOT).contains(searchInput.toLowerCase(Locale.ROOT))) {
                    foundPartNames.add(foundPart);
                } else if (foundPart.getPartName().equals("")) {
                    foundPartNames = getAllParts();
                }
            }
        }
        else {
            for (Part foundPart : getAllParts()) {
                if (foundPart.getId() == Integer.parseInt(searchInput)) {
                    foundPartNames.add(foundPart);
                } else if (foundPart.getPartName().equals("")) {
                    foundPartNames = getAllParts();
                }
            }
        }

        return foundPartNames;
    }

    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static void initializeProductDataSet() {
        if (isFirstTime) {
            addProduct(new Product("Macbook Pro 13 inch", 100, 1299.99, 500, 5));
            addProduct(new Product("Mac Mini", 125, 699.99, 400, 5));
            addProduct(new Product("IPad Pro 10 inch", 200, 999.99, 350, 5));
            addProduct(new Product("IPad Pro 12.9 inch", 200, 1099.99, 350, 5));
        }
    }

    //Method that first checks if the input is String or an int. Then, it will check each element of the allParts array and add the matches to
    //another ObservableList called foundPartNames and return that to be shown as the new contents of the partsTableView
    public static ObservableList<Product> lookUpProduct(String searchInput) {
        ObservableList<Product> foundProductNames = FXCollections.observableArrayList();
        boolean isText = true;
        if (searchInput.matches(".*\\d.*")) {
            isText = false;
        }

        if (isText) {
            for (Product foundProduct : getAllProducts()) {
                //conditional statement makes both the searchInput string and the partName String lowerCase so that it can disregard capitalization
                if (foundProduct.getProductName().toLowerCase(Locale.ROOT).contains(searchInput.toLowerCase(Locale.ROOT))) {
                    foundProductNames.add(foundProduct);
                } else if (foundProduct.getProductName().equals("")) {
                    foundProductNames = getAllProducts();
                }
            }
        }
        else {
            for (Product foundProduct : getAllProducts()) {
                if (foundProduct.getProductID() == Integer.parseInt(searchInput)) {
                    foundProductNames.add(foundProduct);
                } else if (foundProduct.getProductName().equals("")) {
                    foundProductNames = getAllProducts();
                }
            }
}

        return foundProductNames;
    }

    public static void deleteProduct(Product product) {
        for (int i = 0; i < allProducts.size(); ++i) {
            if (product == allProducts.get(i))
                allProducts.remove(i);
        }
    }





    public Inventory() {

    }
}
