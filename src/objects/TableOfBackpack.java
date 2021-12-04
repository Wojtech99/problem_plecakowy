package objects;

public class TableOfBackpack {
    private int itemNumber;
    private int itemWeight;
    private int itemValue;

    public TableOfBackpack(int itemNumber, int itemWeight, int itemValue) {
        this.itemNumber = itemNumber;
        this.itemWeight = itemWeight;
        this.itemValue = itemValue;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public int getItemWeight() {
        return itemWeight;
    }

    public int getItemValue() {
        return itemValue;
    }
}
