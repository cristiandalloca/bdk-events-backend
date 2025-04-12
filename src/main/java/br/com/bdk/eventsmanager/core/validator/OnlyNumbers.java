package br.com.bdk.eventsmanager.core.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = OnlyNumbersValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface OnlyNumbers {

    String message() default "{validation.only-numbers-are-allowed}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
