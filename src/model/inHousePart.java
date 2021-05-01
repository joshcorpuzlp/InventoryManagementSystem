package model;


public class inHousePart extends Part {
    private int machineID;

    /**
     * constructor extends the Parts class with an extra field, "machineID".
     * @param partName
     * @param partInventoryLevel
     * @param price
     * @param machineID -- this is the added parameter in the constructor.
     */
    public inHousePart(String partName,int partInventoryLevel, double price, int maxInventory, int minInventory, int machineID) {
        super(partName, partInventoryLevel, price, maxInventory, minInventory);
        setMachineID(machineID);
    };

    public int getMachineID() {
        return machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
