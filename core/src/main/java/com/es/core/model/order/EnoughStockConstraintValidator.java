package com.es.core.model.order;

import com.es.core.model.stock.Stock;
import com.es.core.model.stock.StockDao;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnoughStockConstraintValidator implements ConstraintValidator<EnoughStock, OrderItem> {

    @Resource
    private StockDao stockDao;

    @Override
    public boolean isValid(OrderItem orderItem, ConstraintValidatorContext constraintValidatorContext) {
        int requiredQuantity = orderItem.getQuantity();
        Stock available = stockDao.getStockForPhone(orderItem.getPhone());
        return requiredQuantity <= available.getStock() - available.getReserved();
    }
}
