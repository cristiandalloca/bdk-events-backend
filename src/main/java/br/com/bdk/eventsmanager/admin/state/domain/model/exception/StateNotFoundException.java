package br.com.bdk.eventsmanager.admin.state.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.ResourceNotFoundException;

public class StateNotFoundException extends ResourceNotFoundException {

    public StateNotFoundException(String id) {
        super("Não foi encontrado um estado com código '%s'".formatted(id));
    }
}
