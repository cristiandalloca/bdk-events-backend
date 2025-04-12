package br.com.bdk.eventsmanager.admin.company.invitationtype.api.v1.controller.openapi;

import br.com.bdk.eventsmanager.admin.company.invitationtype.api.v1.model.CompanyInvitationTypeModel;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Collection;

@Tag(name = "Companies")
@SecurityRequirement(name = "bearerAuth")
public interface CompanyInvitationTypeRegisterControllerOpenApi {

    @Operation(summary = "Add invitation type to a company")
    void addInvitationType(@Parameter(description = Description.COMPANY_IDENTIFIER, example = Example.IDENTIFIER) String externalCompanyId,
                           @Parameter(description = Description.INVITATION_TYPE_IDENTIFIER, example = Example.IDENTIFIER) String externalInvitationTypeId);

    @Operation(summary = "Remove invitation type of a company")
    void removeInvitationType(@Parameter(description = Description.COMPANY_IDENTIFIER, example = Example.IDENTIFIER) String externalCompanyId,
                              @Parameter(description = Description.INVITATION_TYPE_IDENTIFIER, example = Example.IDENTIFIER) String externalInvitationTypeId);

    @Operation(summary = "Find all invitation type of company")
    Collection<CompanyInvitationTypeModel> findAllInvitationTypes(@Parameter(description = Description.COMPANY_IDENTIFIER, example = Example.IDENTIFIER) String externalCompanyId);

}