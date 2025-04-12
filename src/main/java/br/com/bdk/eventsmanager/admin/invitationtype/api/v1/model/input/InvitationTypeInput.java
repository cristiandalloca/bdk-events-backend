package br.com.bdk.eventsmanager.admin.invitationtype.api.v1.model.input;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class InvitationTypeInput {

    @NotBlank
    @Size(max = 50)
    @Schema(example = "Baile", description = SpringDocConstantsUtil.Description.INVITATION_TYPE_NAME)
    private String name;

}
