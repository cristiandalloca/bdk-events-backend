package br.com.bdk.eventsmanager.core.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class OnlyLetterValidator implements ConstraintValidator<OnlyLetters, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return StringUtils.isAlphaSpace(value);
    }
    
}
