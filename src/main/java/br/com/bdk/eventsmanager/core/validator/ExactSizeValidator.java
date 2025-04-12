package br.com.bdk.eventsmanager.core.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class ExactSizeValidator implements ConstraintValidator<ExactSize, String> {

    private int size;

    @Override
    public void initialize(ExactSize constraintAnnotation) {
        size = constraintAnnotation.size();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isBlank(value) || value.length() == this.size;
    }

}
