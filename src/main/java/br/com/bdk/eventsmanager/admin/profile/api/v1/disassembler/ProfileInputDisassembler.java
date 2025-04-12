package br.com.bdk.eventsmanager.admin.profile.api.v1.disassembler;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.admin.privilege.domain.model.Privilege;
import br.com.bdk.eventsmanager.admin.profile.api.v1.model.input.ProfileInput;
import br.com.bdk.eventsmanager.admin.profile.domain.model.Profile;
import br.com.bdk.eventsmanager.core.api.disassembler.InputDisassembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ProfileInputDisassembler implements InputDisassembler<ProfileInput, Profile> {

    @Override
    public Profile toEntity(@NonNull ProfileInput input) {
        var profile = new Profile();
        this.copyToEntity(input, profile);
        return profile;
    }

    @Override
    public void copyToEntity(@NonNull ProfileInput input, @NonNull Profile profile) {
        profile.setName(input.getName());
        profile.setCompany(new Company(input.getCompanyExternalId()));
        if (input.getPrivilegesExternalIds() != null) {
            input.getPrivilegesExternalIds().forEach(privilegeExternalId -> profile.addPrivilege(new Privilege(privilegeExternalId)));
        }
    }
}
