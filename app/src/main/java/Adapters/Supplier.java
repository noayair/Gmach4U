package Adapters;

public class Supplier {
    private String name, email, phone, address, openingTime, category, location, id;

    public Supplier(){}

    public Supplier(String name, String email, String phone, String address, String openingTime, String category, String location,String id){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.openingTime = openingTime;
        this.id = id;
        this.category = category;
        this.location = location;
    }
    public Supplier(String name,String email, String phone, String address,String id){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.id = id;
        this.category=getCategory();
        this.openingTime = getOpeningTime();
        this.location = getLocation();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() { return phone;}
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getOpeningTime() {
        return openingTime;
    }
    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }
    public String getId() {
        return id;
    }
    public String getLocation() {return location;}
    public void setLocation(String location){this.location = location;}
    public String getCategory() {return category;}
    public void setCategory(String category){this.category = category;}

}
