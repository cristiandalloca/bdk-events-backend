package br.com.bdk.eventsmanager.admin.country.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.ResourceNotFoundException;

public class CountryNotFoundException extends ResourceNotFoundException {

    public CountryNotFoundException(String id) {
        super("Não foi encontrado um país com código '%s'".formatted(id));
    }
}
