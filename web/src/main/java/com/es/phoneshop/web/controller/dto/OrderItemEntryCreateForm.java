package com.es.phoneshop.web.controller.dto;

import com.es.core.model.phone.Phone;
import com.es.phoneshop.web.controller.validation.EnoughStock;

@EnoughStock
public class OrderItemEntryCreateForm {
    private Phone phone;
    private Integer quantity;

    public OrderItemEntryCreateForm(Phone phone, Integer quantity) {
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
