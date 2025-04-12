package br.com.bdk.eventsmanager.admin.address.domain.service;

import br.com.bdk.eventsmanager.admin.address.domain.model.Address;
import br.com.bdk.eventsmanager.admin.address.domain.model.exception.PostalCodeNotFoundException;
import br.com.bdk.eventsmanager.admin.address.domain.model.viacep.AddressResponse;
import br.com.bdk.eventsmanager.admin.city.domain.model.City;
import br.com.bdk.eventsmanager.admin.city.domain.service.CityRegistrationService;
import br.com.bdk.eventsmanager.admin.country.domain.model.Country;
import br.com.bdk.eventsmanager.admin.country.domain.service.CountryRegisterService;
import br.com.bdk.eventsmanager.admin.state.domain.model.State;
import br.com.bdk.eventsmanager.admin.state.domain.service.StateRegisterService;
import br.com.bdk.eventsmanager.core.validator.ExactSize;
import br.com.bdk.eventsmanager.core.validator.OnlyNumbers;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.apache.commons.lang3.ObjectUtils.allNull;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class AddressSearchService {

    private static final String BRAZIL_ACRONYM = "BRA";
    private static final String BRAZIL_NAME = "Brasil";

    @Value("${address.find-by-postal-code.api}")
    private String urlApiSearchAddress;

    private final CityRegistrationService cityRegisterService;
    private final StateRegisterService stateRegisterService;
    private final CountryRegisterService countryRegisterService;
    private final RestTemplate restTemplate;

    public Address findByPostalCode(@NotBlank @ExactSize(size = 8) @OnlyNumbers String postalCode) {
        this.validatePropertyUrlApiInitialization();

        AddressResponse addressResponse = null;
        boolean postalCodeFound = false;
        try {
            addressResponse = restTemplate.getForObject(urlApiSearchAddress.formatted(postalCode), AddressResponse.class);
            postalCodeFound = true;
        } catch (RestClientException e) {
            log.error("Falha ao recuperar endereço do endpoint: {}", urlApiSearchAddress, e);
        }

        if (!postalCodeFound || addressResponse == null
                || allNull(addressResponse.getStreet(), addressResponse.getNeighborhood())) {
            throw new PostalCodeNotFoundException(postalCode);
        }

        final var address = new Address();
        address.setPostalCode(postalCode);
        address.setStreet(addressResponse.getStreet());
        address.setNeighborhood(addressResponse.getNeighborhood());
        address.setCity(this.getCityOrRegisterIfNecessary(addressResponse));

        return address;
    }

    private City getCityOrRegisterIfNecessary(AddressResponse addressResponse) {
        City city = cityRegisterService.findByNameIgnoreCase(addressResponse.getCityName());
        if (city == null && addressResponse.existsCityAndStateInfo()) {
            city = new City();
            city.setName(addressResponse.getCityName());
            city.setState(this.getStateOrRegisterIfNecessary(addressResponse));
            city = cityRegisterService.validateAndSave(city);
        }
        return city;
    }

    private State getStateOrRegisterIfNecessary(AddressResponse addressResponse) {
        State state = stateRegisterService.findByAcronymIgnoreCase(addressResponse.getStateAcronym());
        if (state == null) {
            state = new State();
            state.setName(addressResponse.getStateName());
            state.setAcronym(addressResponse.getStateAcronym());
            state.setCountry(this.getCountryOrRegisterIfNecessary());
            state = stateRegisterService.saveAndValidate(state);
        }
        return state;
    }

    private Country getCountryOrRegisterIfNecessary() {
        Country country = countryRegisterService.findByAcronymIgnoreCase(BRAZIL_ACRONYM);
        if (country == null) {
            country = new Country();
            country.setAcronym(BRAZIL_ACRONYM);
            country.setName(BRAZIL_NAME);
            country = countryRegisterService.saveAndValidate(country);
        }
        return country;
    }

    private void validatePropertyUrlApiInitialization() {
        if (StringUtils.isBlank(this.urlApiSearchAddress)) {
            throw new IllegalArgumentException("Parameter urlApiSearchAddress is required");
        }
    }

}
