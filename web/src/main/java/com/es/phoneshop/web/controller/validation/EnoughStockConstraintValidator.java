package com.es.phoneshop.web.controller.validation;

import com.es.core.model.stock.Stock;
import com.es.core.model.stock.StockDao;
import com.es.phoneshop.web.controller.dto.OrderItemEntryCreateForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnoughStockConstraintValidator implements ConstraintValidator<EnoughStock, OrderItemEntryCreateForm> {

    private final StockDao stockDao;

    public EnoughStockConstraintValidator(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    @Override
    public boolean isValid(OrderItemEntryCreateForm orderItemEntryCreateForm, ConstraintValidatorContext constraintValidatorContext) {
        int requiredQuantity = orderItemEntryCreateForm.getQuantity();
        Stock available = stockDao.getStockForPhone(orderItemEntryCreateForm.getPhone());
        return requiredQuantity <= available.getStock() - available.getReserved();
    }
}
