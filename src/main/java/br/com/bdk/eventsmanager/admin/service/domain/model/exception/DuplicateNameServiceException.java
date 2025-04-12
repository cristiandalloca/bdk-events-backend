package br.com.bdk.eventsmanager.admin.service.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.BusinessException;

public class DuplicateNameServiceException extends BusinessException {

    public DuplicateNameServiceException(String name) {
        super("Já existe um serviço com nome '%s'".formatted(name));
    }
}