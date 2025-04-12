package br.com.bdk.eventsmanager.core.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class PhoneNumberValidatorTest {

    private final PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();

    @ParameterizedTest
    @ValueSource(strings = {" ", "  ", ""})
    void shouldReturnTrueWhenValueIsBlank(String value) {
        assertThat(phoneNumberValidator.isValid(value, null)).isTrue();
    }

    @Test
    void shouldReturnTrueWhenValueIsNull() {
        assertThat(phoneNumberValidator.isValid(null, null)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"+5518981832227", "(63) 3426-2943", "(37) 99935-4375", "6334262943", "37999354375"})
    void shouldReturnTrueWhenAValidPhoneNumber(String value) {
        assertThat(phoneNumberValidator.isValid(value, null)).isTrue();
    }
}