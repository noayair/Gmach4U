package Adapters;

public class ProductItem {
    private String name, description, unitsInStock;
    private int id;
    private static int counter;

    public ProductItem(){}
    public ProductItem(String name, String description, String unitsInStock){
        this.name = name;
        this.description = description;
        this.unitsInStock = unitsInStock;
        this.id = counter++;
    }
    //set & get
    public String getName () { return this.name;}
    public void setName (String name) { this.name = name;}
    public String getDescription() {return this.description;}
    public void setDescription() {this.description = description;}
    public String getUnitsInStock() {return this.unitsInStock;}
    public void setUnitsInStock() {this.unitsInStock = unitsInStock;}
    public int getId() {return this.id;}
    public void setId() {this.id = id;}
}
