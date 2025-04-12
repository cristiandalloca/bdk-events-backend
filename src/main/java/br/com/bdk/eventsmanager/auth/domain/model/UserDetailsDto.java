package br.com.bdk.eventsmanager.auth.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class UserDetailsDto implements UserDetails {

    private Long id;

    private String externalId;

    private String name;

    private String login;

    private String password;

    private String companyExternalId;

    private String companyName;

    private boolean companyActive;

    private boolean updatePasswordNextLogin;

    private boolean active;

    private boolean admin;

    @Setter
    private Set<PrivilegeDto> privileges = new HashSet<>();

    public UserDetailsDto(Long id, String externalId, String name, String login, String password, String companyExternalId,
                          String companyName, boolean companyActive, boolean updatePasswordNextLogin, boolean active, boolean admin) {
        this.id = id;
        this.externalId = externalId;
        this.name = name;
        this.login = login;
        this.password = password;
        this.companyExternalId = companyExternalId;
        this.companyName = companyName;
        this.companyActive = companyActive;
        this.updatePasswordNextLogin = updatePasswordNextLogin;
        this.active = active;
        this.admin = admin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.isAdmin()) {
            return Set.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return privileges.stream()
                .map(PrivilegeDto::getName)
                .map(privilegeName -> new SimpleGrantedAuthority("ROLE_".concat(privilegeName)))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.companyActive;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
