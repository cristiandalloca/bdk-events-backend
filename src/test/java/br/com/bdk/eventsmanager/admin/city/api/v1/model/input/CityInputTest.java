package br.com.bdk.eventsmanager.admin.city.api.v1.model.input;

import br.com.bdk.eventsmanager.core.ValidatorTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class CityInputTest extends ValidatorTest {

    private CityInput cityInput;

    @BeforeEach
    void setUp() {
        cityInput = new CityInput();
        ReflectionTestUtils.setField(cityInput, "name", "Florianópolis");
        ReflectionTestUtils.setField(cityInput, "stateId", "dfd197a2-d345-4834-90dc-57867c0e2fa0");
    }

    @Test
    void shouldValidateCityInput() {
        var constraintViolations = validator.validate(cityInput);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    void shouldValidateNameIsNull() {
        validateFieldIsNull(cityInput, "name");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void shouldValidateNameIsBlank(String name) {
        validateField(cityInput, "name", name);
    }

    @Test
    void shouldValidateNameMaxSize() {
        validateAlphabeticFieldMaxSize(cityInput, "name", 255);
    }

    @ParameterizedTest
    @ValueSource(strings = {"ab1ab", "abbbba  2  s", "1"})
    void shouldValidateNameContainsOnlyLetters(String name) {
        validateField(cityInput, "name", name);
    }

    @Test
    void shouldValidateStateIdIsNull() {
        validateFieldIsNull(cityInput, "stateId");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void shouldValidateStateIdIsBlank(String stateId) {
        validateField(cityInput, "stateId", stateId);
    }

    @Test
    void shouldValidateStateIdMaxSize() {
        validateAlphaNumericField(cityInput, "stateId", 36);
    }

}