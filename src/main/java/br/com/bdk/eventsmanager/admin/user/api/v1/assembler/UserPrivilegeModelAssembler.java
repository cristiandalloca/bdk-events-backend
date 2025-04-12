package br.com.bdk.eventsmanager.admin.user.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.privilege.domain.model.Privilege;
import br.com.bdk.eventsmanager.admin.user.api.v1.model.UserPrivilegeModel;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserPrivilegeModelAssembler implements ModelAssembler<Privilege, UserPrivilegeModel> {

    @NonNull
    @Override
    public UserPrivilegeModel toModel(@NonNull Privilege privilege) {
        return UserPrivilegeModel.builder()
                .id(privilege.getExternalId())
                .name(privilege.getName())
                .description(privilege.getDescription())
                .build();
    }
}
