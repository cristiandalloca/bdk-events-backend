package br.com.bdk.eventsmanager.admin.company.parameter.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.ResourceNotFoundException;

public class CompanySignatureNotFoundException extends ResourceNotFoundException {

    public CompanySignatureNotFoundException(String companyExternalId) {
        super("Não foi possível encontrar a assinatura da empresa de código '%s'".formatted(companyExternalId));
    }
}
