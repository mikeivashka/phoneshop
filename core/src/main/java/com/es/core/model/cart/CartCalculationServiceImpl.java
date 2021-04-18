package com.es.core.model.cart;

import java.math.BigDecimal;
import java.util.Collection;

public class CartCalculationServiceImpl implements CartCalculationService {
    @Override
    public BigDecimal calculateCartTotal(Collection<? extends CartItem> cartItems) {
        return cartItems.stream()
                .map(cartItem -> cartItem.getPhone().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Integer countTotalItems(Collection<? extends CartItem> cartItems) {
        return cartItems.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}
