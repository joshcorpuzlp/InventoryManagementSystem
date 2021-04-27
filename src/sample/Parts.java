package sample;

import javafx.beans.property.SimpleStringProperty;

public class Parts {

    //id field needs to also have a corresponding static int id to keep count of all the inputted items.
    private int id;
    private static int partCounter = 1;
    private SimpleStringProperty partName;
    private int partInventoryLevel, maxInventory, minInventory;
    private double price;

    public Parts(String partName, int partInventoryLevel, double price) {
        this.id = partCounter++;
        setPartName(partName);
        setPartInventoryLevel(partInventoryLevel);
        setPrice(price);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPartName() {
        return partName.get();
    }

    public void setPartName(String partName) {
        this.partName = new SimpleStringProperty(partName);
    }

    public int getPartInventoryLevel() {
        return partInventoryLevel;
    }

    public void setPartInventoryLevel(int partInventoryLevel) {
        this.partInventoryLevel = partInventoryLevel;
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
