package br.com.bdk.eventsmanager.admin.user.domain.service;

import br.com.bdk.eventsmanager.admin.profile.domain.service.ProfileRegistrationService;
import br.com.bdk.eventsmanager.admin.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileRegisterService {

    private final UserRegistrationService userRegisterService;
    private final ProfileRegistrationService profileRegisterService;
    private final UserRepository userRepository;

    @Transactional
    public void addProfile(@NonNull String userExternalId, @NonNull String profileExternalId) {
        var user = userRegisterService.findByExternalId(userExternalId);
        var profile
                = profileRegisterService.findByExternalIdAndCompanyExternalId(profileExternalId, user.getCompany().getExternalId());

        user.addProfile(profile);
        userRepository.save(user);
    }

    @Transactional
    public void removeProfile(@NonNull String userExternalId, @NonNull String profileExternalId) {
        var user = userRegisterService.findByExternalId(userExternalId);
        var profile = profileRegisterService.findByExternalId(profileExternalId);

        user.removeProfile(profile);
        userRepository.save(user);
    }

}
