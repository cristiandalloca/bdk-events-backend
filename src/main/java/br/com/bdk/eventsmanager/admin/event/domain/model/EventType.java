package br.com.bdk.eventsmanager.admin.event.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
public enum EventType {
    GRADUATION("Formatura"),
    WEDDING("Casamento");

    private final String description;

    @JsonCreator
    public static EventType forValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        return EnumUtils.getEnumIgnoreCase(EventType.class, value);
    }
}
