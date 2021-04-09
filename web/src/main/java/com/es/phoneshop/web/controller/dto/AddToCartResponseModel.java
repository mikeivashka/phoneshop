package com.es.phoneshop.web.controller.dto;

public class AddToCartResponseModel {
    private boolean success;
    private Double cartSubtotal;
    private Long cartQuantity;
    private String message;

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

    public Long getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(Long cartQuantity) {
        this.cartQuantity = cartQuantity;
    }
}
