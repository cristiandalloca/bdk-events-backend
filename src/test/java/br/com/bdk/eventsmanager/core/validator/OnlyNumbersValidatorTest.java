package br.com.bdk.eventsmanager.core.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class OnlyNumbersValidatorTest {

    private final OnlyNumbersValidator validator = new OnlyNumbersValidator();

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void shouldReturnTrueWhenValueIsBlank(String value) {
        assertThat(validator.isValid(value, null)).isTrue();
    }

    @Test
    void shouldReturnTrueWhenValueIsNull() {
        assertThat(validator.isValid(null, null)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"12", "00145", "01234123456857561"})
    void shouldReturnTrueWhenValueContainsOnlyNumbers(String value) {
        assertThat(validator.isValid(value, null)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"12a", "00 145", "012341l23456857561"})
    void shouldReturnFalseWhenValueNotContainsOnlyNumbers(String value) {
        assertThat(validator.isValid(value, null)).isFalse();
    }
}