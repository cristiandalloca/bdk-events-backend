package br.com.bdk.eventsmanager.admin.educationalinstitution.api.v1.model.input;

import br.com.bdk.eventsmanager.admin.educationalinstitution.domain.model.EducationalInstitutionType;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class EducationalInstitutionInput {

    @NotBlank
    @Size(max = 255)
    @Schema(example = Example.EDUCATIONAL_INSTITUTION_NAME, description = Description.EDUCATIONAL_INSTITUTION_NAME)
    private String name;

    @NotNull
    @Schema(example = Example.EDUCATIONAL_INSTITUTION_TYPE, description = Description.EDUCATIONAL_INSTITUTION_TYPE)
    private EducationalInstitutionType type;

    @NotBlank
    @Size(max = 36)
    @Schema(example = Example.IDENTIFIER, description = Description.CITY_IDENTIFIER)
    private String cityId;

    @NotNull
    @Schema(example = Example.BOOLEAN, description = Description.EDUCATIONAL_INSTITUTION_ACTIVE)
    private Boolean active;

}
