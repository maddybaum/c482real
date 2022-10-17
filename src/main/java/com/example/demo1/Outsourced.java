package com.example.demo1;

public class Outsourced extends Part{
    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public Outsourced(int id, String name, int inventory, double price, int max, int min, String companyName) {
        super(id, name, inventory, price, max, min);
        this.companyName = companyName;
    }
}
