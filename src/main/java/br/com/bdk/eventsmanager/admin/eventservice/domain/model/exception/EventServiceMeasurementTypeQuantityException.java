package br.com.bdk.eventsmanager.admin.eventservice.domain.model.exception;

import br.com.bdk.eventsmanager.admin.eventservice.domain.model.EventServiceMeasurementType;
import br.com.bdk.eventsmanager.core.exception.BusinessException;

public class EventServiceMeasurementTypeQuantityException extends BusinessException {

    public EventServiceMeasurementTypeQuantityException(EventServiceMeasurementType measurementType) {
        super("“Foi informado o tipo de unidade de medida ‘%s’, para a qual a quantidade não pode ser maior que zero e/ou não é permitido alterá-la na proposta".formatted(measurementType.getDescription()));
    }
}
