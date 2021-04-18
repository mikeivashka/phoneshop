package com.es.core.model.order;

import com.es.core.model.cart.CartItem;
import com.es.core.model.stock.Stock;
import com.es.core.model.stock.StockDao;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnoughStockConstraintValidator implements ConstraintValidator<EnoughStock, CartItem> {

    private final StockDao stockDao;

    public EnoughStockConstraintValidator(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    @Override
    public boolean isValid(CartItem cartItem, ConstraintValidatorContext constraintValidatorContext) {
        int requiredQuantity = cartItem.getQuantity();
        Stock available = stockDao.getStockForPhone(cartItem.getPhone());
        return requiredQuantity <= available.getStock() - available.getReserved();
    }
}
