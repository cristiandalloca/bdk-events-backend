package br.com.bdk.eventsmanager.admin.profile.domain.validator;


import br.com.bdk.eventsmanager.admin.profile.domain.model.Profile;
import br.com.bdk.eventsmanager.admin.profile.domain.model.exception.DuplicateNameProfileException;
import br.com.bdk.eventsmanager.admin.profile.domain.repository.ProfileRepository;
import br.com.bdk.eventsmanager.core.exception.BusinessException;
import br.com.bdk.eventsmanager.core.validator.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileRegistrationValidator implements RegistrationValidator<Profile> {

    private final ProfileRepository profileRepository;

    @Override
    public void validate(@NonNull Profile profile) throws BusinessException {
        var company = profile.getCompany();
        var existingProfileByNameAndCompany
                = profileRepository.findByNameIgnoreCaseAndCompanyId(profile.getName(), company.getId());
        if (existingProfileByNameAndCompany.isPresent() && this.isNotSame(profile, existingProfileByNameAndCompany.get())) {
            throw new DuplicateNameProfileException(profile.getName());
        }
    }

}
