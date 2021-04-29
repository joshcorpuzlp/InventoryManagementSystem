package model;

import javafx.beans.property.SimpleStringProperty;

public class outsourcePart extends Part {
    private SimpleStringProperty companyName;

    //constructor that extends Parts
    public outsourcePart  (String partName, int partInventoryLevel, double price, int maxInventory, int minInventory, String companyName ) {
        super(partName, partInventoryLevel, price, maxInventory, minInventory);
        setCompanyName(companyName);
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public void setCompanyName(String companyName) {
        this.companyName = new SimpleStringProperty(companyName);
    }
}
