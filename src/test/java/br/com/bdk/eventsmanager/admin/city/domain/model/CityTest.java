package br.com.bdk.eventsmanager.admin.city.domain.model;

import br.com.bdk.eventsmanager.admin.state.domain.model.State;
import br.com.bdk.eventsmanager.core.ValidatorTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class CityTest extends ValidatorTest {

    private City city;

    @BeforeEach
    void setUp() {
        city = new City();
        city.setName("Florianópolis");
        city.setState(new State());
    }

    @Test
    void shouldValidateCity() {
        var constraintViolations = validator.validate(city);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    void shouldValidateCityNameIsNull() {
        validateFieldIsNull(city, "name");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void shouldValidateCityNameIsBlank(String name) {
        validateField(city, "name", name);
    }

    @Test
    void shouldValidateCityNameMaxSize() {
        validateAlphabeticFieldMaxSize(city, "name", 255);
    }

    @ParameterizedTest
    @ValueSource(strings = {"ab1ab", "abbbba  2  s", "1"})
    void shouldValidateCityNameOnlyLetters(String name) {
        validateField(city, "name", name);
    }

    @Test
    void shouldValidateCityStateIsNull() {
        validateFieldIsNull(city, "state");
    }

}