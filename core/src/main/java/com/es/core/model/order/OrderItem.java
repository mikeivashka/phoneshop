package com.es.core.model.order;

import com.es.core.model.cart.CartItem;
import com.es.core.model.phone.Phone;

public class OrderItem extends CartItem {
    private Long id;
    private Order order;

    public OrderItem(Phone phone, Integer quantity, Order order) {
        super(phone, quantity);
        this.order = order;
    }

    public OrderItem(CartItem cartItem, Order order) {
        super(cartItem.getPhone(), cartItem.getQuantity());
        this.order = order;
    }

    public OrderItem() {
        super();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
