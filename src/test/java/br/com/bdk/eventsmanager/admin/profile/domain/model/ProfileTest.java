package br.com.bdk.eventsmanager.admin.profile.domain.model;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.admin.privilege.domain.model.Privilege;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class ProfileTest {

    private Profile profile;

    @BeforeEach
    void setUp() {
        profile = new Profile();
        profile.setName("Vendedor");
        profile.setCompany(new Company());
    }

    @Test
    void shouldAddPrivilege() {
        profile.addPrivilege(new Privilege());
        assertThat(profile.getPrivileges().size()).isOne();
    }

    @Test
    void shouldAddSamePrivilege() {
        Privilege privilege = new Privilege();
        ReflectionTestUtils.setField(privilege, "id", 1L);

        profile.addPrivilege(privilege);
        profile.addPrivilege(privilege);
        assertThat(profile.getPrivileges().size()).isOne();
    }

    @Test
    void shouldRemoveAllPrivileges() {
        profile.addPrivilege(new Privilege());
        profile.addPrivilege(new Privilege());
        profile.addPrivilege(new Privilege());
        profile.addPrivilege(new Privilege());
        profile.addPrivilege(new Privilege());
        profile.addPrivilege(new Privilege());
        profile.addPrivilege(new Privilege());

        profile.removeAllPrivileges();
        assertThat(profile.getPrivileges()).isEmpty();
    }
}