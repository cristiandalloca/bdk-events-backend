package br.com.bdk.eventsmanager.admin.educationalinstitution.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

@Getter
@AllArgsConstructor
public enum EducationalInstitutionType {
    HIGH_SCHOOL("Ensino médio"),
    HIGHER_EDUCATION("Ensino superior");

    private final String description;

    @Nullable
    @JsonCreator
    public static EducationalInstitutionType forValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        return EnumUtils.getEnumIgnoreCase(EducationalInstitutionType.class, value);
    }
}
