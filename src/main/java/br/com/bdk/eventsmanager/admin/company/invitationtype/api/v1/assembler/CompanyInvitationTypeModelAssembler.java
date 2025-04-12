package br.com.bdk.eventsmanager.admin.company.invitationtype.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.company.invitationtype.api.v1.model.CompanyInvitationTypeModel;
import br.com.bdk.eventsmanager.admin.company.invitationtype.domain.model.CompanyInvitationType;
import br.com.bdk.eventsmanager.admin.invitationtype.api.v1.assembler.InvitationTypeModelAssembler;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyInvitationTypeModelAssembler implements ModelAssembler<CompanyInvitationType, CompanyInvitationTypeModel> {

    private final InvitationTypeModelAssembler invitationTypeModelAssembler;

    @Override
    public CompanyInvitationTypeModel toModel(CompanyInvitationType companyInvitationType) {
        return CompanyInvitationTypeModel.builder()
                .id(companyInvitationType.getExternalId())
                .invitationType(invitationTypeModelAssembler.toModel(companyInvitationType.getInvitationType()))
                .build();
    }
}
