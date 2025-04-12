package br.com.bdk.eventsmanager.admin.company.parameter.domain.service;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.admin.company.parameter.domain.model.CompanyParameter;
import br.com.bdk.eventsmanager.admin.company.parameter.domain.model.exception.CompanySignatureNotFoundException;
import br.com.bdk.eventsmanager.admin.company.parameter.domain.repository.CompanyParameterRepository;
import br.com.bdk.eventsmanager.core.storage.StorageFileService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.net.URI;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanySignatureRegistrationServiceTest {

    @InjectMocks
    private CompanySignatureRegisterService companySignatureRegisterService;

    @Mock
    private CompanyParameterRegisterService companyParameterRegisterService;

    @Mock
    private CompanyParameterRepository companyParameterRepository;

    @Mock
    private StorageFileService storageFileService;

    @Captor
    private ArgumentCaptor<CompanyParameter> companyParameterArgumentCaptor;

    private static final String COMPANY_EXTERNAL_ID = "123L";

    private CompanyParameter companyParameter;

    @BeforeEach
    void setUp() {
        companyParameter = new CompanyParameter();
        companyParameter.setCompany(new Company());
        companyParameter.setLogoUri("logo");
        companyParameter.setSignatureUri("signature");
        companyParameter.setInitialParagraphContract("<html></html>");
    }

    @Test
    void shouldSaveSignature() {
        when(companyParameterRegisterService.findByCompanyExternalIdOrCreate(COMPANY_EXTERNAL_ID))
                .thenReturn(companyParameter);

        final var signatureUri = UUID.randomUUID().toString();
        when(storageFileService.upload(any()))
                .thenReturn(signatureUri);

        final var newSignature = new MockMultipartFile("teste", "test".getBytes());
        companySignatureRegisterService.saveSignature(COMPANY_EXTERNAL_ID, newSignature);
        verify(companyParameterRepository, times(1)).save(companyParameterArgumentCaptor.capture());

        var companyParameterSaved = companyParameterArgumentCaptor.getValue();
        assertThat(companyParameterSaved.getSignatureUri()).isEqualTo(signatureUri);
    }

    @Test
    @SneakyThrows
    void shouldFindSignatureByCompanyExternalId() {
        when(companyParameterRegisterService.findByCompanyExternalIdOrFail(COMPANY_EXTERNAL_ID))
                .thenReturn(companyParameter);

        URL expectedURL = URI.create("http://teste.com.br").toURL();
        when(storageFileService.getTemporaryUrl(anyString()))
                .thenReturn(expectedURL);

        var result = companySignatureRegisterService.findSignatureByCompanyExternalId(COMPANY_EXTERNAL_ID);
        assertThat(result).isEqualTo(expectedURL);
    }

    @Test
    void shouldThrowExceptionWhenFindSignatureByCompanyExternalId() {
        when(companyParameterRegisterService.findByCompanyExternalIdOrFail(COMPANY_EXTERNAL_ID))
                .thenReturn(companyParameter);
        companyParameter.setSignatureUri(null);
        var exception = assertThrows(CompanySignatureNotFoundException.class,
                () -> companySignatureRegisterService.findSignatureByCompanyExternalId(COMPANY_EXTERNAL_ID));

        assertThat(exception.getMessage())
                .isEqualTo("Não foi possível encontrar a assinatura da empresa de código '%s'".formatted(COMPANY_EXTERNAL_ID));
    }

    @Test
    void shouldRemoveSignatureByCompanyExternalId() {
        when(companyParameterRegisterService.findByCompanyExternalId(COMPANY_EXTERNAL_ID))
                .thenReturn(Optional.of(companyParameter));

        companySignatureRegisterService.removeSignatureByCompanyExternalId(COMPANY_EXTERNAL_ID);

        verify(companyParameterRepository, times(1)).save(companyParameterArgumentCaptor.capture());
        var companyParameterSaved = companyParameterArgumentCaptor.getValue();
        assertThat(companyParameterSaved.getSignatureUri()).isNull();
    }

    @Test
    void shouldRemoveSignatureByCompanyExternalIdWhenNotExistsCompanyParameter() {
        when(companyParameterRegisterService.findByCompanyExternalId(COMPANY_EXTERNAL_ID)).thenReturn(Optional.empty());
        companySignatureRegisterService.removeSignatureByCompanyExternalId(COMPANY_EXTERNAL_ID);
        verifyNoInteractions(companyParameterRepository);
    }
}