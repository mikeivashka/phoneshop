package com.es.core.model.order;

import com.es.core.model.phone.PhoneDao;
import com.es.core.model.stock.StockDao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class JdbcOrderDao implements OrderDao {
    private static final String SQL_GET_ORDER_BY_ID = "SELECT * FROM orders WHERE orders.id = ?";
    private static final String SQL_GET_ITEMS_FOR_ORDER = "SELECT * FROM orderItems WHERE orderItems.orderId = ? ";

    @Resource
    private StockDao stockDao;

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void placeOrder(Order order) {
        Long id = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(convertOrderToMap(order))
                .longValue();
        order.setId(id);
        saveOrderItems(order);
        order.getOrderItems().forEach(item -> stockDao.reservePhone(item.getPhone().getId(), item.getQuantity()));
    }

    @Override
    public Optional<Order> findById(Long id) {
        List<Order> result = jdbcTemplate.query(SQL_GET_ORDER_BY_ID, new BeanPropertyRowMapper<>(Order.class), id);
        if (!result.isEmpty()) {
            Order order = result.get(0);
            order.setOrderItems(getItemsForOrder(order));
            return Optional.of(result.get(0));
        } else {
            return Optional.empty();
        }
    }

    private List<OrderItem> getItemsForOrder(Order order) {
        return jdbcTemplate.query(SQL_GET_ITEMS_FOR_ORDER,
                (rs, rowNum) -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setQuantity(rs.getInt("quantity"));
                    orderItem.setPhone(phoneDao.get(rs.getLong("phoneId"))
                            .orElseThrow(IllegalArgumentException::new));
                    orderItem.setOrder(order);
                    orderItem.setId(rs.getLong("id"));
                    return orderItem;
                },
                order.getId());
    }

    private void saveOrderItems(Order order) {
        Map<String, Object>[] batches = createOrderItemBatches(order);
        int[] identifiers = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("orderItems")
                .usingGeneratedKeyColumns("id")
                .executeBatch(batches);
        for (int i = 0; i < identifiers.length; i++) {
            order.getOrderItems().get(i).setId((long) identifiers[i]);
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object>[] createOrderItemBatches(Order order) {
        return order.getOrderItems().stream()
                .map(orderItem -> {
                    Map<String, Object> itemAsMap = new HashMap<>();
                    itemAsMap.put("orderId", orderItem.getOrder().getId());
                    itemAsMap.put("quantity", orderItem.getQuantity());
                    itemAsMap.put("phoneId", orderItem.getPhone().getId());
                    return itemAsMap;
                })
                .toArray(Map[]::new);
    }

    private Map<String, Object> convertOrderToMap(Order order) {
        Map<String, Object> orderAsMap = new HashMap<>();
        orderAsMap.put("subtotal", order.getSubtotal());
        orderAsMap.put("deliveryPrice", order.getDeliveryPrice());
        orderAsMap.put("totalPrice", order.getTotalPrice());
        orderAsMap.put("firstName", order.getFirstName());
        orderAsMap.put("lastName", order.getLastName());
        orderAsMap.put("deliveryAddress", order.getDeliveryAddress());
        orderAsMap.put("contactPhoneNo", order.getContactPhoneNo());
        orderAsMap.put("additionalInfo", order.getAdditionalInfo());
        orderAsMap.put("status", order.getStatus());
        orderAsMap.put("placementDate", order.getPlacementDate());
        return orderAsMap;
    }
}
