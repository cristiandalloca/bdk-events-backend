package br.com.bdk.eventsmanager.admin.invitationtype.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.ResourceNotFoundException;

public class InvitationTypeNotFoundException extends ResourceNotFoundException {

    public InvitationTypeNotFoundException(String externalId) {
        super("Não foi encontrado um tipo de convite com código '%s".formatted(externalId));
    }
}
