package br.com.bdk.eventsmanager.admin.eventservice.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.event.domain.model.Event;
import br.com.bdk.eventsmanager.admin.eventservice.api.v1.model.EventServiceEventModel;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventServiceEventModelAssembler implements ModelAssembler<Event, EventServiceEventModel> {

    @NonNull
    @Override
    public EventServiceEventModel toModel(@NonNull Event event) {
        return EventServiceEventModel.builder()
                .id(event.getExternalId())
                .name(event.getName())
                .build();
    }
}
