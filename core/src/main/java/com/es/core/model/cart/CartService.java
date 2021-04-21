package com.es.core.model.cart;

import com.es.core.model.phone.Phone;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public interface CartService {

    Map<Phone, Integer> getCart();

    Set<CartItem> getCartItems();

    void addPhone(Long phoneId, Integer quantity);

    /**
     * @param items key: {@link com.es.core.model.phone.Phone#id}
     *              value: quantity
     */
    void update(Map<Long, Integer> items);

    void remove(Long phoneId);

    BigDecimal getTotalPrice();

    Integer getTotalItemsCount();

    void clearCart();
}
