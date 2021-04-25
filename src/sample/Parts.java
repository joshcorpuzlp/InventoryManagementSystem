package sample;

import javafx.beans.property.SimpleStringProperty;

public class Parts {
    private int id;
    private SimpleStringProperty partName;
    private int partInventoryLevel, maxInventory, minInventory;
    private double price;

    public Parts(int id, String partName, int partInventoryLevel, double price) {
        setId(id);
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
