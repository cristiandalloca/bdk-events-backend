package br.com.bdk.eventsmanager.admin.profile.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.profile.api.v1.model.ProfileModel;
import br.com.bdk.eventsmanager.admin.profile.domain.model.Profile;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileModelAssembler implements ModelAssembler<Profile, ProfileModel> {

    private final ProfileCompanyModelAssembler profileCompanyModelAssembler;

    @NonNull
    @Override
    public ProfileModel toModel(@NonNull Profile profile) {
        return ProfileModel.builder()
                .id(profile.getExternalId())
                .name(profile.getName())
                .company(profileCompanyModelAssembler.toModel(profile.getCompany()))
                .build();
    }
}
