package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * This class creates a product that contains the information for products.*/
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     *
     * @return id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id to set id
     * */

    public void setId(int id) {
        this.id = id;
    }
/**
 * @return name string*/
    public String getName() {
        return name;
    }
/**
 * @param name to set name
 * */
    public void setName(String name) {
        this.name = name;
    }
/**
 * @return price
 * */
    public double getPrice() {
        return price;
    }
/**
 * @param price to set price*/
    public void setPrice(double price) {
        this.price = price;
    }
/**
 * @return the stock*/
    public int getStock() {
        return stock;
    }
/**
 * @param stock to set stock
 * */
    public void setStock(int stock) {
        this.stock = stock;
    }
/**
 * @return the minimum number
 * */
    public int getMin() {
        return min;
    }
/**
 * @param min the minimum
 * */
    public void setMin(int min) {
        this.min = min;
    }
/**
 * @return the maximum
 * */
    public int getMax() {
        return max;
    }
/**
 * @param max to set max
 * */
    public void setMax(int max) {
        this.max = max;
    }

}
