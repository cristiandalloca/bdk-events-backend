package br.com.bdk.eventsmanager.admin.user.domain.service;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.admin.profile.domain.model.Profile;
import br.com.bdk.eventsmanager.admin.profile.domain.service.ProfileRegistrationService;
import br.com.bdk.eventsmanager.admin.user.domain.model.User;
import br.com.bdk.eventsmanager.admin.user.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserProfileRegistrationServiceTest {

    @InjectMocks
    private UserProfileRegisterService userProfileregisterService;

    @Mock
    private UserRegistrationService userRegisterService;

    @Mock
    private ProfileRegistrationService profileRegisterService;

    @Mock
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Test
    void shouldAddUserProfileWhenProfileIsNotAdded() {
        var userExternalId = "1L";
        var profileExternalId = "2L";
        var companyExternalId = "3L";

        User user = new User();
        user.setCompany(new Company(companyExternalId));
        when(userRegisterService.findByExternalId(userExternalId))
                .thenReturn(user);

        Profile profile = new Profile();
        profile.setName("teste");
        when(profileRegisterService.findByExternalIdAndCompanyExternalId(profileExternalId, companyExternalId))
                .thenReturn(profile);

        userProfileregisterService.addProfile(userExternalId, profileExternalId);
        inOrder(userRegisterService, profileRegisterService, userRepository);
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());

        User userSaved = userArgumentCaptor.getValue();
        assertThat(userSaved.getProfiles()).contains(profile);
    }

    @Test
    void shouldAddUserProfileWhenProfileIsAdded() {
        var userExternalId = "1L";
        var profileExternalId = "2L";
        var companyExternalId = "3L";

        Profile profile = new Profile();
        profile.setName("teste");
        when(profileRegisterService.findByExternalIdAndCompanyExternalId(profileExternalId, companyExternalId))
                .thenReturn(profile);

        User user = new User();
        user.setCompany(new Company(companyExternalId));
        user.addProfile(profile);
        when(userRegisterService.findByExternalId(userExternalId)).thenReturn(user);

        userProfileregisterService.addProfile(userExternalId, profileExternalId);
        inOrder(userRegisterService, profileRegisterService, userRepository);
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());

        User userSaved = userArgumentCaptor.getValue();
        assertThat(userSaved.getProfiles()).contains(profile);
        assertThat(userSaved.getProfiles()).hasSize(1);
    }

    @Test
    void shouldRemoveUserProfileWhenProfileIsAdded() {
        var userExternalId = "1L";
        var profileExternalId = "2L";

        Profile profile = new Profile();
        profile.setName("teste");
        when(profileRegisterService.findByExternalId(profileExternalId)).thenReturn(profile);

        User user = new User();
        user.addProfile(profile);
        when(userRegisterService.findByExternalId(userExternalId)).thenReturn(user);

        userProfileregisterService.removeProfile(userExternalId, profileExternalId);

        inOrder(userRegisterService, profileRegisterService, userRepository);
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());

        User userSaved = userArgumentCaptor.getValue();
        assertThat(userSaved.getProfiles()).doesNotContain(profile);
        assertThat(userSaved.getProfiles()).isEmpty();
    }

    @Test
    void shouldRemoveUserProfileWhenProfileIsNotAdded() {
        var userExternalId = "1L";
        var profileExternalId = "2L";

        Profile profile = new Profile();
        profile.setName("teste");
        when(profileRegisterService.findByExternalId(profileExternalId)).thenReturn(profile);

        User user = new User();
        when(userRegisterService.findByExternalId(userExternalId)).thenReturn(user);

        userProfileregisterService.removeProfile(userExternalId, profileExternalId);

        inOrder(userRegisterService, profileRegisterService, userRepository);
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());

        User userSaved = userArgumentCaptor.getValue();
        assertThat(userSaved.getProfiles()).doesNotContain(profile);
        assertThat(userSaved.getProfiles()).isEmpty();
    }
}