package br.com.bdk.eventsmanager.admin.user.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.BusinessException;

public class UserPasswordDoesNotMatchException extends BusinessException {

    public UserPasswordDoesNotMatchException() {
        super("Senha informada não coincide com a senha do usuário");
    }
}
