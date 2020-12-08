package Adapters;

public class Supplier {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String openingTime;
    private String id;

    public Supplier(){}

    public Supplier(String name, String email, String phone, String address, String openingTime, String id){
        this.name = name;
        this.email = email;
        this.phoneNumber = phone;
        this.address = address;
        this.openingTime = openingTime;
        this.id = id;
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
    public String getPhone() {
        return phoneNumber;
    }
    public void setPhone(String phone) {
        this.phoneNumber = phone;
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
}
