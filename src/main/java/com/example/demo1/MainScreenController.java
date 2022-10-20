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

/**This is the controller for the main screen when the application first opens*/
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

    public static void setProductToModify(Product productToModify) {
        MainScreenController.productToModify = productToModify;
    }

    /**@param actionEvent
     * When a user clicks the modify part button, this method will determine if they have selected a part to modify
 * If they have then the modifyPart fxml file will populate
 * If they have not selected a part yet then they will receive an alert*/
    public void modifyPart(ActionEvent actionEvent) throws IOException {
        //Grab currently selected part
        partToModify = (Part)partTable.getSelectionModel().getSelectedItem();

        if(partToModify == null) {
            Alert noValue = new Alert(Alert.AlertType.ERROR);
            noValue.setContentText("There is no value to modify");
            Optional<ButtonType> response = noValue.showAndWait();
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("modifyPart.fxml"));
        loader.load();
        modifyPartController mpc = loader.getController();
        mpc.setInputs(partToModify);


        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }
/**@param actionEvent
 * This is the method that will be called when the user clicks the add button
 * It will populate the addPartFormController */
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
/**@param actionEvent
 * If the user would like to search the parts that are being displayed on the main page,
 * this method will be used*/
    public void searchParts(ActionEvent actionEvent) {
        //Grab text that was typed in search bar
        String searchedPart = partSearchBar.getText();
        if(searchedPart.isBlank()){
            partTable.setItems(Inventory.getAllParts());
        }
        //Grab all of the parts in teh inventory and put them in the partsList
        try {
            Part part = Inventory.lookupPart(Integer.parseInt(searchedPart));
            if(part != null){
                partTable.getSelectionModel().select(part);
            } else {
                //Todo handle null part
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            ObservableList<Part> matchingParts = Inventory.lookupPart(searchedPart);
            if(matchingParts.size() == 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("There are no matches for your search");
                //finish alert
                alert.showAndWait();
            }
            else {
                partTable.setItems(matchingParts);
            }
        }

    }


/**@param actionEvent
 *
 * If the user clicks on the delete button, this method will be called
 * If they have not clicked on any part to delete, they will receive an alert telling them so
 * Otherwise they will receive an alert asking if they are sure they would like to delete */
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

/**@param actionEvent
 * This will be the method called if the user clicks the modify button
 * If they have not selected a product to modify, they will receive an alert telling them so
 * Otherwise the modifyProduct fxml file will load, pre populated with the selected product*/
    public void modifyProduct(ActionEvent actionEvent) throws IOException {
        //Grab currently selected product
        productToModify = (Product)productTable.getSelectionModel().getSelectedItem();
        //Check if no product was selected
        if(productToModify == null){
            Alert noValue = new Alert(Alert.AlertType.ERROR);
            noValue.setContentText("There is no value to modify");
            Optional<ButtonType> response = noValue.showAndWait();
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("modifyProduct.fxml"));
        loader.load();
        modifyProductController mprodc = loader.getController();
        mprodc.setProdInputs(productToModify);

        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();


    }
/**@param actionEvent
 * This is the method called if a user selects add on the product table. It will open the addProduct fxml window*/
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
/** @return ObservableList of products
 * This method allows users to use the search bar for products and will return a list of matching products*/
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
/**@param actionEvent
 * If a user clicks the delete button, this method will be called
 * If they have not selected a product to delete, they will receive an error telling them so
 * Otherwise this method will delete the product*/
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
/**@param actionEvent
 * This will close the application if the user clicks cancel on the main page*/
    public void onExitButtonClicked(ActionEvent actionEvent) {
        System.exit(0);
    }
    /**@param actionEvent
     * This will return the part that has been selected to modify*/
    public static Part getPartToModify() {

        return partToModify;
    }

    /**@return Product
     * This will return the product that has been selected to modify*/
    public static Product getProductToModify(){
        return productToModify;
    }

    /**@param resourceBundle @param url
     * Use initializable in the same way that is described in the tutorial video to import data into the table for basketball players. Initializing page and values into table*/
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
