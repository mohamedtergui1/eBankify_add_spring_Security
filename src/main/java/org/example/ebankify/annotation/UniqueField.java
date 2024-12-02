package org.example.ebankify.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


import org.example.ebankify.validators.UniqueFieldValidator;


@Constraint(validatedBy = UniqueFieldValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueField {
    String message() default "This value must be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> entity();

    String field();
}