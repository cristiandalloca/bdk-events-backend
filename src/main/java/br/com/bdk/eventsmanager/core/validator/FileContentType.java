package br.com.bdk.eventsmanager.core.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FileContentTypeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FileContentType {

    String message() default "Invalid file, only type(s) '{allowed}' are allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String[] allowed();
}
