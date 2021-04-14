package com.es.core.model.cart;

import com.es.core.model.phone.CartItem;

import java.math.BigDecimal;
import java.util.Collection;

public interface CartCalculationService {
    BigDecimal calculateCartTotal(Collection<? extends CartItem> cartItems);

    Long countTotalItems(Collection<? extends CartItem> cartItems);
}
