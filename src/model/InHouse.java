package model;

/**
 * InHouse is a subclass of the Part class. A Part can be either InHouse or Outsourced.
 */
public class InHouse extends Part {

    private int machineId;

    /**
     * constructor for InHouse class
     * @param id id to set
     * @param name name to set
     * @param price price to set
     * @param stock stock to set
     * @param min minimum to set
     * @param max maximum to set
     * @param machineId machineId to set of InHouse button is selected
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @param machineId sets the machineID
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * @return returns the machineID
     */
    public int getMachineId() {
        return machineId;
    }
}