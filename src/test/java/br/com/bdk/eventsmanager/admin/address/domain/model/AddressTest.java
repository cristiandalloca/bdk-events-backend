package br.com.bdk.eventsmanager.admin.address.domain.model;

import br.com.bdk.eventsmanager.admin.city.domain.model.City;
import br.com.bdk.eventsmanager.core.ValidatorTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class AddressTest extends ValidatorTest {

    private Address address;

    @BeforeEach
    void setUp() {
        address = new Address();
        address.setPostalCode("88058320");
        address.setStreet("Tv Nildo Neponoceno Fernandes");
        address.setStreetNumber("411");
        address.setNeighborhood("Ingleses do Rio Vermelho");
        address.setComplement("Rua do supermercado Super Sol");
        address.setCity(new City());
    }

    @Test
    void shouldValidateNoViolations() {
        var violations = validator.validate(address);
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldValidateZipCodeIsNull() {
        validateFieldIsNull(address, "postalCode");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void shouldValidateZipCodeIsBlank(String postalCode) {
        validateField(address, "postalCode", postalCode);
    }

    @Test
    void shouldValidateZipCodeMaxSize() {
        validateNumericFieldMaxSize(address, "postalCode", 8);
    }

    @ParameterizedTest
    @ValueSource(strings = {" 1231-", "a", "1234 245"})
    void shouldValidateZipCodeOnlyNumbers(String postalCode) {
        validateField(address, "postalCode", postalCode);
    }

    @Test
    void shouldValidateStreetIsNull() {
        validateFieldIsNull(address, "street");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void shouldValidateStreetIsBlank(String street) {
        validateField(address, "street", street);
    }

    @Test
    void shouldValidateStreetMaxSize() {
        validateAlphaNumericField(address, "street", 255);
    }

    @Test
    void shouldValidateStreetNumberMaxSize() {
        validateAlphaNumericField(address, "streetNumber", 12);
    }

    @Test
    void shouldValidateDistrictIsNull() {
        validateFieldIsNull(address, "neighborhood");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void shouldValidateDistrictIsBlank(String neighborhood) {
        validateField(address, "neighborhood", neighborhood);
    }

    @Test
    void shouldValidateDistrictMaxSize() {
        validateAlphaNumericField(address, "neighborhood", 255);
    }

    @Test
    void shouldValidateComplementMaxSize() {
        validateAlphaNumericField(address, "complement", 50);
    }

    @Test
    void shouldValidateCityIsNull() {
       validateFieldIsNull(address, "city");
    }

}