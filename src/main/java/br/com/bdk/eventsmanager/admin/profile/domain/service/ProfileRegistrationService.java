package br.com.bdk.eventsmanager.admin.profile.domain.service;

import br.com.bdk.eventsmanager.admin.company.domain.service.CompanyRegistrationService;
import br.com.bdk.eventsmanager.admin.privilege.domain.model.Privilege;
import br.com.bdk.eventsmanager.admin.privilege.domain.service.PrivilegeRegistrationService;
import br.com.bdk.eventsmanager.admin.profile.domain.model.Profile;
import br.com.bdk.eventsmanager.admin.profile.domain.model.exception.ProfileNotFoundException;
import br.com.bdk.eventsmanager.admin.profile.domain.repository.ProfileRepository;
import br.com.bdk.eventsmanager.admin.profile.domain.validator.ProfileRegistrationValidator;
import br.com.bdk.eventsmanager.core.exception.ResourceInUseException;
import br.com.bdk.eventsmanager.core.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileRegistrationService implements RegistrationService<Profile> {

    private final ProfileRegistrationValidator profileRegisterValidator;
    private final CompanyRegistrationService companyRegistrationService;
    private final ProfileRepository profileRepository;
    private final PrivilegeRegistrationService privilegeRegisterService;
    private static final String EXCEPTION_MESSAGE_PROFILE_IN_USE
            = "Perfil de código '%s' não pode ser removido, pois está em uso";

    @Override
    @NonNull
    @Transactional
    public Profile validateAndSave(@NonNull Profile profile) {
        profile.setCompany(companyRegistrationService.findByExternalId(profile.getCompany().getExternalId()));
        Set<Privilege> privileges = profile.getPrivileges();
        if (privileges != null) {
            Set<Privilege> privilegesToSearch = new HashSet<>(privileges);
            profile.removeAllPrivileges();
            privilegesToSearch.forEach(privilege -> profile.addPrivilege(privilegeRegisterService.findByExternalId(privilege.getExternalId())));
        }
        profileRegisterValidator.validate(profile);
        return profileRepository.save(profile);
    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public Profile findByExternalId(@NonNull String externalId) {
        return findByExternalIdOrFail(externalId);
    }

    private Profile findByExternalIdOrFail(String externalId) {
        return profileRepository.findByExternalId(externalId)
                .orElseThrow(() -> new ProfileNotFoundException(externalId));
    }

    @Override
    @NonNull
    @Transactional
    public void removeByExternalId(@NonNull String externalId) {
        try {
            profileRepository.delete(this.findByExternalIdOrFail(externalId));
            profileRepository.flush();
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage(), e);
            throw new ResourceInUseException(EXCEPTION_MESSAGE_PROFILE_IN_USE.formatted(externalId));
        }
    }

    @NonNull
    @Transactional(readOnly = true)
    public Page<Profile> findAll(@Nullable String name, @Nullable String companyExternalId, @NonNull Pageable pageable) {
        return profileRepository.findAllByNameAndCompanyExternalId(StringUtils.trimToNull(name), StringUtils.trimToNull(companyExternalId), pageable);
    }

    @NonNull
    @Transactional(readOnly = true)
    public Profile findByExternalIdAndCompanyExternalId(@NonNull String externalId, @NonNull String companyExternalId) {
        return profileRepository.findByExternalIdAndCompanyExternalId(externalId, companyExternalId)
                .orElseThrow(() -> new ProfileNotFoundException(externalId, companyExternalId));
    }
}
