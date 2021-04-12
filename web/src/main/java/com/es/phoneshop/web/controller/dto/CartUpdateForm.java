package com.es.phoneshop.web.controller.dto;

import java.util.List;

public class CartUpdateForm {
    private List<Long> productIds;

    private List<String> quantities;

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<String> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<String> quantities) {
        this.quantities = quantities;
    }
}
