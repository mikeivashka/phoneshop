package com.es.core.model.order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    void placeOrder(Order order);

    Optional<Order> findById(Long id);

    List<Order> findAll();

    void updateOrderStatus(Long orderId, OrderStatus newStatus);
}
