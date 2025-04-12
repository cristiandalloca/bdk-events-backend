package br.com.bdk.eventsmanager.admin.user.domain.service;

import br.com.bdk.eventsmanager.admin.privilege.domain.model.Privilege;
import br.com.bdk.eventsmanager.admin.profile.domain.model.Profile;
import br.com.bdk.eventsmanager.admin.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserPrivilegeRegisterService {

    private final UserRegistrationService userRegisterService;

    @NonNull
    @Transactional(readOnly = true)
    public List<Privilege> findAllPrivileges(@NonNull String userExternalId) {
        User user = userRegisterService.findByExternalIdWithPrivileges(userExternalId);
        Set<Privilege> privileges = new HashSet<>();
        for (Profile profile : user.getProfiles()) {
            privileges.addAll(profile.getPrivileges());
        }
        return new ArrayList<>(privileges);
    }
}
