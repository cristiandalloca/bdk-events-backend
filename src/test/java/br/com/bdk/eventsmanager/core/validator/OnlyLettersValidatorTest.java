package br.com.bdk.eventsmanager.core.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class OnlyLettersValidatorTest {
    
    @ParameterizedTest
    @ValueSource(strings = {"", "a", "ab", "abcdefghijklmnopqrstuvxwz", "áãàâçóõôòéêèėēíîìúùûū", "São Paulo", "llllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll"})
    void shouldIsValid(String value) {
        var onlyLetterValidator = new OnlyLetterValidator();
        assertThat(onlyLetterValidator.isValid(value, null)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"abcdefghijklmnopqrstuvxwz0", "0123456789", "~!@#$%^&*()_+", ",./;'[]"})
    void shouldIsInvalid(String value) {
        var onlyLetterValidator = new OnlyLetterValidator();
        assertThat(onlyLetterValidator.isValid(value, null)).isFalse();
    }

    @Test
    void shouldIsValidWhenIsNull() {
        var onlyLetterValidator = new OnlyLetterValidator();
        assertThat(onlyLetterValidator.isValid(null, null)).isTrue();

    }

}
