package sample;

import javafx.beans.property.SimpleStringProperty;

public class outsourcePart extends Parts {
    private SimpleStringProperty companyName;

    //constructor that extends Parts
    public outsourcePart  (int id, String partName, int partInventoryLevel, double price, String companyName ) {
        super(id, partName, partInventoryLevel, price);
        setCompanyName(companyName);
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public void setCompanyName(String companyName) {
        this.companyName = new SimpleStringProperty(companyName);
    }
}
