package br.com.bdk.eventsmanager.admin.city.api.v1.model;

import br.com.bdk.eventsmanager.admin.state.api.v1.model.StateCountryModel;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CityStateCountryModel {

    @Schema(example = Example.IDENTIFIER, description = Description.CITY_IDENTIFIER)
    private String id;

    @Schema(example = Example.CITY_NAME, description = Description.CITY_NAME)
    private String name;

    private StateCountryModel state;
}
