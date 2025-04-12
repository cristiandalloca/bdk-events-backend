package br.com.bdk.eventsmanager.admin.city.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.BusinessException;

public class DuplicateNameCityException extends BusinessException {

    public DuplicateNameCityException(String name) {
        super("Já existe uma cidade com nome '%s'".formatted(name));
    }

}
