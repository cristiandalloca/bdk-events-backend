package br.com.bdk.eventsmanager.admin.country.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.BusinessException;

public class DuplicateNameCountryException extends BusinessException {

    public DuplicateNameCountryException(String name) {
        super("Já existe um país com nome '%s'".formatted(name));
    }

}
