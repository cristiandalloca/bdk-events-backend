package br.com.bdk.eventsmanager.core.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExactSizeValidator.class)
@Target({ ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExactSize {

    String message() default "Tamanho inválido. O tamanho deve ser {size} caracteres";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int size();

}
