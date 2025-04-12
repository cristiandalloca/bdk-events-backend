package br.com.bdk.eventsmanager.admin.company.api.v1.model.input;

import br.com.bdk.eventsmanager.core.ValidatorTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyInputTest extends ValidatorTest {

    private CompanyInput companyInput;

    @BeforeEach
    void setUp() {
        companyInput = new CompanyInput();
        ReflectionTestUtils.setField(companyInput, "name", "BDK Events Manager");
        ReflectionTestUtils.setField(companyInput, "businessName", "BDK");
        ReflectionTestUtils.setField(companyInput, "document", "44361334862");
        ReflectionTestUtils.setField(companyInput, "postalCode", "88058320");
        ReflectionTestUtils.setField(companyInput, "street", "Tv. Nildo Neponoceno Fernandes");
        ReflectionTestUtils.setField(companyInput, "neighborhood", "Ingleses do Rio Vermelho");
        ReflectionTestUtils.setField(companyInput, "cityId", "dfd197a2-d345-4834-90dc-57867c0e2fa0");
        ReflectionTestUtils.setField(companyInput, "active", Boolean.TRUE);
    }

    @Test
    void shouldValidateNoViolations() {
        var violations = validator.validate(companyInput);
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldValidateNameIsNull() {
        validateFieldIsNull(companyInput, "name");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void shouldValidateNameIsBlank(String name) {
        validateField(companyInput, "name", name);
    }

    @Test
    void shouldValidateNameMaxSize() {
        validateAlphabeticFieldMaxSize(companyInput, "name", 255);
    }

    @Test
    void shouldValidateBusinessNameIsNull() {
        validateFieldIsNull(companyInput, "businessName");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void shouldValidateBusinessNameIsBlank(String businessName) {
        validateField(companyInput, "businessName", businessName);
    }

    @Test
    void shouldValidateDocumentIsNull() {
        validateFieldIsNull(companyInput, "document");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void shouldValidateDocumentIsBlank(String document) {
        validateField(companyInput, "document", document);
    }

    @ParameterizedTest
    @ValueSource(strings = {"4436133", "32123", "44361334861"})
    void shouldValidateDocumentIsValid(String document) {
        validateField(companyInput, "document", document);
    }

    @Test
    void shouldValidateStateRegistrationNumberMaxSize() {
        validateNumericFieldMaxSize(companyInput, "stateRegistrationNumber", 14);
    }

    @ParameterizedTest
    @ValueSource(strings = {"aa23f", "12a", "   2 23aaa"})
    void shouldValidateStateRegistrationNumberOnlyNumbers(String stateRegistrationNumber) {
        validateField(companyInput, "stateRegistrationNumber", stateRegistrationNumber);
    }

    @Test
    void shouldValidateCityRegistrationNumberMaxSize() {
        validateNumericFieldMaxSize(companyInput, "cityRegistrationNumber", 11);
    }

    @ParameterizedTest
    @ValueSource(strings = {"aa23f", "12a", "   2 23aaa"})
    void shouldValidateCityRegistrationNumberOnlyNumbers(String cityRegistrationNumber) {
        validateField(companyInput, "cityRegistrationNumber", cityRegistrationNumber);
    }

    @Test
    void shouldValidateEmailMaxSize() {
        String email = """
            a@a.com.brrrrrrrrrrrrrrrbrrrrrrrrrrrrrrrbrrrrrrrrrrrrrrrbrrrrrrrrrrrrrrrbrrrrrrrrrrrrrrrbrrrrrrrrrrrrrrr
            brrrrrrr
        """;
        validateField(companyInput, "email", email);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a@", "@", "sdasd1.com"})
    void shouldValidateEmailIsValid(String email) {
        validateField(companyInput, "email", email);
    }

    @ParameterizedTest
    @ValueSource(strings = {"232a", "basdb23123", "asdbxcffa"})
    void shouldValidatePhoneNumberIsValid(String phoneNumber) {
        validateField(companyInput, "phoneNumber", phoneNumber);
    }

    @Test
    void shouldValidatepostalCodeIsNull() {
        validateFieldIsNull(companyInput, "postalCode");
    }

    @Test
    void shouldValidatepostalCodeIsBlank() {
        validateField(companyInput, "postalCode", "        ");
    }

    @Test
    void shouldValidatepostalCodeMaxSize() {
        validateNumericFieldMaxSize(companyInput, "postalCode", 8);
    }

    @ParameterizedTest
    @ValueSource(strings = {"aa2ff3ff", "1124aa12", "   2 23a"})
    void shouldValidatepostalCodeOnlyNumbers(String postalCode) {
        validateField(companyInput, "postalCode", postalCode);
    }

    @Test
    void shouldValidateStreetIsNull() {
        validateFieldIsNull(companyInput, "street");
    }

    @Test
    void shouldValidateStreetIsBlank() {
        validateField(companyInput, "street", "   ");
    }

    @Test
    void shouldValidateStreetMaxSize() {
        validateAlphaNumericField(companyInput, "street", 255);
    }

    @Test
    void shouldValidateStreetNumberMaxSize() {
        validateNumericFieldMaxSize(companyInput, "streetNumber", 12);
    }

    @Test
    void shouldValidateneighborhoodIsNull() {
        validateFieldIsNull(companyInput, "neighborhood");
    }

    @Test
    void shouldValidateneighborhoodIsBlank() {
        validateField(companyInput, "neighborhood", "   ");
    }

    @Test
    void shouldValidateneighborhoodMaxSize() {
        validateNumericFieldMaxSize(companyInput, "neighborhood", 255);
    }

    @Test
    void shouldValidateComplementMaxSize() {
        validateNumericFieldMaxSize(companyInput, "complement", 50);
    }

    @Test
    void shouldValidateCityIdIsNull() {
        validateFieldIsNull(companyInput, "cityId");
    }

    @Test
    void shouldValidateCityIdIsBlank() {
        validateField(companyInput, "cityId", "   ");
    }

    @Test
    void shouldValidateCityIdMaxSize() {
        validateAlphaNumericField(companyInput, "cityId", 36);
    }

    @Test
    void shouldValidateActiveIsNull() {
        validateFieldIsNull(companyInput, "active");
    }
}