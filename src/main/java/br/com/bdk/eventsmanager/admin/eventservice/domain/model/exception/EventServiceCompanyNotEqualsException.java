package br.com.bdk.eventsmanager.admin.eventservice.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.BusinessException;

public class EventServiceCompanyNotEqualsException extends BusinessException {

    public EventServiceCompanyNotEqualsException(String eventExternalId, String serviceExternalId) {
        super("Não é possível vincular o evento código '%s' com o serviço código '%s' pois não pertencem a mesma empresa".formatted(eventExternalId, serviceExternalId));
    }
}
