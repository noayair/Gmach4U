package Adapters;

public class ReserveProduct {
    private String reserveId, productId, supplierId, clientId;
    public ReserveProduct() {}
    public ReserveProduct(String prodId, String suppId, String clientId){
        this.productId = prodId;
        this.supplierId = suppId;
        this.clientId = clientId;
        this.reserveId = prodId.substring(0,3)+suppId.substring(0,3)+clientId.substring(0,3);
    }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getSupplierId() { return supplierId; }
    public void setSupplierId(String supplierId) { this.supplierId = supplierId; }
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getId() { return reserveId; }
}
