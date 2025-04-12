package br.com.bdk.eventsmanager.core.validator;

import jakarta.validation.Payload;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExactSizeValidatorTest {
    
    @Nested
    class WhenInitializeValidator {

        @Test
        void shouldInitializeSize() {
            final int size = 56;
            final ExactSize exactSize = getExactSizeAnnotation(size);
            final var exactSizeValidator = new ExactSizeValidator();
            exactSizeValidator.initialize(exactSize);
            assertEquals(size, ReflectionTestUtils.getField(exactSizeValidator, "size"));
        }
    }

    private ExactSize getExactSizeAnnotation(int size) {
        return new ExactSize(){

            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }

            @Override
            public String message() {
                return "";
            }

            @Override
            public Class<?>[] groups() {
                return null;
            }

            @Override
            public Class<? extends Payload>[] payload() {
                return null;
            }

            @Override
            public int size() {
                return size;
            }
        };
    }

    @Nested
    class WhenValidateValue {

        @ParameterizedTest
        @ValueSource(strings = {"   ", "tsd", "1 2", " 5 ", "", "  ", "   "})
        void shouldReturnTrueWhenValueIsValid(final String value) {
            int size = 3;
            final var exactSizeValidator = new ExactSizeValidator();
            exactSizeValidator.initialize(getExactSizeAnnotation(size));
            assertTrue(exactSizeValidator.isValid(value, null));
        }

        @Test
        void shouldReturnTrueWhenValueIsNull() {
            int size = 3;
            final var exactSizeValidator = new ExactSizeValidator();
            exactSizeValidator.initialize(getExactSizeAnnotation(size));
            assertTrue(exactSizeValidator.isValid(null, null));
        }

        @ParameterizedTest
        @ValueSource(strings = {"2 22", "55555", "4424"})
        void shouldReturnFalseWhenValueIsInvalid(final String value) {
            int size = 3;
            final var exactSizeValidator = new ExactSizeValidator();
            exactSizeValidator.initialize(getExactSizeAnnotation(size));
            assertFalse(exactSizeValidator.isValid(value, null));
        }
    }
}