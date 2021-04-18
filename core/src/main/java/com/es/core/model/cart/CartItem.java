package com.es.core.model.cart;

import com.es.core.model.order.EnoughStock;
import com.es.core.model.phone.Phone;

@EnoughStock
public class CartItem {
    private Phone phone;
    private Integer quantity;

    public CartItem() {
    }

    public CartItem(Phone phone, Integer quantity) {
        this.phone = phone;
        this.quantity = quantity;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
