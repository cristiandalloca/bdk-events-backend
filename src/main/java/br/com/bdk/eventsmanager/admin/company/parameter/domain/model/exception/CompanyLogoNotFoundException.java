package br.com.bdk.eventsmanager.admin.company.parameter.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.ResourceNotFoundException;

public class CompanyLogoNotFoundException extends ResourceNotFoundException {

    public CompanyLogoNotFoundException(String companyExternalId) {
        super("Não foi possível encontrar a logotipo da empresa de código '%s'".formatted(companyExternalId));
    }
}
