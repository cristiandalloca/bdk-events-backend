package br.com.bdk.eventsmanager.admin.city.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.ResourceNotFoundException;

public class CityNotFoundException extends ResourceNotFoundException {

    public CityNotFoundException(String id) {
        super("Não foi encontrada uma cidade com código '%s'".formatted(id));
    }
}
