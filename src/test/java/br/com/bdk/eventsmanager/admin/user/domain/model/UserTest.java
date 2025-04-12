package br.com.bdk.eventsmanager.admin.user.domain.model;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.admin.profile.domain.model.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("Cristian Berti");
        user.setCompany(new Company());
        user.setLogin("cristianberti");
        user.setActive(true);
        user.setEmail("email@email.com");
    }

    @Test
    void shouldCreateNewPassword() {
        final var passwordEncoder = Mockito.mock(PasswordEncoder.class);
        String passwordEncoded = "PasswordEncoded";
        when(passwordEncoder.encode(anyString())).thenReturn(passwordEncoded);
        var passwordGenerated = user.createNewPassword(passwordEncoder);
        assertNotNull(passwordGenerated);
        assertNotNull(user.getPassword());
        assertTrue(user.isUpdatePasswordNextLogin());
        assertEquals(passwordEncoded, user.getPassword());
    }

    @Test
    void shouldChangePassword() {
        String newPassword = "123";
        user.changePassword(newPassword);
        assertFalse(user.isUpdatePasswordNextLogin());
        assertEquals(newPassword, user.getPassword());
    }

    @Test
    void shouldAddProfile() {
        var profile = new Profile();
        user.addProfile(profile);
        assertThat(user.getProfiles()).containsOnly(profile);
    }

    @Test
    void shouldRemoveProfile() {
        var profile = new Profile();
        user.addProfile(profile);
        user.removeProfile(profile);
        assertThat(user.getProfiles()).isEmpty();
    }
}