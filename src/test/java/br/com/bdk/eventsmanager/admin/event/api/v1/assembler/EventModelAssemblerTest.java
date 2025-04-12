package br.com.bdk.eventsmanager.admin.event.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.admin.event.domain.model.Event;
import br.com.bdk.eventsmanager.admin.event.domain.model.EventType;
import br.com.bdk.eventsmanager.admin.event.domain.model.Event_;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class EventModelAssemblerTest {

    @InjectMocks
    private EventModelAssembler eventModelAssembler;

    @Mock
    private EventCompanyModelAssembler eventCompanyModelAssembler;

    @Mock
    private EventTypeModelAssembler eventTypeModelAssembler;

    @Test
    void shouldAssembleModel() {
        var event = new Event();
        ReflectionTestUtils.setField(event, Event_.EXTERNAL_ID, "123-abc-2afg");
        event.setName("Jantar");
        event.setActive(Boolean.TRUE);
        event.addType(EventType.WEDDING);

        var company = new Company("1234-a");
        event.setCompany(company);

        var model = eventModelAssembler.toModel(event);
        assertEquals(event.getName(), model.getName());
        assertEquals(model.getId(), event.getExternalId());
        assertEquals(model.getActive(), event.isActive());
    }
}