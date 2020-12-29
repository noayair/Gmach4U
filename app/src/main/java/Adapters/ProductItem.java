package Adapters;

import android.net.Uri;

public class ProductItem {
    private String name, description, unitsInStock, burrowTime;

    public ProductItem(){}
    public ProductItem(String name, String description, String unitsInStock, String burrowTime){
        this.name = name;
        this.description = description;
        this.unitsInStock = unitsInStock;
        this.burrowTime = burrowTime;
    }
    //set & get
    public String getName () { return this.name;}
    public void setName (String name) { this.name = name;}
    public String getDescription() {return this.description;}
    public void setDescription(String description) {this.description = description;}
    public String getUnitsInStock() {return this.unitsInStock;}
    public void setUnitsInStock(String unitsInStock) {this.unitsInStock = unitsInStock;}
    public String getBurrowTime() {return this.burrowTime;}
    public void setBurrowTime(String burrowTime) {this.burrowTime = burrowTime;}
}
