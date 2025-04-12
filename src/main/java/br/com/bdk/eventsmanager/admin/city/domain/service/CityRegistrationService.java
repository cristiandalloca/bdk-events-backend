package br.com.bdk.eventsmanager.admin.city.domain.service;

import br.com.bdk.eventsmanager.admin.city.domain.model.City;
import br.com.bdk.eventsmanager.admin.city.domain.model.exception.CityNotFoundException;
import br.com.bdk.eventsmanager.admin.city.domain.repository.CityRepository;
import br.com.bdk.eventsmanager.admin.city.domain.validator.CityRegistrationValidator;
import br.com.bdk.eventsmanager.admin.state.domain.service.StateRegisterService;
import br.com.bdk.eventsmanager.core.exception.ResourceInUseException;
import br.com.bdk.eventsmanager.core.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.commons.lang3.StringUtils.trimToNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class CityRegistrationService implements RegistrationService<City> {

    private final CityRepository cityRepository;
    private final CityRegistrationValidator cityRegisterValidator;
    private final StateRegisterService stateRegisterService;
    private static final String CITY_IN_USE_MESSAGE_EXCEPTION
            = "Cidade de código '%s' não pode ser removida, pois está em uso";

    @NonNull
    @Transactional(readOnly = true)
    public City findByExternalId(@NonNull String externalId) { // TODO: Utilizar anotações id externo
        return findByExternalIdOrFail(externalId);
    }

    private City findByExternalIdOrFail(String externalId) {
        return cityRepository.findByExternalId(externalId)
                .orElseThrow(() -> new CityNotFoundException(externalId));
    }

    @Transactional(readOnly = true)
    public City findByNameIgnoreCase(@NonNull String name) { // TODO: Utilizar anotações id externo
        return cityRepository.findByNameIgnoreCase(name).orElse(null);
    }

    @NonNull
    @Transactional
    public City validateAndSave(@NonNull @Valid City city) { // TODO: Utilizar anotações id externo
        cityRegisterValidator.validate(city);
        city.setState(stateRegisterService.findByExternalId(city.getState().getExternalId()));
        return cityRepository.save(city);
    }

    @Transactional
    public void removeByExternalId(@NonNull String externalId) { // TODO: Utilizar anotações id externo
        try {
            cityRepository.delete(this.findByExternalIdOrFail(externalId));
            cityRepository.flush();
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage(), e);
            throw new ResourceInUseException(CITY_IN_USE_MESSAGE_EXCEPTION.formatted(externalId));
        }
    }

    @NonNull
    @Transactional(readOnly = true)
    public Page<City> findAll(@Nullable String externalStateId, @Nullable String name, @NonNull Pageable pageable) { // TODO: Utilizar anotações id externo
        return cityRepository.findAllByStateExternalIdAndName(trimToNull(externalStateId), trimToNull(name), pageable);
    }
}
