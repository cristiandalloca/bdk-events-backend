package br.com.bdk.eventsmanager.admin.state.domain.service;

import br.com.bdk.eventsmanager.admin.country.domain.service.CountryRegisterService;
import br.com.bdk.eventsmanager.admin.state.domain.model.State;
import br.com.bdk.eventsmanager.admin.state.domain.model.exception.StateNotFoundException;
import br.com.bdk.eventsmanager.admin.state.domain.repository.StateRepository;
import br.com.bdk.eventsmanager.admin.state.domain.validator.StateRegistrationValidator;
import br.com.bdk.eventsmanager.core.exception.ResourceInUseException;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.commons.lang3.StringUtils.trimToNull;

@Service
@RequiredArgsConstructor
public class StateRegisterService {

    private final StateRepository stateRepository;
    private final StateRegistrationValidator stateRegisterValidator;
    private final CountryRegisterService countryRegisterService;
    private static final String STATE_IN_USE_MESSAGE_EXCEPTION
            = "Estado de código '%s' não pode ser removido, pois está em uso";

    @Transactional(readOnly = true)
    public State findByExternalId(@NonNull String externalId) {
        return findByExternalIdOrFail(externalId);
    }

    private State findByExternalIdOrFail(String externalId) {
        return stateRepository.findByExternalId(externalId)
                .orElseThrow(() -> new StateNotFoundException(externalId));
    }

    @Transactional
    public State saveAndValidate(@NonNull @Valid State state) {
        stateRegisterValidator.validate(state);
        state.setCountry(countryRegisterService.findByExternalId(state.getCountry().getExternalId()));
        return stateRepository.save(state);
    }

    @Transactional(readOnly = true)
    public Page<State> findAll(@Nullable String name, @NonNull Pageable pageable) {
        return findAllOrEmpty(null, trimToNull(name), pageable);
    }

    @Transactional(readOnly = true)
    public Page<State> findAll(@Nullable String externalCountryId, @Nullable String name, @NonNull Pageable pageable) {
        return findAllOrEmpty(externalCountryId, name, pageable);
    }

    private Page<State> findAllOrEmpty(String externalCountryId, String name, Pageable pageable) {
        return stateRepository.findAllByCountryExternalIdAndName(trimToNull(externalCountryId), trimToNull(name), pageable);
    }

    @Transactional
    public void removeByExternalId(@NonNull String externalId) {
        try {
            stateRepository.delete(this.findByExternalIdOrFail(externalId));
            stateRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new ResourceInUseException(STATE_IN_USE_MESSAGE_EXCEPTION.formatted(externalId));
        }
    }

    @Transactional(readOnly = true)
    public State findByAcronymIgnoreCase(@NonNull String stateAcronym) {
        return stateRepository.findByAcronymIgnoreCase(stateAcronym).orElse(null);
    }
}
