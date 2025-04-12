package br.com.bdk.eventsmanager.admin.company.domain.service;

import br.com.bdk.eventsmanager.admin.address.domain.model.Address;
import br.com.bdk.eventsmanager.admin.city.domain.model.City;
import br.com.bdk.eventsmanager.admin.city.domain.service.CityRegistrationService;
import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.admin.company.domain.model.Company_;
import br.com.bdk.eventsmanager.admin.company.domain.model.exception.CompanyInUseException;
import br.com.bdk.eventsmanager.admin.company.domain.model.exception.CompanyNotFoundException;
import br.com.bdk.eventsmanager.admin.company.domain.repository.CompanyRepository;
import br.com.bdk.eventsmanager.admin.company.domain.validator.CompanyAccessService;
import br.com.bdk.eventsmanager.admin.company.domain.validator.CompanyRegistrationValidator;
import br.com.bdk.eventsmanager.common.ArgumentProviderUtil;
import br.com.bdk.eventsmanager.core.exception.BusinessException;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ValidationAutoConfiguration.class)
@SpringBootTest(classes = CompanyRegistrationService.class)
class CompanyRegistrationServiceTest {

    @Autowired
    private CompanyRegistrationService companyRegistrationService;

    @MockitoBean
    private CompanyRegistrationValidator companyRegistrationValidator;

    @MockitoBean
    private CityRegistrationService cityRegisterService;

    @MockitoBean
    private CompanyRepository companyRepository;

    @MockitoBean
    private CompanyAccessService companyAccessService;

    private Company company;
    private Address address;

    @BeforeEach
    void setUp() {
        company = new Company();
        company.setName("BDK Eventos");
        company.setEmail("email@email.com");
        company.setActive(true);
        company.setDocument("44361334862");
        company.setPhoneNumber("18981832227");
        company.setBusinessName("BDK");
        company.setStateRegistrationNumber("12314122312334");
        company.setCityRegistrationNumber("12311524123");

        address = new Address();
        address.setPostalCode("88058320");
        address.setStreet("Tv. Nildo Neponoceno Fernandes");
        address.setStreetNumber("397");
        address.setComplement("Apto 204, Bloco B");
        address.setNeighborhood("Ingleses do Rio Vermelho");
        address.setCity(new City("1L"));

        company.setAddress(address);
    }

    @Nested
    class WhenValidateAndSaveCompany {

        @Test
        void shouldValidateAndSaveNewCompany() {
            ReflectionTestUtils.setField(company, Company_.ID, null);

            doNothing().when(companyRegistrationValidator).validate(company);
            when(cityRegisterService.findByExternalId(address.getCity().getExternalId())).thenReturn(address.getCity());
            when(companyRepository.save(company)).thenReturn(company);

            final var companySaved = companyRegistrationService.validateAndSave(company);

            assertThat(companySaved)
                    .isNotNull()
                    .usingRecursiveComparison()
                    .isEqualTo(company);

            inOrder(companyRegistrationValidator, cityRegisterService, companyRepository);
            verify(companyAccessService, never()).validate(any());
        }

        @Test
        void shouldValidateAndSaveExistingCompany() {
            ReflectionTestUtils.setField(company, Company_.ID, Long.valueOf(RandomStringUtils.secure().nextNumeric(5)));

            doNothing().when(companyAccessService).validate(company.getExternalId());
            doNothing().when(companyRegistrationValidator).validate(company);
            when(cityRegisterService.findByExternalId(address.getCity().getExternalId())).thenReturn(address.getCity());
            when(companyRepository.save(company)).thenReturn(company);

            final var companySaved = companyRegistrationService.validateAndSave(company);

            assertThat(companySaved)
                    .isNotNull()
                    .usingRecursiveComparison()
                    .isEqualTo(company);

            inOrder(companyAccessService, companyRegistrationValidator, cityRegisterService, companyRepository);
        }

        @Test
        void shouldThrowExceptionWhenCompanyIsNull() {
            assertThatThrownBy(() -> companyRegistrationService.validateAndSave(null))
                    .isInstanceOf(ConstraintViolationException.class)
                    .hasMessage("validateAndSave.entity: não deve ser nulo");
            verifyNoInteractions(companyAccessService, companyRegistrationValidator, cityRegisterService, companyRepository);
        }

        @Nested
        class WhenValidateCompanyName {

            @Test
            void shouldThrowExceptionWhenIsNull() {
                ReflectionTestUtils.setField(company, Company_.NAME, null);
                assertThatThrownBy(() -> companyRegistrationService.validateAndSave(company))
                        .isInstanceOf(ConstraintViolationException.class)
                        .hasMessage("validateAndSave.entity.name: não deve estar em branco");
                verifyNoInteractions(companyAccessService, companyRegistrationValidator, cityRegisterService, companyRepository);
            }

            @ParameterizedTest
            @ArgumentsSource(ArgumentProviderUtil.BlankStringArgumentProvider.class)
            void shouldThrowExceptionWhenIsBlank(final String blankName) {
                ReflectionTestUtils.setField(company, Company_.NAME, blankName);
                assertThatThrownBy(() -> companyRegistrationService.validateAndSave(company))
                        .isInstanceOf(ConstraintViolationException.class);
                verifyNoInteractions(companyAccessService, companyRegistrationValidator, cityRegisterService, companyRepository);
            }

            @Test
            void shouldThrowExceptionWhenIsMoreThanSizePermitted() {
                ReflectionTestUtils.setField(company, Company_.NAME, RandomStringUtils.secure().nextAlphabetic(256));
                assertThatThrownBy(() -> companyRegistrationService.validateAndSave(company))
                        .isInstanceOf(ConstraintViolationException.class)
                        .hasMessage("validateAndSave.entity.name: tamanho deve ser entre 1 e 255");
                verifyNoInteractions(companyAccessService, companyRegistrationValidator, cityRegisterService, companyRepository);
            }
        }

        @Nested
        class WhenValidateCompanyBusinessName {

            @Test
            void shouldThrowExceptionWhenIsNull() {
                ReflectionTestUtils.setField(company, Company_.BUSINESS_NAME, null);
                assertThatThrownBy(() -> companyRegistrationService.validateAndSave(company))
                        .isInstanceOf(ConstraintViolationException.class)
                        .hasMessage("validateAndSave.entity.businessName: não deve estar em branco");
                verifyNoInteractions(companyAccessService, companyRegistrationValidator, cityRegisterService, companyRepository);
            }

            @ParameterizedTest
            @ArgumentsSource(ArgumentProviderUtil.BlankStringArgumentProvider.class)
            void shouldThrowExceptionWhenIsBlank(final String blankBusinessName) {
                ReflectionTestUtils.setField(company, Company_.BUSINESS_NAME, blankBusinessName);
                assertThatThrownBy(() -> companyRegistrationService.validateAndSave(company))
                        .isInstanceOf(ConstraintViolationException.class);
                verifyNoInteractions(companyAccessService, companyRegistrationValidator, cityRegisterService, companyRepository);
            }

            @Test
            void shouldThrowExceptionWhenIsMoreThanSizePermitted() {
                ReflectionTestUtils.setField(company, Company_.BUSINESS_NAME, RandomStringUtils.secure().nextAlphabetic(256));
                assertThatThrownBy(() -> companyRegistrationService.validateAndSave(company))
                        .isInstanceOf(ConstraintViolationException.class)
                        .hasMessage("validateAndSave.entity.businessName: tamanho deve ser entre 1 e 255");
                verifyNoInteractions(companyAccessService, companyRegistrationValidator, cityRegisterService, companyRepository);
            }
        }

        @Nested
        class WhenValidateCompanyEmail {

            @ParameterizedTest
            @ArgumentsSource(ArgumentProviderUtil.InvalidEmailArgumentProvider.class)
            void shouldThrowExceptionWhenIsMoreThanSizePermitted(final String invalidEmail) {
                ReflectionTestUtils.setField(company, Company_.EMAIL, invalidEmail);
                assertThatThrownBy(() -> companyRegistrationService.validateAndSave(company))
                        .isInstanceOf(ConstraintViolationException.class)
                        .hasMessage("validateAndSave.entity.email: deve ser um endereço de e-mail bem formado");
                verifyNoInteractions(companyAccessService, companyRegistrationValidator, cityRegisterService, companyRepository);
            }
        }

    }

    @Test
    void shouldValidateAndSave() {
        assertDoesNotThrow(() -> companyRegistrationService.validateAndSave(company));
        inOrder(companyRegistrationValidator, cityRegisterService, companyRepository);
        verify(companyRegistrationValidator, times(1)).validate(company);
        verify(cityRegisterService, times(1)).findByExternalId(anyString());
        verify(companyRepository, times(1)).save(company);
    }

    @Test
    void shouldValidateAndSaveWithNoAddress() {
        company.setAddress(null);
        assertDoesNotThrow(() -> companyRegistrationService.validateAndSave(company));
        inOrder(companyRegistrationValidator, companyRepository);
        verify(companyRegistrationValidator, times(1)).validate(company);
        verify(cityRegisterService, never()).findByExternalId(anyString());
        verify(companyRepository, times(1)).save(company);
    }

    @Test
    void shouldThrowExceptionAndNotSaveWhenValidationFail() {
        doThrow(BusinessException.class).when(companyRegistrationValidator).validate(company);
        assertThrows(BusinessException.class, () -> companyRegistrationService.validateAndSave(company));
        verify(cityRegisterService, never()).findByExternalId(anyString());
        verify(companyRepository, never()).save(any(Company.class));
    }

    @Test
    void shouldThrowExceptionWhenFindByExternalId() {
        String externalId = UUID.randomUUID().toString();
        var exception = assertThrows(CompanyNotFoundException.class,
                () -> companyRegistrationService.findByExternalId(externalId));
        assertEquals("Não foi encontrada uma empresa com código '%s'".formatted(externalId), exception.getMessage());
    }

    @Test
    void shouldRemoveByExternalId() {
        String externalId = UUID.randomUUID().toString();
        when(companyRepository.findByExternalId(externalId)).thenReturn(Optional.of(company));
        assertDoesNotThrow(() -> companyRegistrationService.removeByExternalId(externalId));
        verify(companyRepository, times(1)).delete(company);
        verify(companyRepository, times(1)).flush();
    }

    @Test
    void shouldThrowExceptionWhenRemoveByExternalId() {
        String externalId = UUID.randomUUID().toString();
        when(companyRepository.findByExternalId(externalId)).thenReturn(Optional.of(company));
        doThrow(DataIntegrityViolationException.class).when(companyRepository).delete(any(Company.class));
        var exception = assertThrows(CompanyInUseException.class, () -> companyRegistrationService.removeByExternalId(externalId));

        assertEquals("Empresa de código '%s' não pode ser removida, pois está em uso".formatted(externalId), exception.getMessage());
    }
}