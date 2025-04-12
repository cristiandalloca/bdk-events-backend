package br.com.bdk.eventsmanager.admin.invitationtype.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.invitationtype.api.v1.model.InvitationTypeModel;
import br.com.bdk.eventsmanager.admin.invitationtype.domain.model.InvitationType;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class InvitationTypeModelAssembler implements ModelAssembler<InvitationType, InvitationTypeModel> {

    @NonNull
    @Override
    public InvitationTypeModel toModel(@NonNull InvitationType invitationType) {
        return InvitationTypeModel.builder()
                .id(invitationType.getExternalId())
                .name(invitationType.getName())
                .build();
    }
}
