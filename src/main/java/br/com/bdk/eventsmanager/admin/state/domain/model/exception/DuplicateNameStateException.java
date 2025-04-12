package br.com.bdk.eventsmanager.admin.state.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.BusinessException;

public class DuplicateNameStateException extends BusinessException {

    public DuplicateNameStateException(String name) {
        super("Já existe um estado com nome '%s'".formatted(name));
    }

}
