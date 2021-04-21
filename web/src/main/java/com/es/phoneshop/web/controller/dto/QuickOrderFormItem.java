package com.es.phoneshop.web.controller.dto;

import com.es.phoneshop.web.controller.validation.ExistingProductKey;

import javax.validation.constraints.Min;

public class QuickOrderFormItem {
    @ExistingProductKey
    private String productKey;
    @Min(value = 1L, message = "Enter a positive number")
    private String quantity;

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
