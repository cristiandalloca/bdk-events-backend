package br.com.bdk.eventsmanager.admin.country.api.v1.model.input;

import br.com.bdk.eventsmanager.core.ValidatorTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class CountryInputTest extends ValidatorTest {

    private CountryInput countryInput;


    @BeforeEach
    void setUp() {
        countryInput = new CountryInput();
        ReflectionTestUtils.setField(countryInput, "name", "Brasil");
        ReflectionTestUtils.setField(countryInput, "acronym", "BRA");
    }

    @Test
    void shouldValidate() {
        var violations = validator.validate(countryInput);
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldValidateNameIsNull() {
        validateFieldIsNull(countryInput, "name");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void shouldValidateNameIsBlank(String name) {
        validateField(countryInput, "name", name);
    }

    @Test
    void shouldValidateNameMaxSize() {
        validateAlphabeticFieldMaxSize(countryInput, "name", 255);
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaa123", "  a254", "00001111 a"})
    void shouldValidateNameOnlyLetters(String name) {
        validateField(countryInput, "name", name);
    }

    @Test
    void shouldValidateAcronymIsNull() {
        validateFieldIsNull(countryInput, "acronym");
    }

    @Test
    void shouldValidateAcronymIsBlank() {
        validateField(countryInput, "acronym", "  ");
    }

    @Test
    void shouldValidateAcronymMaxSize() {
        validateAlphabeticFieldMaxSize(countryInput, "acronym", 3);
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "ab1", "1 a"})
    void shouldValidateAcronymOnlyLetters(String acronym) {
        validateField(countryInput, "acronym", acronym);
    }
}