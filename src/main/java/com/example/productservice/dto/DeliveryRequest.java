package com.example.productservice.dto;

public class DeliveryRequest {

    private Long productId;
    private String address;

    public DeliveryRequest() {
    }

    public DeliveryRequest(Long productId, String address) {
        this.productId = productId;
        this.address = address;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
