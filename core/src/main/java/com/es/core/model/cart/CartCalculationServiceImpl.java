package com.es.core.model.cart;

import com.es.core.model.phone.CartItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;

@Service
public class CartCalculationServiceImpl implements CartCalculationService {
    @Override
    public BigDecimal calculateCartTotal(Collection<? extends CartItem> cartItems) {
        return cartItems.stream()
                .map(cartItem -> cartItem.getPhone().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Long countTotalItems(Collection<? extends CartItem> cartItems) {
        return cartItems.stream()
                .mapToLong(CartItem::getQuantity)
                .sum();
    }
}
