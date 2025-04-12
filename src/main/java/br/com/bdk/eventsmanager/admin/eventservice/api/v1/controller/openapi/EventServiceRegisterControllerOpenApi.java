package br.com.bdk.eventsmanager.admin.eventservice.api.v1.controller.openapi;

import br.com.bdk.eventsmanager.admin.eventservice.api.v1.model.EventServiceCompleteModel;
import br.com.bdk.eventsmanager.admin.eventservice.api.v1.model.EventServiceMeasurementTypeModel;
import br.com.bdk.eventsmanager.admin.eventservice.api.v1.model.input.EventServiceCompleteInput;
import br.com.bdk.eventsmanager.core.api.openapi.RegisterController;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Collection;

@Tag(name = "Event services")
@SecurityRequirement(name = "bearerAuth")
public interface EventServiceRegisterControllerOpenApi extends RegisterController<EventServiceCompleteInput, EventServiceCompleteModel> {

    @Override
    @Operation(summary = "Create a event service")
    EventServiceCompleteModel create(EventServiceCompleteInput input);

    @Override
    @Operation(summary = "Find event service by id")
    EventServiceCompleteModel findByExternalId(@Parameter(example = Example.IDENTIFIER, description = Description.EVENT_SERVICE_IDENTIFIER) String externalId);

    @Override
    @Operation(summary = "Update a event service")
    EventServiceCompleteModel updateByExternalId(@Parameter(example = Example.IDENTIFIER, description = Description.EVENT_SERVICE_IDENTIFIER) String externalId, EventServiceCompleteInput input);

    @Override
    @Operation(summary = "Remove a event service by id")
    void removeByExternalId(@Parameter(example = Example.IDENTIFIER, description = Description.EVENT_SERVICE_IDENTIFIER) String externalId);

    @Operation(summary = "Find all measurement types")
    Collection<EventServiceMeasurementTypeModel> findAllMeasurementTypes();
}
