package controller;

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
import model.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Add Product class. Implements Initializable interface.
 */
public class AddProduct implements Initializable {
    public TextField productIdField;
    public TextField productNameField;
    public TextField productStockField;
    public TextField productPriceField;
    public TextField productMaxField;
    public TextField productMinField;
    public TableView<Part> partTable;
    public TableView<Part> associatedPartTable;
    public TableColumn<Part, Integer> partId;
    public TableColumn<Part, String> partName;
    public TableColumn<Part, Integer> partStock;
    public TableColumn<Part, Double> partPrice;
    public TableColumn<Part, Integer> associatedPartId;
    public TableColumn<Part, String> associatedPartName;
    public TableColumn<Part, Integer> associatedPartStock;
    public TableColumn<Part, Double> associatedPartPrice;
    public ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    public TextField searchPartField;

    /**
     * cancels addProduct screen and returns to main menu.
     * @param event event that takes place when cancel is pressed.
     * @throws IOException throws exception if resource is not found for MainForm.fxml.
     */
    public void cancelButton(ActionEvent event) throws IOException {
        Alert cancelAdd = new Alert(Alert.AlertType.CONFIRMATION, "By cancelling, all entered information will be deleted. Continue??");
        Optional<ButtonType> selection = cancelAdd.showAndWait();
        if (selection.isPresent() && selection.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    /**
     * Saves all entered fields for a new product.
     * @param event event that takes place when save is pressed.
     * @throws IOException throws exception if resource for MainForm.fxml cannot be found.
     */
    public void saveProductButton(ActionEvent event) throws IOException {
        try {
            if (Integer.parseInt(productMinField.getText()) > Integer.parseInt(productMaxField.getText())) {
                Alert minError = new Alert(Alert.AlertType.ERROR);
                minError.setContentText("Minimum quantity cannot be more than the Max. Check your entries and try again.");
                minError.showAndWait();
                return;
            } else if (Integer.parseInt(productStockField.getText()) > Integer.parseInt(productMaxField.getText())) {
                Alert stockError = new Alert(Alert.AlertType.ERROR);
                stockError.setContentText("On hand amount cannot be greater than maximum QTY. Check your entries and try again.");
                stockError.showAndWait();
                return;
            } else if (Integer.parseInt(productStockField.getText()) < Integer.parseInt(productMinField.getText())) {
                Alert stockError = new Alert(Alert.AlertType.ERROR);
                stockError.setContentText("On hand amount cannot be less than minimum QTY. Check your entries and try again.");
                stockError.showAndWait();
                return;
            }
            int id = Main.prodIdNumber;
            Main.prodIdNumber++;
            String name = productNameField.getText();
            double price = Double.parseDouble(productPriceField.getText());
            int stock = Integer.parseInt(productStockField.getText());
            int max = Integer.parseInt(productMaxField.getText());
            int min = Integer.parseInt(productMinField.getText());
            Product product = new Product(id, name, price, stock, min, max);
            Inventory.addProduct(product);
            for (Part part : associatedParts) {
                product.addAssociatedPart(part);
            }
            Stage scene = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            scene.setScene(new Scene(root));
            scene.show();
        }
        catch (NumberFormatException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Must enter valid entries in all fields. Check contents and try again.");
            error.showAndWait();
        }
    }

    /**
     * adds an associated part to a product.
     * @param event event that takes place when Add Associated Part is pressed.
     */
    public void addAssociatedPart (ActionEvent event){
        Part associatedPart = partTable.getSelectionModel().getSelectedItem();
        if (associatedPart != null) {
            associatedParts.add(associatedPart);
        }
    }

    /**
     * removes an associated part from a product.
     * @param event event that takes place when Remove Associated Part is pressed.
     */
    public void removeAssociatedPart (ActionEvent event){
        Alert confirmRemove = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove the associated part??");
        Optional<ButtonType> selection = confirmRemove.showAndWait();
        if (selection.isPresent() && selection.get() == ButtonType.OK) {
            Part associatedPart = associatedPartTable.getSelectionModel().getSelectedItem();
            associatedParts.remove(associatedPart);
        }
    }

    /**
     * Searches for a part by either id or substring of a name.
     * @param event event that takes place when a search is performed.
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
     * Initializes Add Product scene. Populates parts table in order to add desired associated parts to a product.
     * @param location location to use upon initialization.
     * @param resources resources to use upon initialization.
     */
    @Override
    public void initialize (URL location, ResourceBundle resources){

        partTable.setItems(Inventory.getAllParts());
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartTable.setItems(associatedParts);
        associatedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
