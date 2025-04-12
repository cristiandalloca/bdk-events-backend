package br.com.bdk.eventsmanager.admin.event.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class EventCompanyModelAssemblerTest {

    @InjectMocks
    private EventCompanyModelAssembler eventCompanyModelAssembler;

    @Test
    void shouldAssembleModel() {
        var company = new Company();
        String externalId = "123L-ABC";
        ReflectionTestUtils.setField(company, "externalId", externalId);
        company.setName("BDK");
        company.setBusinessName("BDK Gestão Eventos");

        var model = eventCompanyModelAssembler.toModel(company);
        assertEquals(company.getExternalId(), model.getId());
        assertEquals(company.getName(), model.getName());
        assertEquals(company.getBusinessName(), model.getBusinessName());
    }
}