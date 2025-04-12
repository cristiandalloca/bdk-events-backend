package br.com.bdk.eventsmanager.admin.city.api.v1.controller.openapi;

import br.com.bdk.eventsmanager.admin.city.api.v1.model.CityStateCountryModel;
import br.com.bdk.eventsmanager.admin.city.api.v1.model.input.CityInput;
import br.com.bdk.eventsmanager.core.api.model.PageModel;
import br.com.bdk.eventsmanager.core.api.openapi.ParameterQueryPageable;
import br.com.bdk.eventsmanager.core.api.openapi.RegisterController;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;

@Tag(name = "Cities")
@SecurityRequirement(name = "bearerAuth")
public interface CityRegisterControllerOpenApi extends RegisterController<CityInput, CityStateCountryModel> {

    @Operation(summary = "Find a city by id")
    CityStateCountryModel findByExternalId(@Parameter(description = Description.CITY_IDENTIFIER, example = Example.IDENTIFIER) String externalId);

    @Operation(summary = "Create a new city")
    CityStateCountryModel create(CityInput input);

    @Operation(summary = "Update a city")
    CityStateCountryModel updateByExternalId(@Parameter(description = Description.CITY_IDENTIFIER, example = Example.IDENTIFIER) String externalId,
                                             CityInput input);

    @Operation(summary = "Remove a city")
    void removeByExternalId(@Parameter(description = Description.CITY_IDENTIFIER, example = Example.IDENTIFIER) String externalId);

    @Operation(summary = "Search cities")
    @ParameterQueryPageable
    PageModel<CityStateCountryModel> findAll(@Parameter(description = Description.STATE_IDENTIFIER) String externalStateId,
                                             @Parameter(description = Description.CITY_NAME) String name,
                                             @Parameter(hidden = true) Pageable pageable);

}
