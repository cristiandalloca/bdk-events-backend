package br.com.bdk.eventsmanager.admin.state.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.BusinessException;

public class DuplicateAcronymStateException extends BusinessException {

    public DuplicateAcronymStateException(String acronym) {
        super("Já existe um estado com sigla '%s'".formatted(acronym));
    }

}
