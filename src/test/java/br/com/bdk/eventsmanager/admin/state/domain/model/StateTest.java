package br.com.bdk.eventsmanager.admin.state.domain.model;

import br.com.bdk.eventsmanager.admin.country.domain.model.Country;
import br.com.bdk.eventsmanager.core.ValidatorTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class StateTest extends ValidatorTest {

    private State state;

    @BeforeEach
    void setUp() {
        state = new State();
        state.setName("Santa Catarina");
        state.setAcronym("SC");
        state.setCountry(new Country("BR"));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome ultrapassar 255 caracteres")
    void shouldExceptionWhenNameSizeIsMoreThan255Characters() {
        validateAlphaNumericField(state, "name", 255);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome for nulo")
    void shouldExceptionWhenNameIsNull() {
        validateFieldIsNull(state, "name");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    @DisplayName("Deve lançar exceção quando o nome for vazio")
    void shouldExceptionWhenNameIsBlank(String name) {
        validateField(state, "name", name);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o acrônimo for nulo")
    void shouldExceptionWhenAcronymIsNull() {
        validateFieldIsNull(state, "acronym");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    @DisplayName("Deve lançar exceção quando o acrônimo for vazio")
    void shouldExceptionWhenAcronymIsBlank(String acronym) {
        validateField(state, "acronym", acronym);
    }

    @Test
    @DisplayName("Deve lançar exceção de apenas letras para a abreviação")
    void shouldExceptionWhenAcronymContainsNonLettersCharacter() {
        validateField(state, "acronym", "Ab1");
    }

    @Test
    @DisplayName("Deve lançar exceção quando o país for nulo")
    void shouldExceptionWhenCountryIsNull() {
        validateFieldIsNull(state, "country");
    }

    @ParameterizedTest
    @DisplayName("Deve retornar o acrônimo em uppercase")
    @ValueSource(strings = { "iAd", "aaa", "GGG" })
    void shouldReturnAcronymInUpperCase(String acronym) {
        state.setAcronym(acronym);
        assertThat(state.getAcronym()).isEqualTo(acronym.toUpperCase());
    }

    @Test
    @DisplayName("Deve retornar vazio quando o acrônimo for nulo")
    void shouldReturnEmptyWhenAcronymIsNull() {
        var state = new State();
        assertThat(state.getAcronym()).isEmpty();
    }
}
