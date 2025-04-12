package br.com.bdk.eventsmanager.admin.user.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.BusinessException;

public class DuplicateLoginUserException extends BusinessException {

    public DuplicateLoginUserException(String login) {
        super("Já existe um usuário com login '%s'".formatted(login));
    }

}
