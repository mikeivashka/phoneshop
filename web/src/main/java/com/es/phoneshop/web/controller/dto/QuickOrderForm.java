package com.es.phoneshop.web.controller.dto;

import javax.validation.Valid;
import java.util.List;

public class QuickOrderForm {
    @Valid
    private List<@Valid QuickOrderFormItem> orderItems;

    public List<QuickOrderFormItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<QuickOrderFormItem> orderItems) {
        this.orderItems = orderItems;
    }
}
