package br.com.bdk.eventsmanager.admin.country.domain.service;

import br.com.bdk.eventsmanager.admin.country.domain.model.Country;
import br.com.bdk.eventsmanager.admin.country.domain.model.exception.CountryNotFoundException;
import br.com.bdk.eventsmanager.admin.country.domain.repository.CountryRepository;
import br.com.bdk.eventsmanager.admin.country.domain.validator.CountryRegistrationValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryRegistrationServiceTest {

    @InjectMocks
    private CountryRegisterService countryRegisterService;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CountryRegistrationValidator countryRegisterValidator;

    @Test
    @DisplayName("Deve validar e criar um novo país")
    void shouldValidadeAndCreateOrUpdateCountry() {
        var newCountry = new Country();
        countryRegisterService.saveAndValidate(newCountry);
        inOrder(countryRegisterValidator, countryRepository);
        verify(countryRegisterValidator, only()).validate(newCountry);
        verify(countryRepository, only()).save(newCountry);
    }

    @Test
    @DisplayName("Deve lançar exceção quando não encontrar o país a partir do id externo")
    void shouldThrowExceptionWhenCountryNotFoundByExternalId() {
        assertThrowsExactly(CountryNotFoundException.class,
                () -> countryRegisterService.findByExternalId("1L"));
    }

    @Test
    @DisplayName("Não deve lançar exceção quando página for nula ao consultar todos paises")
    void shouldDoesNotThrowExceptionWhenSearchCountries() {
        Pageable unpaged = Pageable.unpaged();
        when(countryRegisterService.findAll(null, unpaged)).thenReturn(Page.empty());
        var result = countryRegisterService.findAll(null, unpaged);
        assertThat(result.isEmpty()).isTrue();
    }
}