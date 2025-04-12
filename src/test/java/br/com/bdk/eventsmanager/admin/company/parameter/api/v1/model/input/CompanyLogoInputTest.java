package br.com.bdk.eventsmanager.admin.company.parameter.api.v1.model.input;

import br.com.bdk.eventsmanager.core.ValidatorTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyLogoInputTest extends ValidatorTest {

    private CompanyLogoInput companyLogoInput;

    @BeforeEach
    void setUp() {
        companyLogoInput = new CompanyLogoInput();
        var mockMultipartFile = new MockMultipartFile("logo",
                "test.png", MediaType.IMAGE_PNG_VALUE, "test".getBytes());
        companyLogoInput.setLogo(mockMultipartFile);
    }

    @Test
    void shouldValidate() {
        var violations = validator.validate(companyLogoInput);
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldValidateLogoIsNull() {
        validateField(companyLogoInput, "logo");
    }

    @Test
    void shouldValidateLogoContentType() {
        var multipartFile = new MockMultipartFile("logo",
                "test.txt", MediaType.TEXT_PLAIN_VALUE, "test".getBytes());
        validateField(companyLogoInput, "logo", multipartFile);
    }
}