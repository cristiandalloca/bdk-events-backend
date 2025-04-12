package br.com.bdk.eventsmanager.admin.eventservice.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.eventservice.api.v1.model.EventServiceMeasurementTypeModel;
import br.com.bdk.eventsmanager.admin.eventservice.domain.model.EventServiceMeasurementType;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class EventServiceMeasurementTypeModelAssembler implements ModelAssembler<EventServiceMeasurementType, EventServiceMeasurementTypeModel> {

    @NonNull
    @Override
    public EventServiceMeasurementTypeModel toModel(@NonNull EventServiceMeasurementType eventServiceMeasurementType) {
        return EventServiceMeasurementTypeModel.builder()
                .id(eventServiceMeasurementType.name())
                .description(eventServiceMeasurementType.getDescription())
                .build();
    }
}
