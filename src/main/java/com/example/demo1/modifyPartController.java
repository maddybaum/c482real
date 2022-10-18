package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
/**Creating the controller for when a user would like to modify part*/
public class modifyPartController {
    public RadioButton inHouseRadio;
    public ToggleGroup addPartTGroup;
    public RadioButton outsourcedRadioModify;
    public TextField modPartIDinput;
    public TextField modPartNameInput;
    public TextField modPartInvInput;
    public TextField modPartPCInput;
    public TextField modPartMaxInput;
    public TextField modPartMinInput;
    public TextField modPartMachIdInput;
    public Button modPartSaveBtn;
    public Button modPartCancelBtn;
    public Label machineID;

    /**Method for if the product is an inhouse product, then set the text for the label to machine ID*/
    public void ModOnInHouse(ActionEvent actionEvent) {
        machineID.setText("Machine ID");
    }
    /**If outsourced radio button is selected then change text to Company ID*/

    public void ModOnOutsourced(ActionEvent actionEvent) {
        machineID.setText("Company ID");

    }
    /**This method brings the values from the part that the user is wanting to modify so they are pre filled when the window opens*/
    public void setInputs(Part part){
        System.out.println(part.getName());
        modPartIDinput.setText(Integer.toString(part.getId()));
        modPartNameInput.setText(part.getName());
        modPartInvInput.setText(Integer.toString(part.getStock()));
        modPartPCInput.setText(Double.toString(part.getPrice()));
        modPartMinInput.setText(Integer.toString(part.getMin()));
        modPartMaxInput.setText(Integer.toString(part.getMax()));

        if(part instanceof Outsourced){
            outsourcedRadioModify.setSelected(true);
            machineID.setText("Company ID");
            modPartMachIdInput.setText(((Outsourced) part).getCompanyName());
        }
        if(part instanceof InHouse){
            outsourcedRadioModify.setSelected(false);
            machineID.setText("Machine ID");
            modPartMachIdInput.setText(String.valueOf(((InHouse) part).getMachineID()));
        }
    }
    /**This method allows the user to save the newly modified part*/
    public void saveModifyPart(ActionEvent actionEvent) {
        try {
            //Grab the selected part from the main screen and then use that same ID
            Part partToMod = MainScreenController.getPartToModify();
            int id = partToMod.getId();

            //Grab the input name
            String newPartName = modPartNameInput.getText();
            //Grab the input inventory number and convert to an int
            int newPartInv = Integer.parseInt(modPartInvInput.getText());
            //Grab the input price and convert it into a double
            double newPartCost = Double.parseDouble(modPartPCInput.getText());
            //Grab the input max and minimum and convert from string into integer
            int newPartMax = Integer.parseInt(modPartMaxInput.getText());
            int newPartMin = Integer.parseInt(modPartMinInput.getText());
            int machineId;
            String companyName;

            //Check if the minimum and maximum are appropriate
            if(newPartMin > newPartMax){
                Alert minMax = new Alert(Alert.AlertType.ERROR);
                minMax.setContentText("Your minimum is larger than your maximum");
                Optional<ButtonType> response = minMax.showAndWait();
            }
            //If the inhouse radio button is selected, then we need to grab the integer for the machine ID
            if(inHouseRadio.isSelected()){
                machineId = Integer.parseInt(modPartMachIdInput.getText());
                Part partToAdd = new InHouse(id, newPartName, newPartCost, newPartInv,  newPartMax, newPartMin, machineId);
                Inventory.deletePart(partToMod);
                Inventory.addPart(partToAdd);
            }
            if(outsourcedRadioModify.isSelected()){
                companyName = modPartMachIdInput.getText();
                Part partToAdd = new Outsourced(id, newPartName, newPartCost, newPartInv, newPartMax, newPartMin, companyName);
                Inventory.deletePart(partToMod);
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
            Alert noValue = new Alert(Alert.AlertType.ERROR);
            noValue.setContentText("Error adding part. Please check all values are correct");
            Optional<ButtonType> response = noValue.showAndWait();
            throw new RuntimeException(e);
        }
    }
/**This method will return to the main page if the user clicks cancel*/
    public void onModifyCancelClicked(ActionEvent actionEvent) throws IOException {
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
}
