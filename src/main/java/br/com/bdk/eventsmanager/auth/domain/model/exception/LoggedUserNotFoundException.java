package br.com.bdk.eventsmanager.auth.domain.model.exception;

import org.springframework.security.core.AuthenticationException;

public class LoggedUserNotFoundException extends AuthenticationException {
    public LoggedUserNotFoundException() {
        super("Não foi possível encontrar um usuário logado");
    }
}
