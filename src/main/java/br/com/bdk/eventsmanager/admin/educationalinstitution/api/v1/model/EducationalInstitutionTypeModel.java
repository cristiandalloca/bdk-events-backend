package br.com.bdk.eventsmanager.admin.educationalinstitution.api.v1.model;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EducationalInstitutionTypeModel {

    @Schema(example = Example.EDUCATIONAL_INSTITUTION_TYPE, description = Description.EDUCATIONAL_INSTITUTION_TYPE)
    private String id;

    @Schema(example = Example.EDUCATIONAL_INSTITUTION_TYPE_DESCRIPTION, description = Description.EDUCATIONAL_INSTITUTION_TYPE_DESCRIPTION)
    private String description;
}
