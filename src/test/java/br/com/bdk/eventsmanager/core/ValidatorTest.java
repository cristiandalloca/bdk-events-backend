package br.com.bdk.eventsmanager.core;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("java:S2187")
public class ValidatorTest {

    protected static Validator validator;

    @BeforeAll
    static void beforeAll() {
        try (var validationFactory = Validation.buildDefaultValidatorFactory()) {
            validator = validationFactory.getValidator();
        }
    }

    protected void validateFieldIsNull(Object object, String fieldName) {
        ReflectionTestUtils.setField(object, fieldName, null);
        onlyOneViolation(object);
    }

    protected void validateField(Object object, String fieldName, Object... notAllowedValues) {
        for (Object notAllowedValue : notAllowedValues) {
            ReflectionTestUtils.setField(object, fieldName, notAllowedValue);
            onlyOneViolation(object);
        }
    }

    protected void validateAlphaNumericField(Object object, String fieldName, int maxSize) {
        ReflectionTestUtils.setField(object, fieldName, RandomStringUtils.secure().nextAlphanumeric(maxSize + 1));
        onlyOneViolation(object);
    }

    protected void validateAlphabeticFieldMaxSize(Object object, String fieldName, int maxSize) {
        ReflectionTestUtils.setField(object, fieldName, RandomStringUtils.secure().nextAlphabetic(maxSize + 1));
        onlyOneViolation(object);
    }

    protected void validateNumericFieldMaxSize(Object object, String fieldName, int maxSize) {
        ReflectionTestUtils.setField(object, fieldName, RandomStringUtils.secure().nextNumeric(maxSize + 1));
        onlyOneViolation(object);
    }

    private void onlyOneViolation(Object object) {
        var constraintViolations = validator.validate(object);
        assertThat(constraintViolations.size()).isOne();
    }
}
