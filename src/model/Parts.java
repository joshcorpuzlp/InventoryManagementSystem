package model;

import javafx.beans.property.SimpleStringProperty;

public class Parts {

    //id field needs to also have a corresponding static int id to keep count of all the inputted items.
    private int id;
    private static int partCounter = 1;
    private SimpleStringProperty partName;
    private int stock, maxInventory, minInventory;
    private double price;

    public Parts(String partName, int stock, double price, int maxInventory, int minInventory) {
        setId();
        setPartName(partName);
        setStock(stock);
        setPrice(price);
        setMaxInventory(maxInventory);
        setMinInventory(minInventory);
    }


    public int getId() {
        return id;
    }

    public void setId() {
        this.id = partCounter++;
    }

    public String getPartName() {
        return partName.get();
    }

    public void setPartName(String partName) {
        this.partName = new SimpleStringProperty(partName);
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMaxInventory() {
        return maxInventory;
    }

    public void setMaxInventory(int maxInventory) {
        this.maxInventory = maxInventory;
    }

    public int getMinInventory() {
        return minInventory;
    }

    public void setMinInventory(int minInventory) {
        this.minInventory = minInventory;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
