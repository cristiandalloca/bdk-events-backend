package br.com.bdk.eventsmanager.admin.educationalinstitution.api.v1.model;

import br.com.bdk.eventsmanager.admin.city.api.v1.model.CityStateModel;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EducationalInstitutionModel {

    @Schema(example = Example.IDENTIFIER, description = Description.EDUCATIONAL_INSTITUTION_IDENTIFIER)
    private String id;

    @Schema(example = Example.EDUCATIONAL_INSTITUTION_NAME, description = Description.EDUCATIONAL_INSTITUTION_NAME)
    private String name;

    private EducationalInstitutionTypeModel type;

    private CityStateModel city;

    @Schema(example = Example.BOOLEAN, description = Description.EDUCATIONAL_INSTITUTION_ACTIVE)
    private Boolean active;

}
