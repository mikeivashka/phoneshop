package com.es.phoneshop.web.controller.validation;

import com.es.core.model.phone.PhoneDao;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProductKeyExistenceConstraintValidator implements ConstraintValidator<ExistingProductKey, String> {
    private final PhoneDao phoneDao;

    public ProductKeyExistenceConstraintValidator(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return phoneDao.get(s).isPresent();
    }
}
