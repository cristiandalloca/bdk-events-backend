package br.com.bdk.eventsmanager.admin.service.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

public enum ServiceCostType {
    FIXED,
    VARIABLE;

    @JsonCreator
    public static ServiceCostType forValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        return EnumUtils.getEnumIgnoreCase(ServiceCostType.class, value);
    }
}
