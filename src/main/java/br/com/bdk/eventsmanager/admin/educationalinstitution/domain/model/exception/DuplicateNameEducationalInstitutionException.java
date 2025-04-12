package br.com.bdk.eventsmanager.admin.educationalinstitution.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.BusinessException;

public class DuplicateNameEducationalInstitutionException extends BusinessException {

    public DuplicateNameEducationalInstitutionException(String name) {
        super("Já existe uma instituição de ensino com nome '%s'".formatted(name));
    }
}
