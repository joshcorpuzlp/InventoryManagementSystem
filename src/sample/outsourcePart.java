package sample;

import javafx.beans.property.SimpleStringProperty;

public class outsourcePart extends Parts {
    private SimpleStringProperty companyName;

    //constructor that extends Parts
    public outsourcePart  (String partName, int partInventoryLevel, double price, String companyName ) {
        super(partName, partInventoryLevel, price);
        setCompanyName(companyName);
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public void setCompanyName(String companyName) {
        this.companyName = new SimpleStringProperty(companyName);
    }
}
