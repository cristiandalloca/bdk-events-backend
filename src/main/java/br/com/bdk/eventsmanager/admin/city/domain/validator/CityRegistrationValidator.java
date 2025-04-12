package br.com.bdk.eventsmanager.admin.city.domain.validator;

import br.com.bdk.eventsmanager.admin.city.domain.model.City;
import br.com.bdk.eventsmanager.admin.city.domain.model.exception.DuplicateNameCityException;
import br.com.bdk.eventsmanager.admin.city.domain.repository.CityRepository;
import br.com.bdk.eventsmanager.admin.state.domain.model.exception.StateNotFoundException;
import br.com.bdk.eventsmanager.admin.state.domain.repository.StateRepository;
import br.com.bdk.eventsmanager.core.validator.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CityRegistrationValidator implements RegistrationValidator<City> {

    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    @Transactional(readOnly = true)
    public void validate(@NonNull City city) {
        var existingCityByName = cityRepository.findByNameIgnoreCase(city.getName());
        if (existingCityByName.isPresent() && this.isNotSame(city, existingCityByName.get())) {
            throw new DuplicateNameCityException(city.getName());
        }

        if (!stateRepository.existsByExternalId(city.getState().getExternalId())) {
            throw new StateNotFoundException(city.getState().getExternalId());
        }
    }

}
