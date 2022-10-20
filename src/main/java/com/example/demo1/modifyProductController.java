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

/**This is a class that will be used when the user wants to modify a product*/
public class modifyProductController implements Initializable {
    /**
     * The selected product to modify will be called prodToModify
     */
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

    /**@param actionEvent
     * Method to add a part to the selectedPartsTable on the bottom of the modify product form
     */
    public void prodAdd(ActionEvent actionEvent) {
        Part partToAdd = (Part) modPartsTable.getSelectionModel().getSelectedItem();
        selectedOptions.add(partToAdd);
        selectedPartsTable.setItems(selectedOptions);
    }

    /**@param product
     * Set the modify product input fields to the fields of the product that was selected
     */
    public void setProdInputs(Product product) {
        modIDInput.setText(Integer.toString(product.getId()));
        modNameInput.setText(product.getName());
        modPriceInput.setText(Double.toString(product.getPrice()));
        modInvInput.setText(Integer.toString(product.getStock()));
        modMaxInput.setText(Integer.toString(product.getMax()));
        modMinInput.setText(Integer.toString(product.getMin()));

        //fill the lower table with the associated parts for the product selected
        selectedPartsTable.setItems(product.getAllAssociatedParts());

    }

    /**@param actionEvent
     * this is a method so that when people are modifying a product they can remove some of the previously selected parts
     */
    public void modRemove(ActionEvent actionEvent) {
        Part partToDelete = (Part) selectedPartsTable.getSelectionModel().getSelectedItem();

        if (partToDelete == null) {
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

    /**@param actionEvent
     * Method so that when users finish modifying a product it will be saved
     */
    public void modSave(ActionEvent actionEvent) throws IOException {
        try {
            //get the ID of the selected part
            Product productToModify = MainScreenController.getProductToModify();
            int id = productToModify.getId();
            //Get the name of the selected part
            String name = modNameInput.getText();
            //Get the modified price
            Double price = Double.parseDouble(modPriceInput.getText());
            //Get the modified inventory
            int stock = Integer.parseInt(modInvInput.getText());
            //Get the modified min
            int min = Integer.parseInt(modMinInput.getText());
            //Get the input max
            int max = Integer.parseInt(modMaxInput.getText());
            //Verify max is larger than min

            if (min > max) {
                Alert minMax = new Alert(Alert.AlertType.ERROR);
                minMax.setContentText("Your minimum is larger than your maximum");
            } else {


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
        }
        } catch (NumberFormatException e) {
            Alert failure = new Alert(Alert.AlertType.ERROR);
            failure.setContentText("Error modifying product. Please ensure all fields are correct");
            failure.showAndWait();
        }

    }

    /**@param actionEvent
     * Method that brings the user back to the main screen if they press cancel
     */
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

    /**@param resourceBundle
     * Initialize method to import values into the tables
     */
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

    /**@param actionEvent
     * Method so that users are able to search for a part
     */
    public void searchForPart(ActionEvent actionEvent) {
        String searchedPart = modProdSearch.getText();
        if (searchedPart.isBlank()) {
            modPartsTable.setItems(Inventory.getAllParts());
        }
        //Grab all of the parts in teh inventory and put them in the partsList
        try {
            Part part = Inventory.lookupPart(Integer.parseInt(searchedPart));
            if (part != null) {
                modPartsTable.getSelectionModel().select(part);
            } else {
                //Todo handle null part
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            ObservableList<Part> matchingParts = Inventory.lookupPart(searchedPart);
            if (matchingParts.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("There are no matches for your search");
                //finish alert
                alert.showAndWait();
            } else {
                modPartsTable.setItems(matchingParts);
            }
        }

    }
}