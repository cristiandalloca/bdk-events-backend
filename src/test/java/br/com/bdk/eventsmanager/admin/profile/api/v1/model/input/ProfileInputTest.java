package br.com.bdk.eventsmanager.admin.profile.api.v1.model.input;

import br.com.bdk.eventsmanager.core.ValidatorTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProfileInputTest extends ValidatorTest {

    private ProfileInput profileInput;

    @BeforeEach
    void setUp() {
        profileInput = new ProfileInput();
        ReflectionTestUtils.setField(profileInput, "name", "VENDEDOR");
        ReflectionTestUtils.setField(profileInput, "companyExternalId", "dfd197a2-d345-4834-90dc-57867c0e2fa0");

        var privilegesIds = List.of("dfd197a2-d345-4834-90dc-57867c0e2fa0", "dfd778a2-d345-4834-90dc-57867c0e2fa0");
        ReflectionTestUtils.setField(profileInput, "privilegesExternalIds", privilegesIds);
    }

    @Test
    void shouldValidate() {
        var violations = validator.validate(profileInput);
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldValidateNameIsNull() {
        validateFieldIsNull(profileInput, "name");
    }

    @Test
    void shouldValidateCompanyExternalIdIsNull() {
        validateFieldIsNull(profileInput, "companyExternalId");
    }

    @Test
    void shouldValidateNameIsBlank() {
        validateField(profileInput, "name", " ");
    }

    @Test
    void shouldValidateCompanyExternalIdIsBlank() {
        validateField(profileInput, "companyExternalId", " ");
    }

    @Test
    void shouldValidateNameMaxSize() {
        validateAlphaNumericField(profileInput, "name", 255);
    }

    @Test
    void shouldValidateCompanyExternalIdMaxSize() {
        validateAlphaNumericField(profileInput, "companyExternalId", 36);
    }
}