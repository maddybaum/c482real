package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    //We will need to grab the selected values in order to modify part and product. These will be static variables so we can use a getter to grab them and use in modifyPartController

    //Using handoff method from webinar to pass the static variables from one controller to another
    private static Part partToModify;
    private static Product productToModify;
    public Button partModifyButton;
    public Button partAddButton;
    public TextField partSearchBar;
    public Label partLabel;
    public TableView partTable;
    public TableColumn tablePartID;
    public TableColumn tablePartName;
    public TableColumn tablePartInvLvl;
    public TableColumn tablePartCPU;
    public Button partDeleteButton;
    public Button productModifyButton;
    public Button productAddButton;
    public TextField productSearchBar;
    public TableView productTable;
    public TableColumn tableProductID;
    public TableColumn tableProductName;
    public TableColumn tableProductInvLvl;
    public TableColumn tableProductCPU;
    public Button productDeleteButton;
    public Button mainExitButton;

    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public void modifyPart(ActionEvent actionEvent) throws IOException {
        //Grab currently selected part
        partToModify = (Part)partTable.getSelectionModel().getSelectedItem();
        //Check if nothing is selected
        if(partToModify == null){
            Alert noValue = new Alert(Alert.AlertType.ERROR);
            noValue.setContentText("There is no value to modify");
            Optional<ButtonType> response = noValue.showAndWait();

        } else {
            Parent modifyPart = FXMLLoader.load(HelloApplication.class.getResource("modifyPart.fxml"));
            Scene scene = new Scene(modifyPart);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

        }
    }

    public void addPart(ActionEvent actionEvent) throws IOException {
        //Grab the modal fxml file
        Parent addPartModal = FXMLLoader.load(HelloApplication.class.getResource("addPartForm.fxml"));
        //set new scene with add part modal
        Scene scene = new Scene(addPartModal);
        //set stage of the modal
        Stage modal = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        //put add part modal inside
        modal.setScene(scene);
        //show the modal
        modal.show();
    }

    public void searchParts(ActionEvent actionEvent) {
        //Grab text that was typed in search bar
        String searchedPart = partSearchBar.getText();
        //Grab all of the parts in teh inventory and put them in the partsList
        ObservableList<Part> partsList = Inventory.getAllParts();
        //Create empty list to store matching parts
        ObservableList matchingParts = FXCollections.observableArrayList();
        //loop through the parts in parts list
        for(Part part : partsList){
            //check if the part name and part id contains your search terms
            if(part.getName().contains(searchedPart) || String.valueOf(part.getId()).contains(searchedPart)){
                //if the name or ID contains search terms, then add it to the matching parts list
                matchingParts.add(part);

            }
        }
        //display all of the matching parts in the table
        partTable.setItems(matchingParts);
        //If there are no matching parts, the list size will be 0, so display alert that there are no matching parts
        if(matchingParts.size() == 0){
            Alert noValue = new Alert(Alert.AlertType.ERROR);
            noValue.setContentText("There no values matching your search terms");
            Optional<ButtonType> response = noValue.showAndWait();
            ;
        }

    }

    public void deletePart(ActionEvent actionEvent) {
        //Setting functionality to delete a selected part. Need to type cast the selection to a Part object, and then build in methods to identify the already selected part in the table
        Part partToDelete = (Part)partTable.getSelectionModel().getSelectedItem();
        //If nothing is selected then we just want to escape
        if(partToDelete == null){
            Alert noValue = new Alert(Alert.AlertType.ERROR);
            noValue.setContentText("There is no value to delete");
            Optional<ButtonType> response = noValue.showAndWait();

            return;
        }
        //per instructions ask if user is sure they want to delete through an alert
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setContentText("Are you sure you want to delete?");
        Optional<ButtonType> response = confirmation.showAndWait();


        //delete the selected part
        if(response.get() == ButtonType.OK){
            Inventory.deletePart(partToDelete);
        }
    }


    public void modifyProduct(ActionEvent actionEvent) throws IOException {
        //Grab currently selected product
        Product productToModify = (Product)productTable.getSelectionModel().getSelectedItem();
        //Check if no product was selected
        if(productToModify == null){
            Alert noValue = new Alert(Alert.AlertType.ERROR);
            noValue.setContentText("There is no value to modify");
            Optional<ButtonType> response = noValue.showAndWait();
        } else {
            //load the modifyProduct fxml file
            Parent modifyProduct = FXMLLoader.load(HelloApplication.class.getResource("modifyProduct.fxml"));
            //set new scene with modifyproduct fxml
            Scene scene = new Scene(modifyProduct);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    public void addProduct(ActionEvent actionEvent) throws IOException {
        //Grab the addProductForm fxml file
        Parent addProductModal = FXMLLoader.load((HelloApplication.class.getResource("addProduct.fxml")));
        //Set new scene using the fxml modal
        Scene scene = new Scene(addProductModal);
        Stage modal = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        //Put add product modal inside
        modal.setScene(scene);
        //Open the window
        modal.show();
    }

    public ObservableList searchProducts(ActionEvent actionEvent) {
        //Grab text that was typed in the search bar
        String searchedProduct = productSearchBar.getText();
        //Get all of the products in the inventory
        ObservableList<Product> productsList = Inventory.getAllProducts();
        //Create empty list to store the matching products
        ObservableList matchingProducts = FXCollections.observableArrayList();
        //Loop through the products in product list
        for (Product product : productsList) {
            //If the product's name contains the searched term or the product's id contains the search term, then add it to the matching product list
            if (product.getName().contains(searchedProduct) || String.valueOf(product.getId()).contains(searchedProduct)) {
                matchingProducts.add(product);
            }
        }
        //assign the matching products list to the product table so all of the products that match search terms are displayed
        productTable.setItems(matchingProducts);
        //If the length of the matching products list is 0, that means there were no matching products
        if (matchingProducts.size() == 0) {
            Alert noValue = new Alert(Alert.AlertType.ERROR);
            noValue.setContentText("There is no value matching your search terms");
            Optional<ButtonType> response = noValue.showAndWait();
            ;
        }
        return matchingProducts;
    }

    public void deleteProduct(ActionEvent actionEvent) {
        //Setting functionality to delete selected product. Need to type cast the selection to a Product and then use built in Java methods to identify which product (if any) is selected
        Product productToDelete = (Product)productTable.getSelectionModel().getSelectedItem();
        //If nothing is selected then we just want to escape
        if(productToDelete == null) {
            //Need to add an alert
            Alert noValue = new Alert(Alert.AlertType.ERROR);
            noValue.setContentText("There is no value to delete");
            Optional<ButtonType> response = noValue.showAndWait();
            return;
        }
        else {
            Inventory.deleteProduct(productToDelete);
        }
    }

    public void onExitButtonClicked(ActionEvent actionEvent) {
        System.exit(0);
    }
    public static Part getPartToModify() {

        return partToModify;
    }
    public static Product getProductToModify(){
        return productToModify;
    }

    //Use initializable in the same way that is described in the tutorial video to import data into the table for basketball players
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Within the initializable method, set the part table content to be from the getAllParts() method in inventory
        partTable.setItems(Inventory.getAllParts());
        //Need to assign each table column to an attribute of the part object that we want to display -> make sure it matches exactly
        tablePartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tablePartCPU.setCellValueFactory(new PropertyValueFactory<>("price"));
        tablePartInvLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));

        //Set the product table content to be from getAllProducts method()
        productTable.setItems(Inventory.getAllProducts());
        //Need to assign each table column to an attribute of the product object that we want to display
        tableProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableProductCPU.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableProductInvLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }

}
