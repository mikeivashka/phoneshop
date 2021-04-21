package com.es.phoneshop.web.controller.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ProductKeyExistenceConstraintValidator.class)
public @interface ExistingProductKey {
    String message() default "Item does not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
