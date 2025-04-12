package br.com.bdk.eventsmanager.admin.invitationtype.api.v1.model;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import static br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;

@Builder
@Getter
public class InvitationTypeModel {

    @Schema(example = Example.IDENTIFIER, description = Description.INVITATION_TYPE_IDENTIFIER)
    private String id;

    @Schema(example = Example.INVITATION_TYPE_NAME, description = Description.INVITATION_TYPE_NAME)
    private String name;

}
