package br.com.bdk.eventsmanager.admin.company.parameter.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.ResourceNotFoundException;

public class CompanyParameterNotFoundException extends ResourceNotFoundException {

    public CompanyParameterNotFoundException(String companyExternalId) {
        super("Não foi possível encontrar parametros para a empresa de código '%s'".formatted(companyExternalId));
    }
}
