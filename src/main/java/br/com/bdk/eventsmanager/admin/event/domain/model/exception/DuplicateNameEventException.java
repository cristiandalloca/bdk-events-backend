package br.com.bdk.eventsmanager.admin.event.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.BusinessException;

public class DuplicateNameEventException extends BusinessException {

    public DuplicateNameEventException(String name) {
        super("Já existe um evento com nome '%s'".formatted(name));
    }
}
