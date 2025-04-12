package br.com.bdk.eventsmanager.admin.event.api.v1.disassembler;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.admin.event.api.v1.model.input.EventInput;
import br.com.bdk.eventsmanager.admin.event.domain.model.Event;
import br.com.bdk.eventsmanager.admin.event.domain.model.EventType;
import br.com.bdk.eventsmanager.core.api.disassembler.InputDisassembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class EventInputDisassembler implements InputDisassembler<EventInput, Event> {

    @Override
    public Event toEntity(@NonNull EventInput input) {
        var event = new Event();
        this.copyToEntity(input, event);
        return event;
    }

    @Override
    public void copyToEntity(@NonNull EventInput input, @NonNull Event event) {
        event.setName(input.getName());
        event.setActive(input.getActive());
        event.setCompany(new Company(input.getCompanyId()));

        event.removeAllTypes();
        if (input.getTypes() != null) {
            for (EventType type : input.getTypes()) {
                event.addType(type);
            }
        }
    }
}
