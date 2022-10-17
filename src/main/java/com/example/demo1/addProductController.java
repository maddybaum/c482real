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

public class addProductController implements Initializable {
    //Use an observable list to hold the items that the user has selected for the list
    private ObservableList<Part> selectedPartList = FXCollections.observableArrayList();
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

    public void searchForPart(ActionEvent actionEvent) {
        //Grab text that was typed in search bar
        String searchedPart = addProdSearch.getText();
        //Grab all of the parts the inventory and put them in the partsList
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
        partTableOptions.setItems(matchingParts);
        //If there are no matching parts, the list size will be 0, so display alert that there are no matching parts
        if(matchingParts.size() == 0){
            Alert noSearch = new Alert(Alert.AlertType.ERROR);
            noSearch.setContentText("There are no matches for your search terms");
            Optional<ButtonType> response = noSearch.showAndWait();
        }
    }

    public void addPart(ActionEvent actionEvent) {
        //Grab the currently selected part
        Part partToAdd = (Part) partTableOptions.getSelectionModel().getSelectedItem();
        selectedPartList.add(partToAdd);
        //Display the list of parts in the table
        partTableSelected.setItems(selectedPartList);
    }

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
        selectedPartList.remove(partToDelete);
        //Display the list of parts with the selected one removed
        partTableSelected.setItems(selectedPartList);
    }

    public void saveAddProd(ActionEvent actionEvent) {
        try {
            //Using method suggested in webinar to generate a random ID that will not overlap with others
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
            Product product = new Product(id, name, price, stock, max, min);
            //Add new product to inventory
            //Loop through the parts and use the addAssocPart method from product to add the parts
            for(Part part : selectedPartList){
                product.addAssociatedParts(part);
            }
            Inventory.addProduct(product);
            Inventory.getAllProducts();

            //return to main page
            Parent mainModal = FXMLLoader.load(getClass().getResource("main.fxml"));
            //set new scene with add part modal
            Scene scene = new Scene(mainModal);
            //set stage of the modal
            Stage modal = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            //put add part modal inside
            modal.setScene(scene);
            //show the modal
            modal.show();
        } catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("There was an error adding product");
            Optional<ButtonType> response = error.showAndWait();
            return;
        }
    }

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
        partTableSelected.setItems(selectedPartList);
        selectedID.setCellValueFactory(new PropertyValueFactory<>("id"));
        selectedName.setCellValueFactory(new PropertyValueFactory<>("name"));
        selectedCPU.setCellValueFactory(new PropertyValueFactory<>("price"));
        selectedInvLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
}
