package br.com.bdk.eventsmanager.core.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class OnlyNumbersValidator implements ConstraintValidator<OnlyNumbers, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isBlank(value) || isNumeric(value);
    }

}
