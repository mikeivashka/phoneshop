package com.es.phoneshop.web.controller.dto;

public class AddToCartResponse {
    private boolean success;
    private Double cartSubtotal;
    private Integer cartQuantity;
    private String message;

    public Integer getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(Integer cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getSuccessStatus() {
        return success;
    }

    public Double getCartSubtotal() {
        return cartSubtotal;
    }

    public void setCartSubtotal(Double cartSubtotal) {
        this.cartSubtotal = cartSubtotal;
    }

}
