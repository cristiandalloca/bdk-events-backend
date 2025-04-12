package br.com.bdk.eventsmanager.admin.invitationtype.api.v1.controller.openapi;

import br.com.bdk.eventsmanager.admin.invitationtype.api.v1.model.InvitationTypeModel;
import br.com.bdk.eventsmanager.admin.invitationtype.api.v1.model.input.InvitationTypeInput;
import br.com.bdk.eventsmanager.core.api.openapi.RegisterController;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Collection;

@Tag(name = "Invitation types")
@SecurityRequirement(name = "bearerAuth")
public interface InvitationTypeRegisterControllerOpenApi extends RegisterController<InvitationTypeInput, InvitationTypeModel> {

    @Override
    @Operation(summary = "Create a invitation type")
    InvitationTypeModel create(InvitationTypeInput input);

    @Override
    @Operation(summary = "Find a invitation type by id")
    InvitationTypeModel findByExternalId(@Parameter(description = Description.INVITATION_TYPE_IDENTIFIER, example = Example.IDENTIFIER) String externalId);

    @Override
    @Operation(summary = "Upadate a invitation type by id")
    InvitationTypeModel updateByExternalId(@Parameter(description = Description.INVITATION_TYPE_IDENTIFIER, example = Example.IDENTIFIER) String externalId,
                                           InvitationTypeInput input);

    @Override
    @Operation(summary = "Remove a invitation type by id")
    void removeByExternalId(@Parameter(description = Description.INVITATION_TYPE_IDENTIFIER, example = Example.IDENTIFIER) String externalId);

    @Operation(summary = "Search invitation types")
    Collection<InvitationTypeModel> findAll(@Parameter(description = Description.INVITATION_TYPE_NAME) String name);
}
