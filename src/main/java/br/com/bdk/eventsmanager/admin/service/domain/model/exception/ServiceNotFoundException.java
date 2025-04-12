package br.com.bdk.eventsmanager.admin.service.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.ResourceNotFoundException;

public class ServiceNotFoundException extends ResourceNotFoundException {

    public ServiceNotFoundException(String externalId) {
        super("Não foi encontrado um serviço com código '%s'".formatted(externalId));
    }
}