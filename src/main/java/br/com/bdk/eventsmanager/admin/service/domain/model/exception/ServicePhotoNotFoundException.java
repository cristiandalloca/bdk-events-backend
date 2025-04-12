package br.com.bdk.eventsmanager.admin.service.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.ResourceNotFoundException;

public class ServicePhotoNotFoundException extends ResourceNotFoundException {

    public ServicePhotoNotFoundException(String externalId) {
        super("Não foi encontrado uma foto do serviço de código '%s'".formatted(externalId));
    }
}