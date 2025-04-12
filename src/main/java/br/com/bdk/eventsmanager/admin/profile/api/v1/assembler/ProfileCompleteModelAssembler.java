package br.com.bdk.eventsmanager.admin.profile.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.profile.api.v1.model.ProfileCompleteModel;
import br.com.bdk.eventsmanager.admin.profile.domain.model.Profile;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class ProfileCompleteModelAssembler implements ModelAssembler<Profile, ProfileCompleteModel> {

    private final ProfileCompanyModelAssembler profileCompanyModelAssembler;
    private final ProfilePrivilegeModelAssembler profilePrivilegeModelAssembler;

    @NonNull
    @Override
    public ProfileCompleteModel toModel(@NonNull Profile profile) {
        return ProfileCompleteModel.builder()
                .id(profile.getExternalId())
                .name(profile.getName())
                .company(profileCompanyModelAssembler.toModel(profile.getCompany()))
                .privileges(new ArrayList<>(profilePrivilegeModelAssembler.toCollectionModel(profile.getPrivileges())))
                .build();
    }

}
