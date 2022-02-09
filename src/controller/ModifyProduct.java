package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Modify Product class. Implements Initializable interface.
 */
public class ModifyProduct implements Initializable {
    public ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();
    public TextField updateProductId;
    public TextField updateProductName;
    public TextField updateProductStock;
    public TextField updateProductPrice;
    public TextField updateProductMax;
    public TextField updateProductMin;
    public TableView<Part> partTable;
    public TableColumn<Part, Integer> partId;
    public TableColumn<Part, String> partName;
    public TableColumn<Part, Integer> partStock;
    public TableColumn<Part, Double> partPrice;
    public TableView<Part> associatedPartTable;
    public TableColumn<Part, Integer> associatedPartId;
    public TableColumn<Part, String> associatedPartName;
    public TableColumn<Part, Integer> associatedPartStock;
    public TableColumn<Part, Double> associatedPartPrice;
    public TextField searchPartField;

    /**
     * cancels any changes made to a product and returns to main scene.
     * @param event event to take place when cancel is pressed.
     * @throws IOException throws exception if MainForm.fxml cannot be found.
     */
    public void cancelButton(ActionEvent event) throws IOException {
        Alert cancelModify = new Alert(Alert.AlertType.CONFIRMATION, "By cancelling, all changes will not be saved. Continue??");
        Optional<ButtonType> selection = cancelModify.showAndWait();
        if (selection.isPresent() && selection.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    /**
     * Saves changes made to a product and returns to main scene.
     * @param event event to take place when save is pressed.
     * @throws IOException throws exception if MainForm.fxml cannot be found.
     */
    public void saveProductUpdate(ActionEvent event) throws IOException {
        try {
            if (Integer.parseInt(updateProductMin.getText()) > Integer.parseInt(updateProductMax.getText())) {
                Alert minError = new Alert(Alert.AlertType.ERROR);
                minError.setContentText("Min field cannot be more than Max field");
                minError.showAndWait();
                return;
            } else if (Integer.parseInt(updateProductStock.getText()) > Integer.parseInt(updateProductMax.getText())) {
                Alert stockError = new Alert(Alert.AlertType.ERROR);
                stockError.setContentText("QTY on hand cannot be more than MAX inventory");
                stockError.showAndWait();
                return;
            } else if (Integer.parseInt(updateProductStock.getText()) < Integer.parseInt(updateProductMin.getText())) {
                Alert stockError = new Alert(Alert.AlertType.ERROR);
                stockError.setContentText("QTY on hand cannot be less than MIN inventory");
                stockError.showAndWait();
                return;
            }

            int index = 0;
            for (Product product : Inventory.getAllProducts())
                if (product.getId() == Integer.parseInt(updateProductId.getText())) {
                    break;
                } else {
                    index++;
                }

            int id = Integer.parseInt(updateProductId.getText());
            String name = updateProductName.getText();
            double price = Double.parseDouble(updateProductPrice.getText());
            int stock = Integer.parseInt(updateProductStock.getText());
            int min = Integer.parseInt(updateProductMin.getText());
            int max = Integer.parseInt(updateProductMax.getText());

            Product updateProduct = new Product(id, name, price, stock, min, max);
            Inventory.updateProduct(index, updateProduct);
            for (Part part : associatedPartsList) {
                updateProduct.addAssociatedPart(part);
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
     * Adds an associated part to a product.
     * @param event event to take place when Add Associated Part is pressed.
     */
    public void addAssociatedPart(ActionEvent event) {
        Part associatedPart = partTable.getSelectionModel().getSelectedItem();
        if (associatedPart != null) {
            associatedPartsList.add(associatedPart);
        }
    }

    /**
     * Removes an associated part from a product.
     * @param event event to take place when Remove Associated Part is pressed.
     */
    public void removeAssociatedPart(ActionEvent event) {
        Alert confirmRemove = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove the associate part??");
        Optional<ButtonType> selection = confirmRemove.showAndWait();
        if (selection.isPresent() && selection.get() == ButtonType.OK) {
            Part associatedPart = associatedPartTable.getSelectionModel().getSelectedItem();
            associatedPartsList.remove(associatedPart);
        }
    }

    /**
     * Searches for a part in the parts table in order to add a desired associated part to your product.
     * @param event event to take place when a search is performed.
     */
    public void searchPart(ActionEvent event) {
        try {
            String search = searchPartField.getText();
            ObservableList<Part> parts = Inventory.lookupPart(search);

            if (parts.size() == 0) {
                int partId = Integer.parseInt(search);
                if (Inventory.lookupPart(partId) == null) {
                    Alert notFound = new Alert(Alert.AlertType.WARNING);
                    notFound.setContentText("Part(s) not found. Try again");
                    notFound.showAndWait();
                    return;
                }
                parts.add(Inventory.lookupPart(partId));
            }
            partTable.setItems(parts);
        }
        catch (NumberFormatException e) {
            Alert notFound = new Alert(Alert.AlertType.WARNING);
            notFound.setContentText("No part(s) found. Try again");
            notFound.showAndWait();
        }
    }

    /**
     * Transfers information from selected part to the update Product text fields.
     * @param product the product you want to update.
     */
    public void transferProducts(Product product) {
        updateProductId.setText(String.valueOf(product.getId()));
        updateProductName.setText(product.getName());
        updateProductPrice.setText(String.valueOf(product.getPrice()));
        updateProductStock.setText(String.valueOf(product.getStock()));
        updateProductMax.setText(String.valueOf(product.getMax()));
        updateProductMin.setText(String.valueOf(product.getMin()));
        for (Part part : product.getAllAssociatedParts()) {
            associatedPartsList.add(part);
        }
    }

    /**
     * Initializes the Modify Product scene. Populates the parts table as well as any existing associated parts.
     * @param location location to use when initializing.
     * @param resources resources to use upon initialization.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        partTable.setItems(Inventory.getAllParts());
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartTable.setItems(associatedPartsList);
        associatedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}