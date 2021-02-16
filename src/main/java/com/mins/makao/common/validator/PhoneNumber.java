package com.mins.makao.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({FIELD, METHOD})
@Constraint(validatedBy =  PhoneNumberValidator.class)
public @interface PhoneNumber {
    String message() default "Invalid phone number (Size 11 ~ 15)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
