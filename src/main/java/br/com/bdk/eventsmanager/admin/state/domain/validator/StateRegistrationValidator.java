package br.com.bdk.eventsmanager.admin.state.domain.validator;

import br.com.bdk.eventsmanager.admin.country.domain.model.exception.CountryNotFoundException;
import br.com.bdk.eventsmanager.admin.country.domain.repository.CountryRepository;
import br.com.bdk.eventsmanager.admin.state.domain.model.State;
import br.com.bdk.eventsmanager.admin.state.domain.model.exception.DuplicateAcronymStateException;
import br.com.bdk.eventsmanager.admin.state.domain.model.exception.DuplicateNameStateException;
import br.com.bdk.eventsmanager.admin.state.domain.repository.StateRepository;
import br.com.bdk.eventsmanager.core.validator.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class StateRegistrationValidator implements RegistrationValidator<State> {

    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;

    @Transactional(readOnly = true)
    public void validate(@NonNull State state) {
        var existingStateByName = stateRepository.findByNameIgnoreCase(state.getName());
        if (existingStateByName.isPresent() && this.isNotSame(state, existingStateByName.get())) {
            throw new DuplicateNameStateException(state.getName());
        }

        var existingStateByAcronym = stateRepository.findByAcronymIgnoreCase(state.getAcronym());
        if (existingStateByAcronym.isPresent() && this.isNotSame(state, existingStateByAcronym.get())) {
            throw new DuplicateAcronymStateException(state.getAcronym());
        }

        if (!countryRepository.existsByExternalId(state.getCountry().getExternalId())) {
            throw new CountryNotFoundException(state.getCountry().getExternalId());
        }
    }

}
