package com.example.demo1;

public class InHouse extends Part{
    private int machineID;

    public int getMachineID() {
        return machineID;
    }
    /*Add machineID getter and setter*/

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    public InHouse(int id, String name, int inventory, double price, int max, int min, int machineId) {
        super(id, name, inventory, price, max, min);


    }
}
