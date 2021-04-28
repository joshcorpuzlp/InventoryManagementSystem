package model;

import javafx.beans.property.SimpleStringProperty;
import model.Parts;

public class outsourcePart extends Parts {
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
