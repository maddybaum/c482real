package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;
/**Inventory class which creates the methods used for both parts and product*/
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /*Generate getters only because we will be using the add list method instead of setters because these are Lists containing many parts or products*/
    public static int partId;
    public static int productId;
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**@param part
     * Per UML, we need to create add methods for both lists. Java has built in Add method for lists*/

    public static void addPart(Part part) {
        allParts.add(part);
    }
    /**@param product
     * Method to add product*/
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /**We also need to add methods that will look up a part id in the allParts list and a product id in the allProducts list*/
    /**The part search will return a part. It is a static method because it will be used in the same way for all parts, I will loop through the list of parts and match the functions argument to one in the list*/


/**Similar to methods above, this will look up part by name */

/**@return Observable list of parts*/
    public static ObservableList<Part> lookupPart(String name){
        //new list
        ObservableList matchingParts = FXCollections.observableArrayList();
        //ObservableList<Part> allParts = Inventory.getAllParts();
        //loop through all the parts
        for (Part part : allParts){
            //if the part currently looping through matches the name argument, return that part
            if(part.getName().toLowerCase().contains(name.toLowerCase())){
                matchingParts.add(part);
            }
        }

        //if there is no match then return null
        return matchingParts;
    }
    /**@param id
     * used to look up part based on ID*/
    public static Part lookupPart(int id) {
        //ObservableList<Part> allParts = Inventory.getAllParts();
        //ObservableList matchingId = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getId() == id) {
                return part;
            }
        }
        return null;
    }

    /**@param id
     * used to look up product based on id*/
    public static Product lookupProduct(int id){
        for(Product product : allProducts) {
            if(product.getId() == id){
                return product;
            }
        }
        return null;
    }

    /**@return list of products
     * This method will look up a product by name*/
    public static ObservableList<Product> lookupProduct(String name){
        //loop through all the products
        ObservableList allProducts = Inventory.getAllProducts();
        ObservableList matchingProducts = FXCollections.observableArrayList();

        for (Product product : Inventory.getAllProducts()){
            //if the current product being looped through matches the name, then return that product
            if(product.getName() == name){
                matchingProducts.add(product);
            }
        }
        if(matchingProducts.size() == 0){
            Alert noSearch = new Alert(Alert.AlertType.ERROR);
            noSearch.setContentText("There are no matches for your search terms");
            Optional<ButtonType> response = noSearch.showAndWait();
        }
        return matchingProducts;
    }
    /** @param index, selectedPart
     * Now we need to add methods to update part and product by taking in an index and what we want to update that index with*/

    public void updatePart(int index, Part selectedPart){
        //get the list of all parts and then set the specified index to the new selectedPart
        allParts.set(index, selectedPart);
    }
    /**@param index @param selectedPart
     * */
    public void updateProduct(int index, Product selectedProduct){
        //get the list of all products then set the specified index to the new selectedProduct
        allProducts.set(index, selectedProduct);
    }
    /**@param selectedPart
     * @return boolean
     * Now we need to create methods to delete a product or part. These will return boolean values, and will take in their respective part or product to be removed. If unsuccessful, will return false
    //In order to do this and return a boolean, we will need to check if the allParts list contains the selectedPart, and then if it does we will use the built in remove method to delete it
    //Java has a built in contains method that allows you to check if a value exists within a list. I will use this in my conditional statement to check if the selectedPart exists in the allParts list*/
    public static boolean deletePart(Part selectedPart){
        //check if selectedParts is in allParts list
        if(allParts.contains(selectedPart)){
            //If it is then remove the selected part
            allParts.remove(selectedPart);
            return true;
        }
        //If it is not within the allParts list then return false
        else return false;
    }

/**@param selectedProduct @return boolean
 * Similar to delete part listed above*/
    public static boolean deleteProduct(Product selectedProduct){
        //Check if allProducts contains selectedProduct
        if(allProducts.contains(selectedProduct)){
            //if it does then remove the selectedProduct and return true
            allProducts.remove(selectedProduct);
            return true;
        }
        //Otherwise return false
        else return false;
    }
    /**@return partId
     * Increase part and product IDs when new ones added*/
    public static int getNewPartId(){
        return partId++;
    }
    public static int getNewProductId(){
        return productId++;
    }
}
