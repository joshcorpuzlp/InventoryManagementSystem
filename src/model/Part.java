/**
 * Supplied class Part.java
 */

/**
 *
 * @author Joshua Corpuz
 */
package model;

import javafx.beans.property.SimpleStringProperty;

public abstract class Part {

    //id field needs to also have a corresponding static int id to keep count of all the inputted items.
    private int id;
    private static int partCounter = 1;
    private SimpleStringProperty partName;
    private int partInventoryLevel, maxInventory, minInventory;
    private double price;

    public Part(String partName, int partInventoryLevel, double price, int maxInventory, int minInventory) {
        setId();
        setPartName(partName);
        setPartInventoryLevel(partInventoryLevel);
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
