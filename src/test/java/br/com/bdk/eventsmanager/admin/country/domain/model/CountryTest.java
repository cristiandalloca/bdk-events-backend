package br.com.bdk.eventsmanager.admin.country.domain.model;

import br.com.bdk.eventsmanager.core.ValidatorTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;


class CountryTest extends ValidatorTest {

    private Country country;

    @BeforeEach
    void setUp() {
        country = new Country();
        country.setName("Brasil");
        country.setAcronym("BRA");
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome ultrapassar 255 caracteres")
    void shouldExceptionWhenNameSizeIsMoreThan255Characters() {
        validateAlphaNumericField(country, "name", 255);
    }

    @Test
    @DisplayName("Deve lançar exceção quando abreviação ultrapassar 3 caracteres")
    void shouldExceptionWhenAcronymSizeIsMoreThan3Characters() {
        validateAlphabeticFieldMaxSize(country, "acronym", 3);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    @DisplayName("Deve lançar exceção quando nome for vazio")
    void shouldExceptionWhenNameIsBlank(String name) {
        validateField(country, "name", name);
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome for nulo")
    void shouldExceptionWhenNameIsNull() {
        validateFieldIsNull(country, "name");
    }

    @Test
    @DisplayName("Não deve lançar exceção ao construir object")
    void shouldConstructObject() {
        var violations = validator.validate(country);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Deve lançar exceção de apenas letras para a abreviação")
    void shouldExceptionWhenAcronymContainsNonLettersCharacter() {
        validateField(country, "acronym", "Ab1");
    }

    @ParameterizedTest
    @DisplayName("Deve retornar o acrônimo em uppercase")
    @ValueSource(strings = {"iAd", "iii", "GGG"})
    void shouldReturnAcronymInUpperCase(String acronym) {
        country.setAcronym(acronym);
        assertThat(country.getAcronym()).isEqualTo(acronym.toUpperCase());
    }

    @Test
    @DisplayName("Deve retornar vazio quando acronym for nulo")
    void shouldReturnEmptyWhenAcronymIsNull() {
        var country = new Country();
        assertThat(country.getAcronym()).isEmpty();
    }

    @Test
    @DisplayName("Deve instanciar um novo pais com id externo")
    void shouldInstantiateCountryWithExternalId() {
        String externalId = "123-ABC";
        var newCountry = new Country(externalId);
        assertThat(newCountry.getExternalId()).isEqualTo(externalId);
    }
}
