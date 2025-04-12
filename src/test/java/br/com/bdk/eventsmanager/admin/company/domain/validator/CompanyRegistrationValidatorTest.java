package br.com.bdk.eventsmanager.admin.company.domain.validator;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.admin.company.domain.model.exception.DuplicateDocumentCompanyException;
import br.com.bdk.eventsmanager.admin.company.domain.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyRegistrationValidatorTest {

    @InjectMocks
    private CompanyRegistrationValidator companyRegisterValidator;

    @Mock
    private CompanyRepository companyRepository;

    @Test
    void shouldValidateWhenNotExistingCompanyWithSameDocument() {
        Company company = new Company();
        company.setDocument("123");

        when(companyRepository.findByDocument(company.getDocument())).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> companyRegisterValidator.validate(company));
    }

    @Test
    void shouldValidateWhenExistingCompanyButIsSameCompany() {
        Company company = new Company();
        ReflectionTestUtils.setField(company, "id", 1L);
        company.setDocument("123");
        when(companyRepository.findByDocument(company.getDocument())).thenReturn(Optional.of(company));
        assertDoesNotThrow(() -> companyRegisterValidator.validate(company));
    }

    @Test
    void shouldThrowExceptionWhenExistingCompanyByDocument() {
        Company company = new Company();
        ReflectionTestUtils.setField(company, "id", 1L);
        company.setDocument("123");
        when(companyRepository.findByDocument(company.getDocument())).thenReturn(Optional.of(new Company()));

        var exception = assertThrows(DuplicateDocumentCompanyException.class, () -> companyRegisterValidator.validate(company));
        assertEquals("Já existe uma empresa com CPF/CNPJ '%s'".formatted(company.getDocument()), exception.getMessage());
    }
}