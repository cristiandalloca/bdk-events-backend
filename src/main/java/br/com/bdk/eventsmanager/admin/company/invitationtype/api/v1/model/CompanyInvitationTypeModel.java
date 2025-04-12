package br.com.bdk.eventsmanager.admin.company.invitationtype.api.v1.model;

import br.com.bdk.eventsmanager.admin.invitationtype.api.v1.model.InvitationTypeModel;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CompanyInvitationTypeModel {

    @Schema(example = Example.IDENTIFIER, description = Description.COMPANY_INVITATION_TYPE_IDENTIFIER)
    private String id;

    private InvitationTypeModel invitationType;
}
