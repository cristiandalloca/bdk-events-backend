package br.com.bdk.eventsmanager.admin.country.api.v1.model;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CountryModel {

    @Schema(example = Example.IDENTIFIER, description = Description.COUNTRY_IDENTIFIER)
    private String id;

    @Schema(example = Example.COUNTRY_NAME, description = Description.COUNTRY_NAME)
    private String name;

    @Schema(example = Example.COUNTRY_ACRONYM, description = Description.COUNTRY_ACRONYM)
    private String acronym;
}
