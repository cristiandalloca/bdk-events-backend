package br.com.bdk.eventsmanager.admin.invitationtype.api.v1.disassembler;

import br.com.bdk.eventsmanager.admin.invitationtype.api.v1.model.input.InvitationTypeInput;
import br.com.bdk.eventsmanager.admin.invitationtype.domain.model.InvitationType;
import br.com.bdk.eventsmanager.core.api.disassembler.InputDisassembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class InvitationTypeInputDisassembler implements InputDisassembler<InvitationTypeInput, InvitationType> {

    @Override
    public InvitationType toEntity(@NonNull InvitationTypeInput input) {
        var invitationType = new InvitationType();
        this.copyToEntity(input, invitationType);
        return invitationType;
    }

    @Override
    public void copyToEntity(@NonNull InvitationTypeInput input, @NonNull InvitationType invitationType) {
        invitationType.setName(input.getName());
    }
}
