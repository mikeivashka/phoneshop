package com.es.phoneshop.web.controller.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnoughStockConstraintValidator.class)
public @interface EnoughStock {
    String message() default "Not enough stock for item";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
