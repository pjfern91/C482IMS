package model;

/**
 * Outsourced is a subclass of the Part class. A part can be either InHouse or Outsourced.
 */
public class Outsourced extends Part {

    private String companyName;

    /**
     * the constructor for Outsourced class
     * @param id id to set
     * @param name name to set
     * @param price price to set
     * @param stock stock to set
     * @param min minimum to set
     * @param max maximum to set
     * @param companyName sets companyName if Outsourced button is selected
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @param companyName sets the company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return returns the company name
     */
    public String getCompanyName() {
        return companyName;
    }
}