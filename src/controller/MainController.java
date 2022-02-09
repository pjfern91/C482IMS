package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
 * Main Form controller class. Implements Initializable interface.
 */
public class MainController implements Initializable {
    public Label imsLabel;
    public TableView<Part> partTable;
    public TableColumn<Part, Integer> partIdCol;
    public TableColumn<Part, String> partNameCol;
    public TableColumn<Part, Integer> partStockCol;
    public TableColumn<Part, Double> partPriceCol;
    public TableView<Product> productTable;
    public TableColumn<Product, Integer> productIdCol;
    public TableColumn<Product, String> productNameCol;
    public TableColumn<Product, Integer> productStockCol;
    public TableColumn<Product, Double> productPriceCol;
    public TextField searchPartField;
    public TextField searchProductField;
    public Button addPart;
    public Button addProduct;

    /**
     * Adds a part to the parts table.
     * @param event event to take place if Add is pressed in the parts table.
     * @throws IOException throws exception if AddPart.fxml cannot be found.
     */
    public void addPartButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Add a Part");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Updates selected part information.
     * @param event event to take place if update part is pressed in the parts table.
     * @throws IOException throws exception if ModifyPart.fxml cannot be found.
     */
    public void updatePart(ActionEvent event) throws IOException {

        try {
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
            load.load();

            ModifyPart modifyPart = load.getController();
            modifyPart.transferParts(partTable.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = load.getRoot();
            stage.setScene(new Scene(scene));
            stage.setTitle("Update Part information");
            stage.show();
        }
        catch (NullPointerException e) {
            Alert warning = new Alert(Alert.AlertType.ERROR);
            warning.setContentText("Must select a part to modify first.");
            warning.showAndWait();
        }
    }

    /**
     * Deletes a selected part from the parts table.
     * @param event event that takes place when delete is pressed in the parts table.
     */
    public void deletePart(ActionEvent event) {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected part??");
        Optional<ButtonType> selection = confirmDelete.showAndWait();
        if (selection.isPresent() && selection.get() == ButtonType.OK) {
            Inventory.deletePart(selectedPart);
        }
    }

    /**
     * adds a product to the product table.
     * @param event event that takes place when add is pressed in the product table
     * @throws IOException throws exception if AddProduct.fxml cannot be found
     */
    public void addProductButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Add a Product");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Updates a selected product.
     * @param event event to take place when update is pressed in the product table.
     * @throws IOException throws exception is ModifyProduct.fxml cannot be found.
     */
    public void updateProduct(ActionEvent event) throws IOException {
        try {
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
            load.load();

            ModifyProduct modifyProduct = load.getController();
            modifyProduct.transferProducts(productTable.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = load.getRoot();
            stage.setScene(new Scene(scene));
            stage.setTitle("Update Product information");
            stage.show();
        }
        catch (NullPointerException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Must select a product to modify first.");
            error.showAndWait();
        }
    }

    /**
     * deletes a product from the product table.
     * @param event event to take place when delete is pressed
     */
    public void deleteProduct(ActionEvent event) {
        Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected product??");
        Optional<ButtonType> selection = confirmDelete.showAndWait();
        if (selection.isPresent() && selection.get() == ButtonType.OK) {
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
            if (selectedProduct.getAllAssociatedParts().size() > 0) {
                Alert deleteNotAllowed = new Alert(Alert.AlertType.ERROR);
                deleteNotAllowed.setContentText("Cannot delete a product associated to a part(s).");
                deleteNotAllowed.showAndWait();
                return;
            }
            Inventory.deleteProduct(selectedProduct);
        }
    }

    /**
     * searches for a part by id or substring of name
     * @param event event to take place when search is performed
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
     * Searches for a product by ID or substring of name.
     * @param event event to take place when a search is performed.
     */
    public void searchProduct(ActionEvent event) {
        try {
            String search = searchProductField.getText();
            ObservableList<Product> products = Inventory.lookupProduct(search);

            if (products.size() == 0) {
                int prodId = Integer.parseInt(search);
                if (Inventory.lookupProduct(prodId) == null) {
                    Alert notFound = new Alert(Alert.AlertType.WARNING);
                    notFound.setContentText("Product(s) not found. Try again.");
                    notFound.showAndWait();
                    return;
                }
                products.add(Inventory.lookupProduct(prodId));
            }
            productTable.setItems(products);
        }
        catch (NumberFormatException e) {
            Alert notFound = new Alert(Alert.AlertType.WARNING);
            notFound.setContentText("No product(s) found. Try again");
            notFound.showAndWait();
        }
    }

    /**
     * closes the application
     * @param event event to take place when exit is pressed
     */
    public void closeApp(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Initializes the MainForm scene. Populates the Parts and Product Tables
     * @param location location to use when initializing
     * @param resources resources to use upon initialization
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        partTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
