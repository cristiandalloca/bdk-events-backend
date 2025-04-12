package br.com.bdk.eventsmanager.admin.user.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.profile.domain.model.Profile;
import br.com.bdk.eventsmanager.admin.user.api.v1.model.UserProfileModel;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserProfileModelAssembler implements ModelAssembler<Profile, UserProfileModel> {

    @NonNull
    @Override
    public UserProfileModel toModel(@NonNull Profile profile) {
        return UserProfileModel.builder()
                .id(profile.getExternalId())
                .name(profile.getName())
                .build();
    }

}
