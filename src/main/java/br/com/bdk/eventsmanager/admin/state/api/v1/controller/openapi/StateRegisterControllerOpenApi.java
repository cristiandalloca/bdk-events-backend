package br.com.bdk.eventsmanager.admin.state.api.v1.controller.openapi;

import br.com.bdk.eventsmanager.admin.state.api.v1.model.StateCountryModel;
import br.com.bdk.eventsmanager.admin.state.api.v1.model.input.StateInput;
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

@Tag(name = "States")
@SecurityRequirement(name = "bearerAuth")
public interface StateRegisterControllerOpenApi extends RegisterController<StateInput, StateCountryModel> {

    @Operation(summary = "Find a state by id")
    StateCountryModel findByExternalId(@Parameter(description = Description.STATE_IDENTIFIER, example = Example.IDENTIFIER) String externalId);

    @Operation(summary = "Create a new state")
    StateCountryModel create(StateInput input);

    @Operation(summary = "Update a state by id")
    StateCountryModel updateByExternalId(@Parameter(description = Description.STATE_IDENTIFIER, example = Example.IDENTIFIER) String externalId,
                                         StateInput input);

    @Operation(summary = "Remove a state by id")
    void removeByExternalId(@Parameter(description = Description.STATE_IDENTIFIER, example = Example.IDENTIFIER) String externalId);

    @Operation(summary = "Search states")
    @ParameterQueryPageable
    PageModel<StateCountryModel> findAll(@Parameter(description = Description.COUNTRY_IDENTIFIER) String externalCountryId,
                                         @Parameter(description = Description.STATE_NAME) String name,
                                         @Parameter(hidden = true) Pageable pageable);

}
