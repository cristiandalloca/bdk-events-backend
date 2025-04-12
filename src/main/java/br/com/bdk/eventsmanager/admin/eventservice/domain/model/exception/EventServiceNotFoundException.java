package br.com.bdk.eventsmanager.admin.eventservice.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.ResourceNotFoundException;

public class EventServiceNotFoundException extends ResourceNotFoundException {

    public EventServiceNotFoundException(String externalId) {
        super("Não foi encontrado um serviço vinculado ao evento de código '%s'".formatted(externalId));
    }

}
