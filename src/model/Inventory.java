package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    //flag variable needed for disableInitializeDataSet()
    private static boolean isFirstTime = true;


    /**
     * Method to add a part object into the private static data member allParts @param is a Parts object.
     * @param part
     */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /**
     * Method that updates a specific Part object within the ObservableList of Part objects.
     * @param index passes an integer value that determines the index number of the part object in the ObservableList to update.
     * @param selectedPart passes a part object that will be used to update the selected index.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Method that updates a specific Product object within the ObservableList of Part objects.
     * @param index passes an integer value that determines the index number of the Product object in the ObservableList to update.
     * @param selectedProduct passes a product object that will be used to update the selected index.
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * deletes the selected part within the ObservableList of Part objects.
     * @param part - passes the part object to delete
     */
    public static void deletePart(Part part) {
        for (int i = 0; i < allParts.size(); ++i) {
            if (part == allParts.get(i))
            allParts.remove(i);
        }
    }

    /**
     *
     * @return the private data member of ObservableList of Part objects.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Method disables the initializeDataSet function after it is run.
     * @return boolean value used as a flag variable in the initializer
     */
    public static boolean disableInitializeDataSet() {
        return isFirstTime = false;
    }

    /**
     * Method called to add the data set to allParts IF isFirstTime is true;
     */
    public static void initializeDataSet() {
        if (isFirstTime) {
            addPart(new outsourcePart("Magic Keyboard", 100, 69.99, 200, 5, "Apple"));
            addPart(new outsourcePart("Magic Trackpad", 100, 59.99, 150, 5, "Apple"));
            addPart(new inHousePart("USB C Charger", 75, 10.99, 150, 5, 1012));
        }
    }

    /**
     * Method that first checks if the input is String or an int. Then, it will check each element of the allParts array and add the matches to
     * another ObservableList called foundPartNames and return that to be shown as the new contents of the partsTableView
     * @param searchInput passes a String input from a TextField
     * @return an ObservableList of Part objects
     */
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

    /**
     * Method to add a product object into the private static data member allProducts
     * @param product passes a Product object to be added into the allProducts ObservableList
     */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /**
     *
     * @return the private data member of ObservableList of Products objects.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Method called to add the data set to allProducts IF isFirstTime is true;
     */
    public static void initializeProductDataSet() {
        if (isFirstTime) {
            addProduct(new Product("Macbook Pro 13 inch", 100, 1299.99, 500, 5));
            addProduct(new Product("Mac Mini", 125, 699.99, 400, 5));
            addProduct(new Product("IPad Pro 10 inch", 200, 999.99, 350, 5));
            addProduct(new Product("IPad Pro 12.9 inch", 200, 1099.99, 350, 5));
        }
    }

    /**
     * Method that first checks if the input is String or an int. Then, it will check each element of the allParts array and add the matches to
     *     another ObservableList called foundPartNames and return that to be shown as the new contents of the partsTableView
     */
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

    /**
     * deletes the selected part within the ObservableList of Product objects.
     * @param product passes the part object to delete
     */
    public static void deleteProduct(Product product) {
        for (int i = 0; i < allProducts.size(); ++i) {
            if (product == allProducts.get(i))
                allProducts.remove(i);
        }
    }

    public Inventory() {

    }
}
