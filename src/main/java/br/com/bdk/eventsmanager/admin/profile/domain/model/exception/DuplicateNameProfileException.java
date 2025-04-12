package br.com.bdk.eventsmanager.admin.profile.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.BusinessException;

public class DuplicateNameProfileException extends BusinessException {

    public DuplicateNameProfileException(String name) {
        super("Já existe perfil com nome '%s'".formatted(name));
    }

}
