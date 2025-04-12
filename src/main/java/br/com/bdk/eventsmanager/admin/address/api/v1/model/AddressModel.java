package br.com.bdk.eventsmanager.admin.address.api.v1.model;

import br.com.bdk.eventsmanager.admin.city.api.v1.model.CityStateModel;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class AddressModel {

    @Schema(example = Example.ADDRESS_STREET, description = Description.ADDRESS_STREET)
    private String street;

    @Schema(example = Example.ADDRESS_NEIGHBORHOOD, description = Description.ADDRESS_NEIGHBORHOOD)
    private String neighborhood;

    private CityStateModel city;

}
