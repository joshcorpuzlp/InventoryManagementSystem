package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;
    private static int productCounter = 1;
    private SimpleStringProperty productName;
    private int productStock, productMinInventory, productMaxInventory;
    private double productPrice;

    public Product(String productName, int productStock, double productPrice, int productMaxInventory, int productMinInventory) {
        setProductID();
        setProductName(productName);
        setProductStock(productStock);
        setProductPrice(productPrice);
        setProductMaxInventory(productMaxInventory);
        setProductMinInventory(productMinInventory);
    }

    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    public void setAssociatedParts(Part part) {
        associatedParts.add(part);
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID() {
        this.productID = productCounter++;
    }

    public String getProductName() {
        return productName.get();
    }

    public void setProductName(String productName) {
        this.productName = new SimpleStringProperty(productName);
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public int getProductMinInventory() {
        return productMinInventory;
    }

    public void setProductMinInventory(int productMinInventory) {
        this.productMinInventory = productMinInventory;
    }

    public int getProductMaxInventory() {
        return productMaxInventory;
    }

    public void setProductMaxInventory(int productMaxInventory) {
        this.productMaxInventory = productMaxInventory;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }


}
