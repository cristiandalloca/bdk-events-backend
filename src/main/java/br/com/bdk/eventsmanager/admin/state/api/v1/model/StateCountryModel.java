package br.com.bdk.eventsmanager.admin.state.api.v1.model;

import br.com.bdk.eventsmanager.admin.country.api.v1.model.CountryModel;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StateCountryModel {

    @Schema(example = Example.IDENTIFIER, description = Description.STATE_IDENTIFIER)
    private String id;

    @Schema(example = Example.STATE_NAME, description = Description.STATE_NAME)
    private String name;

    @Schema(example = Example.STATE_ACRONYM, description = Description.STATE_ACRONYM)
    private String acronym;

    private CountryModel country;
}
