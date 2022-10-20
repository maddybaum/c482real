package com.example.demo1;

/**In house class extends the part class but adds in the machineID attribute*/

public class InHouse extends Part{
    private int machineID;

    public int getMachineID() {
        return machineID;
    }
    /**Add machineID getter and setter*/

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
    /**Constructor for in house parts*/
    public InHouse(int id, String name, double price, int inventory,  int min, int max, int machineId) {
        super(id, name, price, inventory, min, max);


    }
}
