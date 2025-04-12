package br.com.bdk.eventsmanager.admin.address.domain.service;

import br.com.bdk.eventsmanager.admin.address.domain.model.exception.PostalCodeNotFoundException;
import br.com.bdk.eventsmanager.admin.address.domain.model.viacep.AddressResponse;
import br.com.bdk.eventsmanager.admin.city.domain.model.City;
import br.com.bdk.eventsmanager.admin.city.domain.service.CityRegistrationService;
import br.com.bdk.eventsmanager.admin.country.domain.model.Country;
import br.com.bdk.eventsmanager.admin.country.domain.service.CountryRegisterService;
import br.com.bdk.eventsmanager.admin.state.domain.model.State;
import br.com.bdk.eventsmanager.admin.state.domain.service.StateRegisterService;
import br.com.bdk.eventsmanager.common.EnvironmentMock;
import br.com.bdk.eventsmanager.core.internationalization.InternationalizationConfiguration;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {ValidationAutoConfiguration.class, InternationalizationConfiguration.class})
@SpringBootTest(classes = AddressSearchService.class)
class AddressSearchServiceTest {

    private static final String POSTAL_CODE = "88058320";
    private static final String MOCK_API_SEARCH_BY_POSTAL_CODE = "https://mock.%s.com";

    @Autowired
    private AddressSearchService addressSearchService;

    @MockitoBean
    private CityRegistrationService cityRegisterService;

    @MockitoBean
    private StateRegisterService stateRegisterService;

    @MockitoBean
    private CountryRegisterService countryRegisterService;

    @MockitoBean
    private RestTemplate restTemplate;

    @Captor
    private ArgumentCaptor<City> cityArgumentCaptor;

    @Captor
    private ArgumentCaptor<State> stateArgumentCaptor;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(addressSearchService, "urlApiSearchAddress", MOCK_API_SEARCH_BY_POSTAL_CODE);
    }

    @Nested
    class WhenFindByPostalCode {

        @Test
        void shouldThrowExceptionWhenPostalCodeIsNull() {
            final var exception = assertThrows(ConstraintViolationException.class, () -> addressSearchService.findByPostalCode(null));
            assertThat(exception).hasMessage("findByPostalCode.postalCode: não deve estar em branco");
            verifyNoInteractions(restTemplate, cityRegisterService, stateRegisterService, countryRegisterService);
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " ", "  ", "    "})
        void shouldThrowExceptionWhenPostalCodeIsBlank(final String postalCodeBlank) {
            final var exception = assertThrows(ConstraintViolationException.class, () -> addressSearchService.findByPostalCode(postalCodeBlank));
            assertThat(exception).hasMessage("findByPostalCode.postalCode: não deve estar em branco");
            assertThat(exception.getConstraintViolations())
                    .hasSize(1);
            verifyNoInteractions(restTemplate, cityRegisterService, stateRegisterService, countryRegisterService);
        }

        @Test
        void shouldThrowExceptionWhenPostalCodeSizeIsNotExact() {
            String postalCode = RandomStringUtils.secure().nextNumeric(9);
            final var exception = assertThrows(ConstraintViolationException.class, () -> addressSearchService.findByPostalCode(postalCode));
            assertThat(exception).hasMessage("findByPostalCode.postalCode: Tamanho inválido. O tamanho deve ser 8 caracteres");
            verifyNoInteractions(restTemplate, cityRegisterService, stateRegisterService, countryRegisterService);
        }

        @Test
        void shouldThrowExceptionWhenPostalCodeContainsAlphanumericCharacters() {
            final var invalidPostalCode = RandomStringUtils.secure().nextAlphanumeric(8);
            final var exception = assertThrows(ConstraintViolationException.class, () -> addressSearchService.findByPostalCode(invalidPostalCode));
            assertThat(exception).hasMessage("findByPostalCode.postalCode: Somente números são permitido");
            verifyNoInteractions(restTemplate, cityRegisterService, stateRegisterService, countryRegisterService);
        }


        @Test
        void shouldFindAddressByZipCode() {
            var addressResponse = new AddressResponse();
            ReflectionTestUtils.setField(addressResponse, "street", "Travessa Nildo Neponoceno Fernandes");
            ReflectionTestUtils.setField(addressResponse, "neighborhood", "Ingleses do Rio Vermelho");
            ReflectionTestUtils.setField(addressResponse, "cityName", "Florianópolis");

            when(restTemplate.getForObject(MOCK_API_SEARCH_BY_POSTAL_CODE.formatted(POSTAL_CODE), AddressResponse.class))
                    .thenReturn(addressResponse);

            var city = new City();
            when(cityRegisterService.findByNameIgnoreCase(addressResponse.getCityName())).thenReturn(city);

            var result = addressSearchService.findByPostalCode(POSTAL_CODE);
            assertThat(result).isNotNull();
            assertThat(result.getPostalCode()).isEqualTo(POSTAL_CODE);
            assertThat(result.getCity()).isEqualTo(city);
            assertThat(result.getStreet()).isEqualTo(addressResponse.getStreet());
            assertThat(result.getNeighborhood()).isEqualTo(addressResponse.getNeighborhood());
        }

        @Test
        void shouldThrowExceptionWhenApiThrowException() {
            doThrow(RestClientException.class).when(restTemplate).getForObject(MOCK_API_SEARCH_BY_POSTAL_CODE.formatted(POSTAL_CODE), AddressResponse.class);
            final var exception = assertThrows(PostalCodeNotFoundException.class,
                    () -> addressSearchService.findByPostalCode(POSTAL_CODE));
            assertThat(exception).hasMessage("Não foi encontrado um endereço para o CEP '%s'".formatted(POSTAL_CODE));
            verifyNoInteractions(cityRegisterService, stateRegisterService, countryRegisterService);
        }

        @Test
        void shouldThrowExceptionWhenApiReturnNull() {
            when(restTemplate.getForObject(MOCK_API_SEARCH_BY_POSTAL_CODE.formatted(POSTAL_CODE), AddressResponse.class))
                    .thenReturn(null);
            final var exception = assertThrows(PostalCodeNotFoundException.class,
                    () -> addressSearchService.findByPostalCode(POSTAL_CODE));
            assertThat(exception).hasMessage("Não foi encontrado um endereço para o CEP '%s'".formatted(POSTAL_CODE));
            verifyNoInteractions(cityRegisterService, stateRegisterService, countryRegisterService);
        }

        @Test
        void shouldThrowExceptionWhenApiReturnStreetAndNeighborhoodNull() {
            final var addressResponse = new AddressResponse();
            when(restTemplate.getForObject(MOCK_API_SEARCH_BY_POSTAL_CODE.formatted(POSTAL_CODE), AddressResponse.class))
                    .thenReturn(addressResponse);
            final var exception = assertThrows(PostalCodeNotFoundException.class,
                    () -> addressSearchService.findByPostalCode(POSTAL_CODE));
            assertThat(exception).hasMessage("Não foi encontrado um endereço para o CEP '%s'".formatted(POSTAL_CODE));
            verifyNoInteractions(cityRegisterService, stateRegisterService, countryRegisterService);
        }

        @Test
        void shouldThrowExceptionWhenUrlApiSearchIsBlank() {
            ReflectionTestUtils.setField(addressSearchService, "urlApiSearchAddress", null);
            var exception = assertThrows(IllegalArgumentException.class, () -> addressSearchService.findByPostalCode(POSTAL_CODE));
            assertThat(exception.getMessage()).isEqualTo("Parameter urlApiSearchAddress is required");
            verifyNoInteractions(restTemplate, cityRegisterService, stateRegisterService, countryRegisterService);
        }

        @Test
        void shouldRegisterCityWhenNotExists() {
            final var addressResponse = EnvironmentMock.mock(AddressResponse.class);
            when(restTemplate.getForObject(MOCK_API_SEARCH_BY_POSTAL_CODE.formatted(POSTAL_CODE), AddressResponse.class))
                    .thenReturn(addressResponse);

            when(cityRegisterService.findByNameIgnoreCase(addressResponse.getCityName()))
                    .thenReturn(null);

            final var existingState = EnvironmentMock.mock(State.class);
            when(stateRegisterService.findByAcronymIgnoreCase(addressResponse.getStateAcronym()))
                    .thenReturn(existingState);

            final var newCity = EnvironmentMock.mock(City.class);
            when(cityRegisterService.validateAndSave(cityArgumentCaptor.capture()))
                    .thenReturn(newCity);

            final var result = addressSearchService.findByPostalCode(POSTAL_CODE);
            assertThat(result).isNotNull();
            assertThat(result.getPostalCode()).isEqualTo(POSTAL_CODE);
            assertThat(result.getStreet()).isEqualTo(addressResponse.getStreet());
            assertThat(result.getNeighborhood()).isEqualTo(addressResponse.getNeighborhood());
            assertThat(result.getCity()).
                    usingRecursiveComparison()
                    .isEqualTo(newCity);

            final var capturedCity = cityArgumentCaptor.getValue();
            assertThat(capturedCity.getState())
                    .usingRecursiveComparison()
                    .isEqualTo(existingState);

            verifyNoInteractions(countryRegisterService);
        }

        @Test
        void shouldRegisterStateWhenNotExists() {
            final var addressResponse = EnvironmentMock.mock(AddressResponse.class);
            when(restTemplate.getForObject(MOCK_API_SEARCH_BY_POSTAL_CODE.formatted(POSTAL_CODE), AddressResponse.class))
                    .thenReturn(addressResponse);

            when(cityRegisterService.findByNameIgnoreCase(addressResponse.getCityName()))
                    .thenReturn(null);

            when(stateRegisterService.findByAcronymIgnoreCase(addressResponse.getStateAcronym()))
                    .thenReturn(null);

            final var existingCountry = EnvironmentMock.mock(Country.class);
            when(countryRegisterService.findByAcronymIgnoreCase("BRA"))
                    .thenReturn(existingCountry);

            final var newState = EnvironmentMock.mock(State.class);
            when(stateRegisterService.saveAndValidate(stateArgumentCaptor.capture()))
                    .thenReturn(newState);

            final var newCity = EnvironmentMock.mock(City.class);
            newCity.setState(newState);
            when(cityRegisterService.validateAndSave(cityArgumentCaptor.capture()))
                    .thenReturn(newCity);

            final var result = addressSearchService.findByPostalCode(POSTAL_CODE);
            assertThat(result).isNotNull();
            assertThat(result.getPostalCode()).isEqualTo(POSTAL_CODE);
            assertThat(result.getStreet()).isEqualTo(addressResponse.getStreet());
            assertThat(result.getNeighborhood()).isEqualTo(addressResponse.getNeighborhood());
            assertThat(result.getCity()).
                    usingRecursiveComparison()
                    .isEqualTo(newCity);

            final var capturedState = stateArgumentCaptor.getValue();
            assertThat(capturedState.getCountry())
                    .usingRecursiveComparison()
                    .isEqualTo(existingCountry);

            final var capturedCity = cityArgumentCaptor.getValue();
            assertThat(capturedCity.getState())
                    .usingRecursiveComparison()
                    .isEqualTo(newState);
        }

        @Test
        void shouldRegisterCountryWhenNotExists() {
            final var addressResponse = EnvironmentMock.mock(AddressResponse.class);
            when(restTemplate.getForObject(MOCK_API_SEARCH_BY_POSTAL_CODE.formatted(POSTAL_CODE), AddressResponse.class))
                    .thenReturn(addressResponse);

            when(cityRegisterService.findByNameIgnoreCase(addressResponse.getCityName()))
                    .thenReturn(null);

            when(stateRegisterService.findByAcronymIgnoreCase(addressResponse.getStateAcronym()))
                    .thenReturn(null);

            when(countryRegisterService.findByAcronymIgnoreCase("BRA"))
                    .thenReturn(null);

            final var newCountry = EnvironmentMock.mock(Country.class);
            when(countryRegisterService.saveAndValidate(any(Country.class)))
                    .thenReturn(newCountry);

            final var newState = EnvironmentMock.mock(State.class);
            newState.setCountry(newCountry);
            when(stateRegisterService.saveAndValidate(stateArgumentCaptor.capture()))
                    .thenReturn(newState);

            final var newCity = EnvironmentMock.mock(City.class);
            newCity.setState(newState);
            when(cityRegisterService.validateAndSave(cityArgumentCaptor.capture()))
                    .thenReturn(newCity);

            final var result = addressSearchService.findByPostalCode(POSTAL_CODE);
            assertThat(result).isNotNull();
            assertThat(result.getPostalCode()).isEqualTo(POSTAL_CODE);
            assertThat(result.getStreet()).isEqualTo(addressResponse.getStreet());
            assertThat(result.getNeighborhood()).isEqualTo(addressResponse.getNeighborhood());
            assertThat(result.getCity()).
                    usingRecursiveComparison()
                    .isEqualTo(newCity);
        }

        @Test
        void shouldReturnCityNullWhenAddressResponseNotExistsCityAndStateInfo() {
            final var addressResponse = EnvironmentMock.mock(AddressResponse.class);
            ReflectionTestUtils.setField(addressResponse, "cityName", null);

            when(restTemplate.getForObject(MOCK_API_SEARCH_BY_POSTAL_CODE.formatted(POSTAL_CODE), AddressResponse.class))
                    .thenReturn(addressResponse);
            when(cityRegisterService.findByNameIgnoreCase(addressResponse.getCityName()))
                    .thenReturn(null);

            final var result = addressSearchService.findByPostalCode(POSTAL_CODE);
            assertThat(result).isNotNull();
            assertThat(result.getPostalCode()).isEqualTo(POSTAL_CODE);
            assertThat(result.getStreet()).isEqualTo(addressResponse.getStreet());
            assertThat(result.getNeighborhood()).isEqualTo(addressResponse.getNeighborhood());
            assertThat(result.getCity()).isNull();
            verifyNoInteractions(stateRegisterService, countryRegisterService);
        }
    }

}