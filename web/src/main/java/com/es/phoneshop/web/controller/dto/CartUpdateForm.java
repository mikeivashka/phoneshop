package com.es.phoneshop.web.controller.dto;

import javax.validation.Valid;
import java.util.List;

public class CartUpdateForm {
    @Valid
    private List<@Valid CartEntryUpdateForm> cartEntries;

    public List<CartEntryUpdateForm> getCartEntries() {
        return cartEntries;
    }

    public void setCartEntries(List<CartEntryUpdateForm> cartEntries) {
        this.cartEntries = cartEntries;
    }
}
