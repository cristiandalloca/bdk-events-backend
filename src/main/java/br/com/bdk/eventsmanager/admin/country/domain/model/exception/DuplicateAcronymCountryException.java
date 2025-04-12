package br.com.bdk.eventsmanager.admin.country.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.BusinessException;

public class DuplicateAcronymCountryException extends BusinessException {

    public DuplicateAcronymCountryException(String acronym) {
        super("Já existe um país a sigla '%s'".formatted(acronym));
    }

}
