package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Main;
import model.Outsourced;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * the AddPart class. implements Initializable interface.
 */
public class AddPart implements Initializable {
    public RadioButton inHousePart;
    public RadioButton outsourcedPart;
    public ToggleGroup partSource;
    public Label changeLabel;
    public TextField partIdField;
    public TextField partNameField;
    public TextField partStockField;
    public TextField partPriceField;
    public TextField partMachineId;
    public TextField partMaxField;
    public TextField partMinField;

    /**
     * cancels addPart screen and returns to main menu.
     * @param event the event that takes place when cancel is pressed.
     * @throws IOException throws exception if resource cannot be found for MainForm.fxml.
     */
    public void cancelButton(ActionEvent event) throws IOException {
        Alert cancelAdd = new Alert(Alert.AlertType.CONFIRMATION, "By cancelling, all changes will not be saved. Continue??");
        Optional<ButtonType> selection = cancelAdd.showAndWait();
        if (selection.isPresent() && selection.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    /**
     * changes label from machine id to company name depending on which radio button is selected.
     * @param event the event that takes place when a radio selection is made.
     */
    public void radioSelection(ActionEvent event) {
        if (outsourcedPart.isSelected())
            changeLabel.setText("Company Name");
        else
            changeLabel.setText("Machine ID");
    }

    /**
     * Saves the added part to the parts table.
     * @param event the event that takes place when save is pressed.
     * @throws IOException throws exception if resource to main form is not found.
     */
    public void savePartButton(ActionEvent event) throws IOException {

        try {
            if (Integer.parseInt(partMinField.getText()) > Integer.parseInt(partMaxField.getText())) {
                Alert minError = new Alert(Alert.AlertType.ERROR);
                minError.setContentText("Min field cannot be more than Max field");
                minError.showAndWait();
                return;
            } else if (Integer.parseInt(partStockField.getText()) > Integer.parseInt(partMaxField.getText())) {
                Alert stockError = new Alert(Alert.AlertType.ERROR);
                stockError.setContentText("QTY on hand cannot be more than MAX inventory");
                stockError.showAndWait();
                return;
            } else if (Integer.parseInt(partStockField.getText()) < Integer.parseInt(partMinField.getText())) {
                Alert stockError = new Alert(Alert.AlertType.ERROR);
                stockError.setContentText("QTY on hand cannot be less than MIN inventory");
                stockError.showAndWait();
                return;
            }

            String name = partNameField.getText();
            double price = Double.parseDouble(partPriceField.getText());
            int stock = Integer.parseInt(partStockField.getText());
            int min = Integer.parseInt(partMinField.getText());
            int max = Integer.parseInt(partMaxField.getText());
            int id = Main.partIdNumber;
            Main.partIdNumber += 1;
            if (inHousePart.isSelected()) {
                int machineId = Integer.parseInt(partMachineId.getText());
                InHouse part = new InHouse(id, name, price, stock, min, max, machineId);
                Inventory.addPart(part);
                part.setMachineId(machineId);
            } else {
                String companyName = partMachineId.getText();
                Outsourced part = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.addPart(part);
                part.setCompanyName(companyName);
            }
            Stage scene = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            scene.setScene(new Scene(root));
            scene.show();
        }
        catch (NumberFormatException e) {
            Alert invalidEntries = new Alert(Alert.AlertType.ERROR);
            invalidEntries.setContentText("Must enter a valid value in ALL fields");
            invalidEntries.showAndWait();
        }

    }

    /**
     * Initializes Add Part scene. Sets the InHouse radio button to true upon initialization.
     * @param location location to use when initializing.
     * @param resources resources to use when initializing.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inHousePart.setSelected(true);
    }
}
