package br.com.bdk.eventsmanager.admin.event.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class EventTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"graduation", "GRADUATION", "gRaDuAtIoN", "wedding", "WeDdInG", "WEDDING"})
    void shouldReturnEnumByValue(String value) {
        assertThat(EventType.forValue(value)).isNotNull();
    }

    @Test
    void shouldReturnNullWhenValueIsNull() {
        assertThat(EventType.forValue(null)).isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void shouldReturnNullWhenValueIsBlank(String value) {
        assertThat(EventType.forValue(value)).isNull();
    }
}