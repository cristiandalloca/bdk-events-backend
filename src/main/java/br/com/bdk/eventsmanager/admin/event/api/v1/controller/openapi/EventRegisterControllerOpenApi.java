package br.com.bdk.eventsmanager.admin.event.api.v1.controller.openapi;

import br.com.bdk.eventsmanager.admin.event.api.v1.model.EventModel;
import br.com.bdk.eventsmanager.admin.event.api.v1.model.EventTypeModel;
import br.com.bdk.eventsmanager.admin.event.api.v1.model.input.EventInput;
import br.com.bdk.eventsmanager.admin.event.api.v1.model.input.EventServiceInput;
import br.com.bdk.eventsmanager.admin.event.domain.model.EventType;
import br.com.bdk.eventsmanager.admin.eventservice.api.v1.model.EventServiceModel;
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

import java.util.Collection;

@Tag(name = "Events")
@SecurityRequirement(name = "bearerAuth")
public interface EventRegisterControllerOpenApi extends RegisterController<EventInput, EventModel> {

    @Override
    @Operation(summary = "Create a event")
    EventModel create(EventInput input);

    @Override
    @Operation(summary = "Find a event by id")
    EventModel findByExternalId(@Parameter(description = Description.EVENT_IDENTIFIER, example = Example.IDENTIFIER) String externalId);

    @Override
    @Operation(summary = "Update a event")
    EventModel updateByExternalId(@Parameter(description = Description.EVENT_IDENTIFIER, example = Example.IDENTIFIER) String externalId, EventInput input);

    @Override
    @Operation(summary = "Remove a event by id")
    void removeByExternalId(@Parameter(description = Description.EVENT_IDENTIFIER, example = Example.IDENTIFIER) String externalId);

    @Operation(summary = "Find all events types")
    Collection<EventTypeModel> findTypes();

    @Operation(summary = "Search events")
    @ParameterQueryPageable
    PageModel<EventModel> findAll(@Parameter(description = Description.EVENT_NAME) String name,
                                  @Parameter(description = Description.COMPANY_IDENTIFIER) String companyExternalId,
                                  @Parameter(description = Description.EVENT_TYPE) EventType type,
                                  @Parameter(description = Description.EVENT_ACTIVE) Boolean active,
                                  @Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Search event services by event id")
    @ParameterQueryPageable
    PageModel<EventServiceModel> findServicesByEventExternalId(@Parameter(description = Description.EVENT_IDENTIFIER, example = Example.IDENTIFIER) String eventExternalId,
                                                               @Parameter(description = Description.SERVICE_NAME) String serviceName,
                                                               @Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Add services to event")
    void addServicesToEvent(@Parameter(description = Description.EVENT_IDENTIFIER, example = Example.IDENTIFIER) String eventExternalId, EventServiceInput input);
}
