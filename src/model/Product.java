package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;
    private SimpleStringProperty productName;
    private int productStock, productMinInventory, productMaxInventory;
    private double productPrice;

    private static int productCounter = 1;


    /**
     * Constructor for the Product class.
     * This is what declares the data members of a new Product object.
     * @param productName String object
     * @param productStock Integer value
     * @param productPrice Double value
     * @param productMaxInventory Integer value
     * @param productMinInventory Integer value
     */
    public Product(String productName, int productStock, double productPrice, int productMaxInventory, int productMinInventory) {
        setProductID();
        setProductName(productName);
        setProductStock(productStock);
        setProductPrice(productPrice);
        setProductMaxInventory(productMaxInventory);
        setProductMinInventory(productMinInventory);
    }

    /**
     * @return - an ObservableList of associateParts
     */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }


    /**
     *
     * @return - the product ID
     */
    public int getProductID() {
        return productID;
    }

    /**
     * Method declares the product's ID
     */
    public void setProductID() {
        this.productID = productCounter++;
    }

    /**
     * @return the product's name.
     */
    public String getProductName() {
        return productName.get();
    }

    /**
     * Method declares the product's name.
     * @param productName passes a String object to be declared as the product's name.
     */
    public void setProductName(String productName) {
        this.productName = new SimpleStringProperty(productName);
    }

    /**
     * @return returns the product's stock level as an integer value.
     */
    public int getProductStock() {
        return productStock;
    }

    /**
     * Method declares the product's stock level.
     * @param productStock passes an integer value that will be declared as the product's stock level.
     */
    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    /**
     *
     * @return returns the product's minimum inventory level
     */
    public int getProductMinInventory() {
        return productMinInventory;
    }

    /**
     * Method delcares the product's minimum inventory level.
     * @param productMinInventory passes an integer value that will be declares as the product's minimum stock level.
     */
    public void setProductMinInventory(int productMinInventory) {
        this.productMinInventory = productMinInventory;
    }

    /**
     *
     * @return returns the product's maximum inventory level
     */
    public int getProductMaxInventory() {
        return productMaxInventory;
    }

    /**
     * Method declares the product's maximum inventory level.
     * @param productMaxInventory passes an integer value that will be declares as the product's maximum stock level.
     */
    public void setProductMaxInventory(int productMaxInventory) {
        this.productMaxInventory = productMaxInventory;
    }

    /**
     *
     * @return returns the product's price as a double value.
     */
    public double getProductPrice() {
        return productPrice;
    }

    /**
     * Method declares the product's price.
     * @param productPrice passes a numerical value that will be declared as the product's price
     */
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * Adds an object of Part class into a Product's ObservableList of associated parts.
     * @param part Part object
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Method sets an ObservableList of Part class the Product's Observable list of associated parts.
     * @param associatedParts passes an ObservableLIst of Part class.
     */
    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }

    /**
     * Method deletes a Part from the associated parts ObservableList.
     * @param part passes a Part object to be removed from the associated parts ObservableList.
     */
    public void deleteAssociatedPart(Part part) {
        associatedParts.remove(part);
    }

}
