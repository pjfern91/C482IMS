package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product class
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * constructor for Product class
     * @param id id to set
     * @param name name to set
     * @param price price to set
     * @param stock stock to set
     * @param min minimum to set
     * @param max maximum to set
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @param id sets product id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return returns product id
     */
    public int getId() {
        return id;
    }

    /**
     * @param name sets product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return returns product name
     */
    public String getName() {
        return name;
    }

    /**
     * @param price sets product price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return returns product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param stock sets product stock amount
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return returns product stock amount
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param min sets min products allowed in inventory
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return returns min stock count
     */
    public int getMin() {
        return min;
    }

    /**
     * @param max sets max products allowed in inventory
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return returns max stock count
     */
    public int getMax() {
        return max;
    }

    /**
     * adds an associated part to a product
     * @param part the part to add
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * removes an associated part from a product
     * @param selectedAssociatedPart the associated part to remove from associatedParts list
     */
    public void deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * @return returns all associated parts to a product
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}
