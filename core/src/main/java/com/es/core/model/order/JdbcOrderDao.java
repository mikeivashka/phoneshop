package com.es.core.model.order;

import com.es.core.model.phone.PhoneDao;
import com.es.core.model.stock.StockDao;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class JdbcOrderDao implements OrderDao {
    private static final String SQL_GET_ORDER_BY_ID = "SELECT * FROM orders WHERE orders.id = ?";
    private static final String SQL_GET_ITEMS_FOR_ORDER = "SELECT * FROM orderItems WHERE orderItems.orderId = ? ";
    private static final String SQL_FIND_ALL_ORDERS = "SELECT * FROM orders ORDER BY placementDate DESC";
    private static final String SQL_UPDATE_ORDER_STATUS = "UPDATE orders SET status = ? WHERE id = ?";

    private final StockDao stockDao;

    private final PhoneDao phoneDao;

    private final JdbcTemplate jdbcTemplate;

    private final List<String> orderFieldNames;

    public JdbcOrderDao(StockDao stockDao, PhoneDao phoneDao, JdbcTemplate jdbcTemplate, List<String> orderFieldNames) {
        this.stockDao = stockDao;
        this.phoneDao = phoneDao;
        this.jdbcTemplate = jdbcTemplate;
        this.orderFieldNames = orderFieldNames;
    }

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

    @Override
    public List<Order> findAll() {
        List<Order> orders = jdbcTemplate.query(SQL_FIND_ALL_ORDERS, new BeanPropertyRowMapper<>(Order.class));
        orders.forEach(order -> order.setOrderItems(getItemsForOrder(order)));
        return orders;
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus newStatus) {
        jdbcTemplate.update(SQL_UPDATE_ORDER_STATUS, newStatus.name(), orderId);
    }

    private List<OrderItem> getItemsForOrder(Order order) {
        return jdbcTemplate.query(SQL_GET_ITEMS_FOR_ORDER, getOrderItemRowMapper(order), order.getId());
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

    private RowMapper<OrderItem> getOrderItemRowMapper(Order order) {
        return (rs, rowNum) -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(rs.getInt("quantity"));
            orderItem.setPhone(phoneDao.get(rs.getLong("phoneId"))
                    .orElseThrow(IllegalArgumentException::new));
            orderItem.setOrder(order);
            orderItem.setId(rs.getLong("id"));
            return orderItem;
        };
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
        BeanWrapperImpl wrapper = new BeanWrapperImpl(order);
        return orderFieldNames.stream()
                .filter(name -> wrapper.getPropertyValue(name) != null)
                .collect(Collectors.toMap(s -> s, wrapper::getPropertyValue));
    }
}
