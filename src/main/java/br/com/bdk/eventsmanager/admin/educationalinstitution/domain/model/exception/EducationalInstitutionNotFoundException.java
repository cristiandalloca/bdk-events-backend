package br.com.bdk.eventsmanager.admin.educationalinstitution.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.ResourceNotFoundException;

public class EducationalInstitutionNotFoundException extends ResourceNotFoundException {

    public EducationalInstitutionNotFoundException(String externalId) {
        super("Não foi encontrado uma instituição de ensino com código '%s'".formatted(externalId));
    }

}
