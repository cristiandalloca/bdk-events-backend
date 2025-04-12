package br.com.bdk.eventsmanager.admin.company.invitationtype.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.ResourceNotFoundException;

public class CompanyInvitationTypeNotFoundException extends ResourceNotFoundException {

    public CompanyInvitationTypeNotFoundException(String externalId) {
        super("Não foi encontrado um tipo de convite vinculado a empresa de código '%s'".formatted(externalId));
    }

    public CompanyInvitationTypeNotFoundException(String companyExternalId, String invitationTypeExternalId) {
        super("Não foi encontrado um tipo de convite de código '%s' relacionado a empresa de código '%s'".formatted(invitationTypeExternalId, companyExternalId));
    }
}
