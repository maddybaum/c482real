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

/**
 * This class is the controller file for when a user has selected that they want to add a part
 */
public class addPartFormController implements Initializable {
    /**
     * The In house radio add.
     */
    public RadioButton inHouseRadioAdd;
    /**
     * The Add part t group.
     */
    public ToggleGroup addPartTGroup;
    /**
     * The Outsourced radio add.
     */
    public RadioButton outsourcedRadioAdd;
    /**
     * The Add part machine id text.
     */
    public Label addPartMachineIDText;
    /**
     * The Add part i dinput.
     */
    public TextField addPartIDinput;
    /**
     * The Add part name input.
     */
    public TextField addPartNameInput;
    /**
     * The Add part inv input.
     */
    public TextField addPartInvInput;
    /**
     * The Add part pc input.
     */
    public TextField addPartPCInput;
    /**
     * The Add part max input.
     */
    public TextField addPartMaxInput;
    /**
     * The Add part min input.
     */
    public TextField addPartMinInput;
    /**
     * The Add part mach id input.
     */
    public TextField addPartMachIdInput;
    /**
     * The Add part comp id input.
     */
    public TextField addPartCompIdInput;
    /**
     * The Add part save btn.
     */
    public Button addPartSaveBtn;
    /**
     * The Add part cancel btn.
     */
    public Button addPartCancelBtn;

    /**@param actionEvent
     * Method for if user has selected the in house radio button, to change the label text to Machine ID @param actionEvent the action event
     */
    public void onInHouseClicked(ActionEvent actionEvent) {
        addPartMachineIDText.setText("Machine ID");
    }

    /**@param actionEvent
     * Method if user selects outsourced radio button, to change the label text to company ID @param actionEvent the action event
     */
    public void onOutsourcedClicked(ActionEvent actionEvent) {
        addPartMachineIDText.setText("Company ID");
    }

    /**
     * @param actionEvent
     * Method to save the new part that the user has added
     * <p>
     * RUNTIME ERROR
     * I had a runtime error with saving a new part because I wanted to get the proper type validation to work
     * but also needed to have the IOException in order to load the main screen after saving. Initially, I had all of the
     * code in my try block, with no exception thrown in the main method. This meant that my catch block needed
     * to have the IOException error along with the NumberFormatException, which I was not able to do. I had a lot of challenges
     * figuring out what to do, because every time I tried to save a new part with incorrect values, my
     * entire application would crash. I was able to meet with my course instructor who explained that I should
     * do my best to shorten the try block, and then throw the IOException error in the method declaration,
     * and the numberformat exception in the catch block.  @param actionEvent the action event
     *
     * @throws IOException the io exception
     */
    public void saveNewPart(ActionEvent actionEvent) throws IOException {
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
            if (newPartMin > newPartMax) {
                Alert minMax = new Alert(Alert.AlertType.ERROR);
                minMax.setContentText("Your minimum is larger than your maximum");
                Optional<ButtonType> response = minMax.showAndWait();

            } else {
                //If the inhouse radio button is selected, then we need to grab the integer for the machine ID
                if (inHouseRadioAdd.isSelected()) {
                    machineId = Integer.parseInt(addPartCompIdInput.getText());
                    InHouse partToAdd = new InHouse(partId, newPartName, newPartCost, newPartInv, newPartMax, newPartMin, machineId);
                    partToAdd.setId(Inventory.getNewPartId());
                    Inventory.addPart(partToAdd);
                }
                if (outsourcedRadioAdd.isSelected()) {
                    companyName = addPartMachIdInput.getText();
                    Part partToAdd = new Outsourced(partId, newPartName, newPartCost, newPartInv, newPartMax, newPartMin, companyName);
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
            }

        }
             catch (NumberFormatException e){
                Alert failure = new Alert(Alert.AlertType.ERROR);
                failure.setContentText("Please make sure all values are correct");
                failure.showAndWait();
            }
        }


    /**
     * @param actionEvent
     * If the user selects cancel from the add part page, this will take them back to the main page @param actionEvent the action event
     *
     * @throws IOException the io exception
     */
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
