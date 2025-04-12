package br.com.bdk.eventsmanager.admin.user.domain.model;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.admin.profile.domain.model.Profile;
import br.com.bdk.eventsmanager.core.entity.ExposedEntity;
import br.com.bdk.eventsmanager.core.util.PasswordUtil;
import br.com.bdk.eventsmanager.core.util.RegexConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User extends ExposedEntity {

    @NotBlank
    @Size(max = 255)
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Email(regexp = RegexConstants.EMAIL_REGEX)
    @Size(max = 124)
    @Column(name = "email", length = 124, nullable = false)
    private String email;

    @NotBlank
    @Size(max = 50)
    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String login;

    @NotBlank
    @Size(max = 255)
    @Column(name = "password", nullable = false)
    @Setter(AccessLevel.NONE)
    private String password;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "active")
    private boolean active = true;

    @Setter(AccessLevel.NONE)
    @Column(name = "admin")
    private boolean admin;

    @Setter(AccessLevel.NONE)
    @Column(name = "update_password_next_login")
    private boolean updatePasswordNextLogin = true;

    @Setter(AccessLevel.NONE)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_profile",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id"))
    private Set<Profile> profiles = new HashSet<>();

    public String createNewPassword(PasswordEncoder passwordEncoder) {
        String generatePassword = PasswordUtil.generateRandomPassword();
        this.updatePasswordNextLogin = true;
        this.password = passwordEncoder.encode(generatePassword);
        return generatePassword;
    }

    public void changePassword(String newPassword) {
        this.updatePasswordNextLogin = false;
        this.password = newPassword;
    }

    public void addProfile(Profile profile) {
        this.getProfiles().add(profile);
    }

    public void removeProfile(Profile profile) {
        this.getProfiles().remove(profile);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
