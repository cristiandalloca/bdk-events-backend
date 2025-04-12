package br.com.bdk.eventsmanager.core.validator;

import br.com.bdk.eventsmanager.core.util.RegexConstants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    private final Pattern pattern = Pattern.compile(RegexConstants.PHONE_NUMBER_REGEX);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isBlank(value) || value.matches(pattern.toString());
    }

}
