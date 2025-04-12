package br.com.bdk.eventsmanager.admin.company.parameter.domain.service;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.admin.company.domain.service.CompanyRegistrationService;
import br.com.bdk.eventsmanager.admin.company.parameter.domain.model.CompanyParameter;
import br.com.bdk.eventsmanager.admin.company.parameter.domain.model.exception.CompanyParameterNotFoundException;
import br.com.bdk.eventsmanager.admin.company.parameter.domain.repository.CompanyParameterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyParameterRegistrationServiceTest {

    @InjectMocks
    private CompanyParameterRegisterService companyParameterRegisterService;

    @Mock
    private CompanyRegistrationService companyRegistrationService;

    @Mock
    private CompanyParameterRepository companyParameterRepository;

    @Captor
    private ArgumentCaptor<CompanyParameter> companyParameterArgumentCaptor;

    private static final String COMPANY_EXTERNAL_ID = "123L";

    private CompanyParameter companyParameter;

    @BeforeEach
    void setUp() {
        companyParameter = new CompanyParameter();
        Company company = new Company();
        ReflectionTestUtils.setField(company, "id", 1L);

        companyParameter.setCompany(company);
        companyParameter.setLogoUri("logo");
        companyParameter.setSignatureUri("signature");
        companyParameter.setInitialParagraphContract("<html></html>");
    }

    @Test
    void shouldFindByCompanyExternalIdOrCreateWhenExistsCompanyParameter() {
        when(companyRegistrationService.findByExternalId(COMPANY_EXTERNAL_ID)).thenReturn(companyParameter.getCompany());
        when(companyParameterRepository.findByCompanyId(companyParameter.getCompany().getId()))
                .thenReturn(Optional.of(companyParameter));
        var result = companyParameterRegisterService.findByCompanyExternalIdOrCreate(COMPANY_EXTERNAL_ID);
        assertThat(result).isNotNull().isEqualTo(companyParameter);
    }

    @Test
    void shouldFindByCompanyExternalIdOrCreateWhenNotExistsCompanyParameter() {
        when(companyRegistrationService.findByExternalId(COMPANY_EXTERNAL_ID)).thenReturn(companyParameter.getCompany());
        when(companyParameterRepository.findByCompanyId(companyParameter.getCompany().getId()))
                .thenReturn(Optional.empty());
        var result = companyParameterRegisterService.findByCompanyExternalIdOrCreate(COMPANY_EXTERNAL_ID);
        assertThat(result).isNotNull().isNotEqualTo(companyParameter);
    }

    @Test
    void shouldFindByCompanyExternalIdOrFail() {
        when(companyRegistrationService.findByExternalId(COMPANY_EXTERNAL_ID)).thenReturn(companyParameter.getCompany());
        when(companyParameterRepository.findByCompanyId(companyParameter.getCompany().getId()))
                .thenReturn(Optional.of(companyParameter));
        var result = companyParameterRegisterService.findByCompanyExternalIdOrFail(COMPANY_EXTERNAL_ID);
        assertThat(result).isNotNull().isEqualTo(companyParameter);
    }

    @Test
    void shouldThrowExceptionWhenFindByCompanyExternalIdOrFail() {
        when(companyRegistrationService.findByExternalId(COMPANY_EXTERNAL_ID)).thenReturn(companyParameter.getCompany());
        when(companyParameterRepository.findByCompanyId(companyParameter.getCompany().getId())).thenReturn(Optional.empty());
        var exception = assertThrows(CompanyParameterNotFoundException.class,
                () -> companyParameterRegisterService.findByCompanyExternalIdOrFail(COMPANY_EXTERNAL_ID));
        assertThat(exception.getMessage())
                .isEqualTo("Não foi possível encontrar parametros para a empresa de código '%s'".formatted(COMPANY_EXTERNAL_ID));
    }

    @Test
    void shouldFindByCompanyExternalIdWhenExistsCompanyParameter() {
        when(companyRegistrationService.findByExternalId(COMPANY_EXTERNAL_ID)).thenReturn(companyParameter.getCompany());
        when(companyParameterRepository.findByCompanyId(companyParameter.getCompany().getId())).thenReturn(Optional.of(companyParameter));
        var result = companyParameterRegisterService.findByCompanyExternalId(COMPANY_EXTERNAL_ID);
        assertThat(result).isNotNull().isEqualTo(Optional.of(companyParameter));
    }

    @Test
    void shouldSave() {
        companyParameterRegisterService.save(companyParameter);
        verify(companyParameterRepository, times(1)).save(companyParameterArgumentCaptor.capture());
        var companyParameterSaved = companyParameterArgumentCaptor.getValue();
        assertThat(companyParameterSaved).isEqualTo(companyParameter);
    }
}