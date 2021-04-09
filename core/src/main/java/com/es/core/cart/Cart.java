package com.es.core.cart;

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.Map;

public class Cart {
    private Map<Long, Long> cartItems;
    private Long totalItemsCount;
    private BigDecimal totalPrice;

    public Cart() {
        this.cartItems = new Hashtable<>();
        totalItemsCount = 0L;
        totalPrice = BigDecimal.ZERO;
    }

    public Long getTotalItemsCount() {
        return totalItemsCount;
    }

    public void setTotalItemsCount(Long totalItemsCount) {
        this.totalItemsCount = totalItemsCount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Map<Long, Long> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Map<Long, Long> cartItems) {
        this.cartItems = cartItems;
    }

    @Override
    public String toString() {
        return "Cart{" + "cartItems=" + cartItems +
                ", totalItems=" + totalItemsCount +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
