package br.com.bdk.eventsmanager.admin.eventservice.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.BusinessException;

public class DuplicateEventServiceException extends BusinessException {

    public DuplicateEventServiceException(String eventExternalId, String serviceExternalId) {
        super("Já existe um serviço de código '%s' vinculado ao evento de código '%s'".formatted(serviceExternalId, eventExternalId));
    }
}
