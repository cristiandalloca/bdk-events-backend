package br.com.bdk.eventsmanager.admin.country.api.v1.controller.openapi;

import br.com.bdk.eventsmanager.admin.country.api.v1.model.CountryModel;
import br.com.bdk.eventsmanager.admin.country.api.v1.model.input.CountryInput;
import br.com.bdk.eventsmanager.admin.state.api.v1.model.StateModel;
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

@Tag(name = "Countries")
@SecurityRequirement(name = "bearerAuth")
public interface CountryRegisterControllerOpenApi extends RegisterController<CountryInput, CountryModel> {

    @Operation(summary = "Find a country by id")
    CountryModel findByExternalId(@Parameter(description = Description.COUNTRY_IDENTIFIER, example = Example.IDENTIFIER) String externalId);

    @Operation(summary = "Create a new country")
    CountryModel create(CountryInput input);

    @Operation(summary = "Update a country by id")
    CountryModel updateByExternalId(@Parameter(description = Description.COUNTRY_IDENTIFIER, example = Example.IDENTIFIER) String externalId,
                                    CountryInput input);

    @Operation(summary = "Remove a country by id")
    void removeByExternalId(@Parameter(description = Description.COUNTRY_IDENTIFIER, example = Example.IDENTIFIER) String externalId);

    @Operation(summary = "Search countries")
    @ParameterQueryPageable
    PageModel<CountryModel> findAll(@Parameter(description = Description.COUNTRY_NAME) String name,
                                    @Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Search states by country")
    @ParameterQueryPageable
    PageModel<StateModel> findAllStates(@Parameter(description = Description.COUNTRY_IDENTIFIER, example = Example.IDENTIFIER) String externalCountryId,
                                        @Parameter(hidden = true) Pageable pageable);

}
