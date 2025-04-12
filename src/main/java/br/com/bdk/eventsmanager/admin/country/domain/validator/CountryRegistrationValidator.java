package br.com.bdk.eventsmanager.admin.country.domain.validator;

import br.com.bdk.eventsmanager.admin.country.domain.model.Country;
import br.com.bdk.eventsmanager.admin.country.domain.model.exception.DuplicateAcronymCountryException;
import br.com.bdk.eventsmanager.admin.country.domain.model.exception.DuplicateNameCountryException;
import br.com.bdk.eventsmanager.admin.country.domain.repository.CountryRepository;
import br.com.bdk.eventsmanager.core.validator.RegistrationValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CountryRegistrationValidator implements RegistrationValidator<Country> {

    private final CountryRepository countryRepository;

    @Transactional(readOnly = true)
    public void validate(@NonNull Country country) {
        var existingCountryByName = countryRepository.findByNameIgnoreCase(country.getName());
        if (existingCountryByName.isPresent() && this.isNotSame(country, existingCountryByName.get())) {
            throw new DuplicateNameCountryException(country.getName());
        }

        var existingCountryByAcronym = countryRepository.findByAcronymIgnoreCase(country.getAcronym());
        if (existingCountryByAcronym.isPresent() && this.isNotSame(country, existingCountryByAcronym.get())) {
            throw new DuplicateAcronymCountryException(country.getAcronym());
        }
    }
}
