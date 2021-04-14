package com.es.core.model.order;

import java.util.Optional;

public interface OrderDao {
    void placeOrder(Order order);

    Optional<Order> findById(Long id);
}
