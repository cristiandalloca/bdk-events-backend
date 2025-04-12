package br.com.bdk.eventsmanager.admin.event.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.ResourceNotFoundException;

public class EventNotFoundException extends ResourceNotFoundException {

    public EventNotFoundException(String externalId) {
        super("Não foi encontrado um evento com código '%s'".formatted(externalId));
    }

}
