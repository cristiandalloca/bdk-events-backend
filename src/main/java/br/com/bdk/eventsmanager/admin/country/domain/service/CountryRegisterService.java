package br.com.bdk.eventsmanager.admin.country.domain.service;

import br.com.bdk.eventsmanager.admin.country.domain.model.Country;
import br.com.bdk.eventsmanager.admin.country.domain.model.exception.CountryNotFoundException;
import br.com.bdk.eventsmanager.admin.country.domain.repository.CountryRepository;
import br.com.bdk.eventsmanager.admin.country.domain.validator.CountryRegistrationValidator;
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
public class CountryRegisterService {

    private final CountryRepository countryRepository;
    private final CountryRegistrationValidator countryRegisterValidator;
    private static final String EXCEPTION_MESSAGE_COUNTRY_IN_USE
            = "País de código '%s' não pode ser removido, pois está em uso";

    @Transactional
    public Country saveAndValidate(@NonNull @Valid Country country) {
        countryRegisterValidator.validate(country);
        return countryRepository.save(country);
    }

    @Transactional(readOnly = true)
    public Page<Country> findAll(@Nullable String name, @NonNull Pageable pageable) {
        return countryRepository.findAllByName(trimToNull(name), pageable);
    }

    @Transactional(readOnly = true)
    public Country findByExternalId(@NonNull String externalId) {
        return findByExternalIdOrFail(externalId);
    }

    @Transactional(readOnly = true)
    public Country findByAcronymIgnoreCase(@NonNull String acronym) {
        return countryRepository.findByAcronymIgnoreCase(acronym).orElse(null);
    }

    private Country findByExternalIdOrFail(String externalId) {
        return countryRepository.findByExternalId(externalId)
                .orElseThrow(() -> new CountryNotFoundException(externalId));
    }

    @Transactional
    public void removeByExternalId(@NonNull String externalId) {
        try {
            countryRepository.delete(this.findByExternalIdOrFail(externalId));
            countryRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new ResourceInUseException(EXCEPTION_MESSAGE_COUNTRY_IN_USE.formatted(externalId));
        }
    }
}
