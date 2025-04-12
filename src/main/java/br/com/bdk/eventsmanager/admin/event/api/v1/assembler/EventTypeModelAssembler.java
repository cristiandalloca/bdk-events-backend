package br.com.bdk.eventsmanager.admin.event.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.event.api.v1.model.EventTypeModel;
import br.com.bdk.eventsmanager.admin.event.domain.model.EventType;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class EventTypeModelAssembler implements ModelAssembler<EventType, EventTypeModel> {

    @NonNull
    @Override
    public EventTypeModel toModel(@NonNull EventType type) {
        return EventTypeModel.builder()
                .id(type.name())
                .description(type.getDescription())
                .build();
    }

}
