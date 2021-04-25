import javafx.beans.property.SimpleStringProperty;

public class Parts {
    private int id;
    private SimpleStringProperty partName;
    private int partInventoryLevel, maxInventory, minInventory;
    private double price;
    private int machineID;
    private SimpleStringProperty companyName;

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
        this.partName.set(partName);
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

    public int getMachineID() {
        return machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    public String getCompanyName() {
        return companyName.get();
    }


    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }
}
