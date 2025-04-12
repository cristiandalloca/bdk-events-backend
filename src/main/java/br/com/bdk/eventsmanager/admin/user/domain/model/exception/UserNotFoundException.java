package br.com.bdk.eventsmanager.admin.user.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException(String id) {
        super("Não foi encontrado um usuário com código '%s'".formatted(id));
    }
}
