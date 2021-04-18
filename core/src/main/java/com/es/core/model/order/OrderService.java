package com.es.core.model.order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    Order getOrderById(Long id);

    void placeOrder(Order order);

    void updateStatus(Order order, OrderStatus newStatus);

    List<Order> findAll();

    BigDecimal getDeliveryPrice();
}
