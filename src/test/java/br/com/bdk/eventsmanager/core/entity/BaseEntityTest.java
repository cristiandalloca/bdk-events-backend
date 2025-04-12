package br.com.bdk.eventsmanager.core.entity;

import br.com.bdk.eventsmanager.admin.country.domain.model.Country;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BaseEntityTest {

    @Test
    void shouldReturnTrueIsNewWhenIdIsNull() {
        var country = new Country();
        assertTrue(country.isNew());
    }

    @Test
    void shouldReturnTrueIsNewWhenIdIsZero() {
        var country = new Country();
        ReflectionTestUtils.setField(country, "id", 0L);
        assertTrue(country.isNew());
    }

    @Test
    void shouldReturnFalseIsNewWhenIdIsNotZero() {
        var country = new Country();
        ReflectionTestUtils.setField(country, "id", 1L);
        assertFalse(country.isNew());
    }
}