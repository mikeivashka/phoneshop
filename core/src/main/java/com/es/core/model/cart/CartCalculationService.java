package com.es.core.model.cart;

import java.math.BigDecimal;
import java.util.Collection;

public interface CartCalculationService {
    BigDecimal calculateCartTotal(Collection<? extends CartItem> cartItems);

    Integer countTotalItems(Collection<? extends CartItem> cartItems);
}
