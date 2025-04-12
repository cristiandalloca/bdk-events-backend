package br.com.bdk.eventsmanager.admin.company.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.ResourceNotFoundException;

public class CompanyNotFoundException extends ResourceNotFoundException {

    public CompanyNotFoundException(String externalId) {
        super("Não foi encontrada uma empresa com código '%s'".formatted(externalId));
    }
}
