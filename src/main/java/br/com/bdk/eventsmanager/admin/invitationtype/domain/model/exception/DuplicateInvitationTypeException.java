package br.com.bdk.eventsmanager.admin.invitationtype.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.BusinessException;

public class DuplicateInvitationTypeException extends BusinessException {

    public DuplicateInvitationTypeException(String name) {
        super("Já existe tipo de convite com nome '%s'".formatted(name));
    }
}
