package Adapters;

public class ProductItem {
    private String name, description, unitsInStock, burrowTime;
    private int id;
    private static int counter;

    public ProductItem(){}
    public ProductItem(String name, String description, String unitsInStock, String burrowTime){
        this.name = name;
        this.description = description;
        this.unitsInStock = unitsInStock;
        this.id = counter++;
        this.burrowTime = burrowTime;
    }
    //set & get
    public String getName () { return this.name;}
    public void setName (String name) { this.name = name;}
    public String getDescription() {return this.description;}
    public void setDescription(String description) {this.description = description;}
    public String getUnitsInStock() {return this.unitsInStock;}
    public void setUnitsInStock(String unitsInStock) {this.unitsInStock = unitsInStock;}
    public int getId() {return this.id;}
    public String getBurrowTime() {return this.burrowTime;}
    public void setBurrowTime(String burrowTime) {this.burrowTime = burrowTime;}
}
