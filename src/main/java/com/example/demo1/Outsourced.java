package com.example.demo1;
/**This class extends the part class which will be used for outsourced parts*/
public class Outsourced extends Part{
    /**
     * Outsourced parts have a company name in addition to the attributes of a Part*/
    private String companyName;
    /**
     * @return companyName */
    public String getCompanyName() {
        return companyName;
    }
/**@param companyName to set the name of the company they are outsourcing to*/
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    /**
     * method to return "outsourced" if a product is outsourced*/
    public String getType(){
        return "Outsourced";
    }

    /**This is the constructor for outsourced products. */
    public Outsourced(int id, String name, double price, int inventory, int max, int min, String companyName) {
        super(id, name, price, inventory, max, min);
        this.companyName = companyName;
    }
}
