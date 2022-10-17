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

public class modifyProductController implements Initializable {
    //Grab the product selected from the main screen;
    Product prodToModify;

    private ObservableList<Part> selectedOptions = FXCollections.observableArrayList();
    public TextField modIDInput;
    public TextField modNameInput;
    public TextField modInvInput;
    public TextField modPriceInput;
    public TextField modMaxInput;
    public TextField modMinInput;
    public TextField modProdSearch;
    public TableView modPartsTable;
    public TableColumn optionsID;
    public TableColumn optionsName;
    public TableColumn optionsInvLvl;
    public TableColumn optionsCPU;
    public TableView selectedPartsTable;
    public TableColumn selectedID;
    public TableColumn selectedName;
    public TableColumn selectedInvLvl;
    public TableColumn selectedCPU;
    public Button modProdAddBtn;
    public Button modProdCancelBtn;
    public Button modProdRemoveBtn;
    public Button modProdSaveBtn;

    public void prodAdd(ActionEvent actionEvent) {
        Part partToAdd = (Part)modPartsTable.getSelectionModel().getSelectedItem();
        selectedOptions.add(partToAdd);
        selectedPartsTable.setItems(selectedOptions);
    }

    public void modRemove(ActionEvent actionEvent) {
        Part partToDelete = (Part)selectedPartsTable.getSelectionModel().getSelectedItem();

        if(partToDelete == null){
                Alert noValue = new Alert(Alert.AlertType.ERROR);
                noValue.setContentText("There is no value to delete");
                Optional<ButtonType> response = noValue.showAndWait();
            }

            //delete the selected part
            else {
            selectedOptions.remove(partToDelete);
            //display the table again with the part deleted
            selectedPartsTable.setItems(selectedOptions);
        }

    }

    public void modSave(ActionEvent actionEvent) throws IOException {
        try {
            //get the ID of the selected part
        int id = prodToModify.getId();
        //Get the name of the selected part
        String name = prodToModify.getName();
        //Get the modified price
        Double price = Double.parseDouble(modPriceInput.getText());
        //Get the modified inventory
        int stock = Integer.parseInt(modInvInput.getText());
        //Get the modified min
        int min = Integer.parseInt(modMinInput.getText());
        //Get tge nidufued nax
        int max = Integer.parseInt(modMaxInput.getText());
        //Verify max is larger than min

            if(min > max){
                Alert minMax = new Alert(Alert.AlertType.ERROR);
                minMax.setContentText("Your minimum is larger than your maximum");
                Optional<ButtonType> response = minMax.showAndWait();
        }
        //Create the new product
        Product newProduct = new Product(id, name, price, stock, min, max);
        //add new product to inventory
        Inventory.addProduct(newProduct);
        //delete the old product
        Inventory.deleteProduct(prodToModify);

        //Grab the modal fxml file
        Parent addPartModal = FXMLLoader.load(HelloApplication.class.getResource("main.fxml"));
        //set new scene with add part modal
        Scene scene = new Scene(addPartModal);
        //set stage of the modal
        Stage modal = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        //put add part modal inside
        modal.setScene(scene);
        //show the modal
        modal.show();
    } catch (IOException e) {
            Alert minMax = new Alert(Alert.AlertType.ERROR);
            minMax.setContentText("Error modifying product. Please ensure all fields are correct");
            Optional<ButtonType> response = minMax.showAndWait();        throw new RuntimeException(e);
    }
}


    public void modCancel(ActionEvent actionEvent) throws IOException {
        //Grab the modal fxml file
        Parent mainModal = FXMLLoader.load(HelloApplication.class.getResource("main.fxml"));
        //set new scene with add part modal
        Scene scene = new Scene(mainModal);
        //set stage of the modal
        Stage modal = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        //put add part modal inside
        modal.setScene(scene);
        //show the modal
        modal.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modPartsTable.setItems(Inventory.getAllParts());
        optionsID.setCellValueFactory(new PropertyValueFactory<>("id"));
        optionsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        optionsInvLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        optionsCPU.setCellValueFactory(new PropertyValueFactory<>("price"));

        selectedPartsTable.setItems(selectedOptions);
        selectedID.setCellValueFactory(new PropertyValueFactory<>("id"));
        selectedName.setCellValueFactory(new PropertyValueFactory<>("name"));
        selectedInvLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        selectedCPU.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Use handoff method to grab selected product from main screen
        prodToModify = MainScreenController.getProductToModify();
    }

    public void searchForProd(ActionEvent actionEvent) {
        //Grab text that was typed in the search bar
        String searchedProduct = modProdSearch.getText();
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
        selectedPartsTable.setItems(matchingProducts);
        //If the length of the matching products list is 0, that means there were no matching products
        if (matchingProducts.size() == 0) {
            Alert noValue = new Alert(Alert.AlertType.ERROR);
            noValue.setContentText("There is no value matching your search terms");
            Optional<ButtonType> response = noValue.showAndWait();
            ;
        }
    }
}
