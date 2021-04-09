package com.es.phoneshop.web.controller.dto;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AddToCartRequestModel {
    @NotNull
    private Long productId;
    @NotNull
    @Min(value = 1L)
    private Long quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
