package br.com.bdk.eventsmanager.admin.user.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.BusinessException;

public class DuplicateEmailUserException extends BusinessException {

    public DuplicateEmailUserException(String email) {
        super("Já existe um usuário com email '%s'".formatted(email));
    }

}
