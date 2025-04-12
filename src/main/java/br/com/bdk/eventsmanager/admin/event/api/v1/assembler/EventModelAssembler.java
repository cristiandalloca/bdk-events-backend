package br.com.bdk.eventsmanager.admin.event.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.event.api.v1.model.EventModel;
import br.com.bdk.eventsmanager.admin.event.domain.model.Event;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventModelAssembler implements ModelAssembler<Event, EventModel> {

    private final EventCompanyModelAssembler eventCompanyModelAssembler;
    private final EventTypeModelAssembler eventTypeModelAssembler;

    @NonNull
    @Override
    public EventModel toModel(@NonNull Event event) {
        return EventModel.builder()
                .id(event.getExternalId())
                .name(event.getName())
                .active(event.isActive())
                .types(eventTypeModelAssembler.toCollectionModel(event.getTypes()))
                .company(eventCompanyModelAssembler.toModel(event.getCompany()))
                .build();
    }
}
