package com.es.core.model.order;

import com.es.core.model.phone.Phone;

import java.util.Map;

public interface OrderService {

    Order createOrder(Map<Phone, Integer> cart);

    Order getOrderById(Long id);

    void recalculatePrices(Order order);

    void placeOrder(Order order);
}