package br.com.bdk.eventsmanager.admin.company.parameter.api.v1.model.input;

import br.com.bdk.eventsmanager.core.ValidatorTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class CompanySignatureInputTest extends ValidatorTest {
    private CompanySignatureInput companySignatureInput;

    @BeforeEach
    void setUp() {
        companySignatureInput = new CompanySignatureInput();
        var mockMultipartFile = new MockMultipartFile("signature",
                "test.png", MediaType.IMAGE_PNG_VALUE, "test".getBytes());
        ReflectionTestUtils.setField(companySignatureInput, "signature", mockMultipartFile);
    }

    @Test
    void shouldValidate() {
        var violations = validator.validate(companySignatureInput);
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldValidateSignatureIsNull() {
        validateFieldIsNull(companySignatureInput, "signature");
    }

    @Test
    void shouldValidateSignatureContentType() {
        var multipartFile = new MockMultipartFile("signature",
                "test.txt", MediaType.TEXT_PLAIN_VALUE, "test".getBytes());
        validateField(companySignatureInput, "signature", multipartFile);
    }
}