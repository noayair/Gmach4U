package Adapters;

public class ReserveProduct {
    private String productId, supplierId, clientId;
    private static int counter;
    private int id;
    public ReserveProduct() {}
    public ReserveProduct(String prodId, String suppId, String clientId){
        this.productId = prodId;
        this.supplierId = suppId;
        this.clientId = clientId;
        this.id = counter++;
    }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getSupplierId() { return supplierId; }
    public void setSupplierId(String supplierId) { this.supplierId = supplierId; }
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public int getId() { return id; }
}
