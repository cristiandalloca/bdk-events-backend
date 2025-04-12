package br.com.bdk.eventsmanager.admin.eventservice.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Getter
@AllArgsConstructor
public enum EventServiceMeasurementType {

    UNIQUE("Único"),
    NUMBER_OF_PEOPLE("Por número de pessoas"),
    NUMBER_OF_INVITATIONS("Por número de convites");

    private final String description;

    public boolean isNumberOfPeopleOrInvitations() {
        return List.of(NUMBER_OF_PEOPLE, NUMBER_OF_INVITATIONS).contains(this);
    }

    @JsonCreator
    public static EventServiceMeasurementType forValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        return EnumUtils.getEnumIgnoreCase(EventServiceMeasurementType.class, value);
    }
}
