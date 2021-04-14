package com.es.core.model.order;

import com.es.core.model.phone.CartItem;

@EnoughStock
public class OrderItem extends CartItem {
    private Long id;
    private Order order;

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
