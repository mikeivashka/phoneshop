package com.es.core.model.order;

import com.es.core.model.phone.Phone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/context/test-config.xml")
public class JdbcOrderDaoIntegrationTest {
    private static final String SQL_COUNT_ORDERS = "SELECT COUNT(*) FROM orders";
    private static final String SQL_COUNT_ITEMS_FOR_ORDER = "SELECT COUNT(*) FROM orderItems WHERE orderId = ?";
    private static final BigDecimal ORDER_SUBTOTAL = BigDecimal.valueOf(500);
    private static final BigDecimal ORDER_DELIVERY_PRICE = BigDecimal.valueOf(5);
    private static final Long EXISTING_PHONE_KEY_1 = 1001L;
    private static final Long EXISTING_PHONE_KEY_2 = 1002L;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private JdbcOrderDao orderDao;

    private Order order;

    private Phone phone1;
    private Phone phone2;

    @Before
    public void setUp() {
        order = new Order();
        phone1 = new Phone();
        phone2 = new Phone();
        phone1.setId(EXISTING_PHONE_KEY_1);
        phone2.setId(EXISTING_PHONE_KEY_2);
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setPhone(phone1);
        orderItem1.setQuantity(1);
        orderItem1.setOrder(order);
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setPhone(phone2);
        orderItem2.setQuantity(1);
        orderItem2.setOrder(order);
        order.setOrderItems(Arrays.asList(orderItem1, orderItem2));
        order.setSubtotal(ORDER_SUBTOTAL);
        order.setDeliveryPrice(ORDER_DELIVERY_PRICE);
        order.setTotalPrice(ORDER_SUBTOTAL.add(ORDER_DELIVERY_PRICE));
        order.setFirstName("First Name");
        order.setLastName("Last Name");
        order.setContactPhoneNo("12345");
        order.setDeliveryAddress("DeliveryAddress");
        order.setStatus(OrderStatus.NEW);
        order.setPlacementDate(new Date());
    }

    @Test
    @DirtiesContext
    public void testPlaceOrderAddsOrderLine() {
        Integer ordersBefore = countOrders();

        orderDao.placeOrder(order);

        assertEquals(ordersBefore + 1, countOrders().intValue());
    }

    @Test
    @DirtiesContext
    public void testPlaceOrderSetsIdToOrder() {
        order.setId(null);

        orderDao.placeOrder(order);

        assertNotNull(order.getId());
    }

    @Test
    @DirtiesContext
    public void testPlaceOrderAddsItemsLines() {
        orderDao.placeOrder(order);

        assertEquals(countItemsForOrder(order.getId()).intValue(), order.getOrderItems().size());
    }

    @Test
    @DirtiesContext
    public void testPlaceOrderSetsIdToOrderItems() {
        orderDao.placeOrder(order);

        assertTrue(order.getOrderItems().stream().allMatch(item -> item.getId() != null));
    }

    private Integer countOrders() {
        return jdbcTemplate.queryForObject(SQL_COUNT_ORDERS, Integer.class);
    }

    private Integer countItemsForOrder(Long orderId) {
        return jdbcTemplate.queryForObject(SQL_COUNT_ITEMS_FOR_ORDER, new Long[]{orderId}, Integer.class);
    }
}