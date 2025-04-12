package br.com.bdk.eventsmanager.core.entity;

import br.com.bdk.eventsmanager.admin.country.domain.model.Country;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class ExposedEntityTest {

    @Test
    void shouldGenerateExternalId() {
        var entity = new Country();
        ReflectionTestUtils.invokeMethod(entity, "prePersist");
        assertThat(entity.getExternalId()).isNotBlank();
    }
}