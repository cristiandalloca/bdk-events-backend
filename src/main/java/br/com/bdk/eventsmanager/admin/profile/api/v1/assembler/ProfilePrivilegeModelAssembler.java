package br.com.bdk.eventsmanager.admin.profile.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.privilege.domain.model.Privilege;
import br.com.bdk.eventsmanager.admin.profile.api.v1.model.ProfileCompleteModel;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ProfilePrivilegeModelAssembler implements ModelAssembler<Privilege, ProfileCompleteModel.ProfilePrivilegeModel> {

    @NonNull
    @Override
    public ProfileCompleteModel.ProfilePrivilegeModel toModel(@NonNull Privilege source) {
        return ProfileCompleteModel.ProfilePrivilegeModel.builder()
                .id(source.getExternalId())
                .name(source.getName())
                .description(source.getDescription())
                .build();
    }
}
