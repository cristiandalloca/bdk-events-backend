package br.com.bdk.eventsmanager.admin.educationalinstitution.domain.service;

import br.com.bdk.eventsmanager.admin.city.domain.service.CityRegistrationService;
import br.com.bdk.eventsmanager.admin.educationalinstitution.domain.model.EducationalInstitution;
import br.com.bdk.eventsmanager.admin.educationalinstitution.domain.model.EducationalInstitutionType;
import br.com.bdk.eventsmanager.admin.educationalinstitution.domain.model.exception.EducationalInstitutionNotFoundException;
import br.com.bdk.eventsmanager.admin.educationalinstitution.domain.repository.EducationalInstitutionRepository;
import br.com.bdk.eventsmanager.admin.educationalinstitution.domain.validator.EducationalInstitutionRegistrationValidator;
import br.com.bdk.eventsmanager.core.exception.ResourceInUseException;
import br.com.bdk.eventsmanager.core.service.RegistrationService;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class EducationalInstitutionRegistrationService implements RegistrationService<EducationalInstitution> {

    private final EducationalInstitutionRegistrationValidator validator;
    private final CityRegistrationService cityRegisterService;
    private final EducationalInstitutionRepository repository;
    private static final String EXCEPTION_MESSAGE_EDUCATIONAL_INSTITUTION_IN_USE
            = "Instituição de ensino de código %s não pode ser removida, pois está em uso";

    @NonNull
    @Override
    @Transactional
    public EducationalInstitution validateAndSave(@NonNull EducationalInstitution educationalInstitution) {
        validator.validate(educationalInstitution);
        educationalInstitution.setCity(cityRegisterService.findByExternalId(educationalInstitution.getCity().getExternalId()));
        return repository.save(educationalInstitution);
    }

    @NonNull
    @Override
    @Transactional(readOnly = true)
    public EducationalInstitution findByExternalId(@NonNull String externalId) {
        return this.findByExternalIdOrFail(externalId);
    }

    @Override
    public void removeByExternalId(@NonNull String externalId) {
        try {
            repository.delete(this.findByExternalIdOrFail(externalId));
            repository.flush();
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage(), e);
            throw new ResourceInUseException(EXCEPTION_MESSAGE_EDUCATIONAL_INSTITUTION_IN_USE.formatted(externalId));
        }
    }

    private EducationalInstitution findByExternalIdOrFail(@NonNull String externalId) {
        return repository.findByExternalId(externalId)
                .orElseThrow(() -> new EducationalInstitutionNotFoundException(externalId));
    }

    @NonNull
    @Transactional(readOnly = true)
    public Page<EducationalInstitution> findAll(@Nullable String name, @Nullable String cityExternalId,
                                                @Nullable EducationalInstitutionType type, @Nullable Boolean active,
                                                @NonNull Pageable pageable) {
        return repository.findAllByFilter(trimToNull(name), trimToNull(cityExternalId), type, active, pageable);
    }
}
