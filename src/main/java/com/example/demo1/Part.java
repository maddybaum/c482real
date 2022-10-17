package com.example.demo1;

public abstract class Part {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /*Create constructor for part so that we can generate getters and setters while keeping the attributes private*/

    public Part(int id, String name, int inventory, double price, int max, int min) {
        this.id = id;
        this.name = name;
        this.stock = inventory;
        this.price = price;
        this.max = max;
        this.min = min;
    }
    /*Generate getters and setters for id, name, inventory, price, max and min*/
    /*Get id of a product*/
    public int getId() {
        return id;
    }
    /*Set product id*/
    public void setId(int id) {
        this.id = id;
    }
    /*Get product name*/
    public String getName() {
        return name;
    }
    /*Set product name*/
    public void setName(String name) {
        this.name = name;
    }
    /*Get inventory of product*/
    public int getStock() {
        return stock;
    }
    /*Set inventory of product*/
    public void setStock(int stock) {
        this.stock = stock;
    }
    /*Get price of product*/
    public double getPrice() {
        return price;
    }
    /*Set price of product*/
    public void setPrice(double price) {
        this.price = price;
    }
    /*Get product max*/
    public int getMax() {
        return max;
    }
    /*Set product max*/
    public void setMax(int max) {
        this.max = max;
    }
    /*Get product min*/
    public int getMin() {
        return min;
    }
    /*Set product min*/
    public void setMin(int min) {
        this.min = min;
    }
}
