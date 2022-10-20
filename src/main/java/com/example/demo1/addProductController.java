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

import static com.example.demo1.Inventory.lookupPart;

/**This is the controller for when a user has clicked to add a product and opened the addProductForm fxml file*/
public class addProductController implements Initializable {
    //Use an observable list to hold the items that the user has selected for the list
    private ObservableList<Part> selectedPartList = FXCollections.observableArrayList();

    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();

    public TextField addIDInput;
    public TextField addNameInput;
    public TextField addInvInput;
    public TextField addPriceInput;
    public TextField addMaxInput;
    public TextField addMinInput;
    public TextField addProdSearch;
    public TableView partTableOptions;
    public TableColumn optionsPartID;
    public TableColumn optionsPartName;
    public TableColumn optionsInventoryLvl;
    public TableColumn optionsCPU;
    public TableView partTableSelected;
    public TableColumn selectedID;
    public TableColumn selectedName;
    public TableColumn selectedInvLvl;
    public TableColumn selectedCPU;
    public Button addProdAddBtn;
    public Button addProdRemoveBtn;
    public Button addProdSaveBtn;
    public Button addProdCancelBtn;
/**@param actionEvent
 * This method searched for a part based on what the user has type in*/
    public void searchForPart(ActionEvent actionEvent) {
        //Grab text that was typed in search bar

        String searchedPart = addProdSearch.getText();
        partTableOptions.setItems((lookupPart(searchedPart)));

    }
/**@param actionEvent
 * Method for adding a part and displaying it in the table*/
    public void addPart(ActionEvent actionEvent) {
        //Grab the currently selected part
        Part partToAdd = (Part) partTableOptions.getSelectionModel().getSelectedItem();
        associatedPartsList.add(partToAdd);

        //Display the list of parts in the table
    }
/**@param actionEvent
 * Method to delete a part if the button is clicked*/
    public void removeProduct(ActionEvent actionEvent) {
        //Grab the selected item from the parts the user has already selected
        Part partToDelete = (Part)partTableSelected.getSelectionModel().getSelectedItem();
        //If user did not select anything to remove then display error
        if(partToDelete == null){
            Alert noValue = new Alert(Alert.AlertType.ERROR);
            noValue.setContentText("There is no value to delete");
            Optional<ButtonType> response = noValue.showAndWait();
            return;
        }
        //Otherwise remove the part to delete
        associatedPartsList.remove(partToDelete);
        //Display the list of parts with the selected one removed

    }
/**@param actionEvent
 * Once the user has filled in the input, this method will allow them to save the new product
 * FUTURE ENHANCEMENT would be a feature that would mark whether all of the parts are in stock for the product that is being
 * added. So when a user wants to add a product that needs the parts: string, ball and feather... if one or more of those is out
 * of stock, then when they add the product the new product shows up in red or with some sort of alert to say that not all of the
 * parts are currently in stock.*/
    public void saveAddProd(ActionEvent actionEvent) throws IOException {
        try {
            int id = (int)Math.random()*1000;
            //Grab all of the inputs from the user
            String name = addNameInput.getText();
            int stock = Integer.parseInt(addInvInput.getText());
            double price = Double.parseDouble(addPriceInput.getText());
            int max = Integer.parseInt(addMaxInput.getText());
            int min = Integer.parseInt(addMinInput.getText());

            if(min > max){
                Alert minMax = new Alert(Alert.AlertType.ERROR);
                minMax.setContentText("Your minimum is larger than your maximum");
                Optional<ButtonType> response = minMax.showAndWait();
            }
            //Create a new product object with the values the user input
            else {
                Product product = new Product(id, name, price, stock, max, min);
                product.setId(Inventory.getNewProductId());
                product.getAllAssociatedParts().addAll(associatedPartsList);
                //Add new product to inventory
                //Loop through the parts and use the addAssocPart method from product to add the parts

                Inventory.addProduct(product);

                Parent mainModal = FXMLLoader.load(getClass().getResource("main.fxml"));
                Scene scene = new Scene(mainModal);
                Stage modal = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                modal.setScene(scene);
                modal.show();
            }
        } catch (NumberFormatException e) {
            Alert failure = new Alert(Alert.AlertType.ERROR);
            failure.setContentText("Please make sure all values are correct");
            failure.showAndWait();
        }

    }
/**@param actionEvent
 * If the user selects cancel, this will bring them back to the main screen*/
    public void addProdCancel(ActionEvent actionEvent) throws IOException {
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
 * Sets the values in the 2 tables on the addProduct page*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Put all of the parts that have been added in the options table
        partTableOptions.setItems(Inventory.getAllParts());
        //add all of the parts to the options table
        optionsPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        optionsPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        optionsCPU.setCellValueFactory(new PropertyValueFactory<>("price"));
        optionsInventoryLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));

        //add the parts that have been selected to the selected parts table
        partTableSelected.setItems(associatedPartsList);
        selectedID.setCellValueFactory(new PropertyValueFactory<>("id"));
        selectedName.setCellValueFactory(new PropertyValueFactory<>("name"));
        selectedCPU.setCellValueFactory(new PropertyValueFactory<>("price"));
        selectedInvLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }
/**@param actionEvent
 * method to search parts by first searching if the search was an ID
 * if not then searching if it was part of a part name*/
    public void searchParts(ActionEvent actionEvent) {

        String searchedPart = addProdSearch.getText();
        if(searchedPart.isBlank()){
            partTableOptions.setItems(Inventory.getAllParts());
        }
        //Grab all of the parts in teh inventory and put them in the partsList
        try {
            Part part = Inventory.lookupPart(Integer.parseInt(searchedPart));
            if(part != null){
                partTableOptions.getSelectionModel().select(part);
            } else {
                //Todo handle null part
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            ObservableList<Part> matchingParts = Inventory.lookupPart(searchedPart);
            if(matchingParts.size() == 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                //finish alert
                alert.showAndWait();
            }
            else {
                partTableOptions.setItems(matchingParts);
            }
        }
    }
}
