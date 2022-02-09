package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *Inventory class
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * adds a part to the allParts list
     * @param newPart the part to add
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * searches part by partID
     * @param partId the id of the part you want to search
     * @return returns part with ID match
     */
    public static Part lookupPart(int partId) {
        for (Part parts : allParts) {
            if (parts.getId() == partId) {
                return parts;
            }
        }
        return null;
    }

    /**
     * searches allParts list by name or partial name
     * @param partName the part name to search
     * @return returns part or parts with matching name or substring of name
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> results = FXCollections.observableArrayList();
        allParts = Inventory.getAllParts();
        for (Part parts : allParts) {
            if (parts.getName().contains(partName)) {
                results.add(parts);
            }
        }
        return results;
    }

    /**
     * modifies selected part information
     * @param index the index of the part to update
     * @param selectedPart the part to update
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * deletes selected part from allParts list
     * @param selectedPart the part to delete
     * @return returns true if selection deleted, otherwise false
     */
    public static boolean deletePart(Part selectedPart) {
        for (Part parts : allParts) {
            if (parts.equals(selectedPart)) {
                allParts.remove(selectedPart);
                return true;
            }
        }
        return false;
    }

    /**
     * @return returns list of all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * adds a product to the allProducts list
     * @param newProduct the product to add
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * searches product by productID
     * @param productId the id of the product you want to search
     * @return returns product with matching ID
     */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * searches allProducts list by name or partial name
     * @param productName the name of the product to search
     * @return returns name or names with matching substrings of name
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> results = FXCollections.observableArrayList();
        allProducts = Inventory.getAllProducts();
        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                results.add(product);
            }
        }
        return results;
    }

    /**
     * modifies selected product information
     * @param index the index of the product to update
     * @param selectedProduct the product to update
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * deletes selected product from allProducts list
     * @param selectedProduct the product you want to delete
     * @return returns true if deleted, otherwise false
     */
    public static boolean deleteProduct(Product selectedProduct) {
        for (Product product : allProducts) {
            if (product.equals(selectedProduct)) {
                allProducts.remove(selectedProduct);
                return true;
            }
        }
        return false;
    }

    /**
     * @return returns list of allParts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}