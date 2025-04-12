package br.com.bdk.eventsmanager.admin.company.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.ResourceInUseException;

public class CompanyInUseException extends ResourceInUseException {

    public CompanyInUseException(String companyExternalId) {
        super("Empresa de código '%s' não pode ser removida, pois está em uso".formatted(companyExternalId));
    }
}
