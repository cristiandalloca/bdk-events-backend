package br.com.bdk.eventsmanager.admin.educationalinstitution.domain.validator;

import br.com.bdk.eventsmanager.admin.educationalinstitution.domain.model.EducationalInstitution;
import br.com.bdk.eventsmanager.admin.educationalinstitution.domain.model.exception.DuplicateNameEducationalInstitutionException;
import br.com.bdk.eventsmanager.admin.educationalinstitution.domain.repository.EducationalInstitutionRepository;
import br.com.bdk.eventsmanager.core.exception.BusinessException;
import br.com.bdk.eventsmanager.core.validator.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class EducationalInstitutionRegistrationValidator implements RegistrationValidator<EducationalInstitution> {

    private final EducationalInstitutionRepository repository;

    @Override
    @Transactional(readOnly = true)
    public void validate(@NonNull EducationalInstitution educationalInstitution) throws BusinessException {
        var existingEducationalInstitution = repository.findByNameIgnoreCase(educationalInstitution.getName());
        if (existingEducationalInstitution.isPresent() && this.isNotSame(educationalInstitution, existingEducationalInstitution.get())) {
            throw new DuplicateNameEducationalInstitutionException(educationalInstitution.getName());
        }

    }
}
