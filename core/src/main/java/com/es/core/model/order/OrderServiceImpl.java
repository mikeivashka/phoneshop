package com.es.core.model.order;

import com.es.core.model.stock.StockDao;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;

    private final StockDao stockDao;

    private BigDecimal deliveryPrice;

    public OrderServiceImpl(OrderDao orderDao, StockDao stockDao) {
        this.orderDao = orderDao;
        this.stockDao = stockDao;
    }

    @Value("${delivery.price}")
    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice.setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderDao.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    @Override
    public void placeOrder(Order order) {
        order.setPlacementDate(new Date());
        order.setStatus(OrderStatus.NEW);
        orderDao.placeOrder(order);
    }

    @Override
    public void updateStatus(Order order, OrderStatus newStatus) {
        orderDao.updateOrderStatus(order.getId(), newStatus);
        if (newStatus == OrderStatus.DELIVERED) {
            stockDao.applyReserved(order.getOrderItems());
        } else if (newStatus == OrderStatus.REJECTED) {
            stockDao.cancelReserved(order.getOrderItems());
        }
    }
}
