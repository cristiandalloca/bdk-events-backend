package br.com.bdk.eventsmanager.admin.address.api.v1;

import br.com.bdk.eventsmanager.admin.address.domain.model.Address;
import br.com.bdk.eventsmanager.admin.city.api.v1.assembler.CityStateModelAssembler;
import br.com.bdk.eventsmanager.admin.city.domain.model.City;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = ValidationAutoConfiguration.class)
@SpringBootTest(classes = AddressModelAssembler.class)
class AddressModelAssemblerTest {

    @Autowired
    private AddressModelAssembler assembler;

    @MockitoBean
    private CityStateModelAssembler cityStateModelAssembler;

    private Address address;

    @BeforeEach
    void setUp() {
        address = new Address();
        address.setNeighborhood("Ingleses do Rio Vermelho");
        address.setPostalCode("88058320");
        address.setStreet("Travessa Nildo Neponoceno Fernandes");
        address.setStreetNumber("397");
        address.setComplement("Apto 204. Bloco B");
        address.setCity(new City());
    }

    @Test
    void shouldAssemble() {
        var model = assembler.toModel(address);
        assertEquals(address.getStreet(), model.getStreet());
        assertEquals(address.getNeighborhood(), model.getNeighborhood());
        verify(cityStateModelAssembler, times(1)).toModel(address.getCity());
    }

    @Test
    void shouldThrowExceptionWhenSourceIsNull() {
        assertThatThrownBy(() -> assembler.toModel(null))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessage("toModel.source: não deve ser nulo");

    }
}