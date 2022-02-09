package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Modify Product class. Implements Initializable interface.
 */
public class ModifyPart implements Initializable {
    public TextField updatePartId;
    public TextField updatePartName;
    public TextField updatePartStock;
    public TextField updatePartPrice;
    public TextField updatePartMax;
    public TextField updatePartMin;
    public TextField updatePartMachine;
    public RadioButton inHouse;
    public RadioButton outsourced;
    public ToggleGroup partModifySource;
    public Label changeLabel;

    /**
     * Cancels any changes made to a part and returns to main menu.
     * @param event event to take place when cancel is pressed.
     * @throws IOException throws exception if MainForm.fxml cannot be found.
     */
    public void cancelButton(ActionEvent event) throws IOException {
        Alert cancelModify = new Alert(Alert.AlertType.CONFIRMATION, "By cancelling, all changes will not be saved. Continue?? ");
        Optional<ButtonType> selection = cancelModify.showAndWait();
        if (selection.isPresent() && selection.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    /**
     * Changes the label between machine ID and company name depending which button is pressed.
     * @param event event to take place when a radio selection is made or changed.
     */
    public void radioSelection(ActionEvent event) {
        if (outsourced.isSelected()) {
            changeLabel.setText("Company Name");
        } else {
            changeLabel.setText("Machine ID");
        }
    }

    /**
     * Saves the changes to a part and returns to main menu
     * @param event event to take place when save is pressed
     * @throws IOException throws exception if MainForm.fxml cannot be found.
     */
    public void savePartUpdate(ActionEvent event) throws IOException {
        try {
            if (Integer.parseInt(updatePartMin.getText()) > Integer.parseInt(updatePartMax.getText())) {
                Alert minError = new Alert(Alert.AlertType.ERROR);
                minError.setContentText("Min field cannot be more than Max field");
                minError.showAndWait();
                return;
            } else if (Integer.parseInt(updatePartStock.getText()) > Integer.parseInt(updatePartMax.getText())) {
                Alert stockError = new Alert(Alert.AlertType.ERROR);
                stockError.setContentText("QTY on hand cannot be more than MAX inventory");
                stockError.showAndWait();
                return;
            } else if (Integer.parseInt(updatePartStock.getText()) < Integer.parseInt(updatePartMin.getText())) {
                Alert stockError = new Alert(Alert.AlertType.ERROR);
                stockError.setContentText("QTY on hand cannot be less than MIN inventory");
                stockError.showAndWait();
                return;
            }

            int id = Integer.parseInt(updatePartId.getText());
            String name = updatePartName.getText();
            double price = Double.parseDouble(updatePartPrice.getText());
            int stock = Integer.parseInt(updatePartStock.getText());
            int min = Integer.parseInt(updatePartMin.getText());
            int max = Integer.parseInt(updatePartMax.getText());
            int index = 0;
            for (Part part : Inventory.getAllParts())
                if (part.getId() == Integer.parseInt(updatePartId.getText())) {
                    break;
                } else {
                    index++;
                }
            if (inHouse.isSelected()) {
                int machineId = Integer.parseInt(updatePartMachine.getText());
                InHouse part = new InHouse(id, name, price, stock, min, max, machineId);
                Inventory.updatePart(index, part);
            } else {
                String companyName = updatePartMachine.getText();
                Outsourced part = new Outsourced(id, name, price, stock, min, max, companyName);
                part.setCompanyName(companyName);
                Inventory.updatePart(index, part);
            }
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NumberFormatException e) {
            Alert invalidEntries = new Alert(Alert.AlertType.ERROR);
            invalidEntries.setContentText("Must enter a valid value in ALL fields");
            invalidEntries.showAndWait();
        }
    }

    /**
     * Transfers information from the selected part to the Modify Part text fields.
     * @param part the part you want to modify.
     */
    public void transferParts(Part part) {
        updatePartId.setText(String.valueOf(part.getId()));
        updatePartName.setText(part.getName());
        updatePartPrice.setText(String.valueOf(part.getPrice()));
        updatePartStock.setText(String.valueOf(part.getStock()));
        updatePartMax.setText(String.valueOf(part.getMax()));
        updatePartMin.setText(String.valueOf(part.getMin()));
        if (part instanceof InHouse) {
            updatePartMachine.setText(String.valueOf(((InHouse) part).getMachineId()));
            inHouse.setSelected(true);
        }
        else {
            updatePartMachine.setText(((Outsourced) part).getCompanyName());
            outsourced.setSelected(true);
            changeLabel.setText("Company Name");

        }
    }

    /**
     * initializes the modify part scene. Sets InHouse radio selection to true upon initialization.
     * @param location location to use when initialized.
     * @param resources resources to use when initialized.
     */
    @Override
    public void initialize (URL location, ResourceBundle resources) {
    }

}