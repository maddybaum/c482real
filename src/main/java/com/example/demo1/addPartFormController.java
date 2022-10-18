package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.demo1.Inventory.partId;
/**This class is the controller file for when a user has selected that they want to add a part*/
public class addPartFormController implements Initializable {
    public RadioButton inHouseRadioAdd;
    public ToggleGroup addPartTGroup;
    public RadioButton outsourcedRadioAdd;
    public Label addPartMachineIDText;
    public TextField addPartIDinput;
    public TextField addPartNameInput;
    public TextField addPartInvInput;
    public TextField addPartPCInput;
    public TextField addPartMaxInput;
    public TextField addPartMinInput;
    public TextField addPartMachIdInput;
    public TextField addPartCompIdInput;
    public Button addPartSaveBtn;
    public Button addPartCancelBtn;
/**Method for if user has selected the in house radio button, to change the label text to Machine ID*/
    public void onInHouseClicked(ActionEvent actionEvent) {
        addPartMachineIDText.setText("Machine ID");
    }
/**Method if user selects outsourced radio button, to change the label text to company ID*/
    public void onOutsourcedClicked(ActionEvent actionEvent) {
        addPartMachineIDText.setText("Company ID");
    }
/**Method to save the new part that the user has added*/
    public void saveNewPart(ActionEvent actionEvent) {
        try {

            //Grab the input name
            String newPartName = addPartNameInput.getText();
            //Grab the input inventory number and convert to an int
            int newPartInv = Integer.parseInt(addPartInvInput.getText());
            //Grab the input price and convert it into a double
            double newPartCost = Double.parseDouble(addPartPCInput.getText());
            //Grab the input max and minimum and convert from string into integer
            int newPartMax = Integer.parseInt(addPartMaxInput.getText());
            int newPartMin = Integer.parseInt(addPartMinInput.getText());
            int machineId;
            String companyName;

            //Check if the minimum and maximum are appropriate
            if(newPartMin > newPartMax){
                Alert minMax = new Alert(Alert.AlertType.ERROR);
                minMax.setContentText("Your minimum is larger than your maximum");
                Optional<ButtonType> response = minMax.showAndWait();
            }
            //If the inhouse radio button is selected, then we need to grab the integer for the machine ID
            if(inHouseRadioAdd.isSelected()) {
                machineId = Integer.parseInt(addPartCompIdInput.getText());
                InHouse partToAdd = new InHouse(partId, newPartName, newPartCost, newPartInv, newPartMax, newPartMin, machineId);
                partToAdd.setId(Inventory.getNewPartId());
                Inventory.addPart(partToAdd);
            }
            if(outsourcedRadioAdd.isSelected()){
                companyName = addPartMachIdInput.getText();
                Part partToAdd = new Outsourced(partId, newPartName, newPartCost,  newPartInv, newPartMax, newPartMin, companyName);
                partToAdd.setId(Inventory.getNewPartId());
                Inventory.addPart(partToAdd);
            }
            //Return to main window once part is added

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
            Alert failure = new Alert(Alert.AlertType.ERROR);
            failure.setContentText("Error adding part. Please make sure all values are correct");
            Optional<ButtonType> response = failure.showAndWait();
            throw new RuntimeException(e);
        }
    }
/**If the user selects cancel from the add part page, this will take them back to the main page*/
    public void onAddCancelClicked(ActionEvent actionEvent) throws IOException {
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

    }
}
