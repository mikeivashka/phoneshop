package com.es.phoneshop.web.controller.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartEntryUpdateForm {
    @NotNull
    private Long productId;
    @NotNull
    @Min(value = 1L, message = "Enter a positive number")
    private String quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
