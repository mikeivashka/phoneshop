package com.es.core.model.order;

import com.es.core.model.cart.CartCalculationService;
import com.es.core.model.phone.Phone;
import com.es.core.model.stock.StockDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private CartCalculationService cartCalculationService;

    @Resource
    private OrderDao orderDao;

    @Resource
    private StockDao stockDao;

    private BigDecimal deliveryPrice;

    @Value("${delivery.price}")
    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice.setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public Order createOrder(Map<Phone, Integer> cart) {
        Order order = new Order();
        order.setOrderItems(createOrderItemsList(cart, order));
        recalculatePrices(order);
        return order;
    }

    @Override
    public Order getOrderById(Long id) {
        return orderDao.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void recalculatePrices(Order order) {
        order.setDeliveryPrice(deliveryPrice);
        order.setSubtotal(cartCalculationService.calculateCartTotal(order.getOrderItems()));
        order.setTotalPrice(deliveryPrice.add(order.getSubtotal()));
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
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

    private List<OrderItem> createOrderItemsList(Map<Phone, Integer> cart, Order order) {
        return cart.entrySet().stream()
                .map(e -> {
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setPhone(e.getKey());
                    item.setQuantity(e.getValue());
                    return item;
                })
                .collect(Collectors.toList());
    }

}
