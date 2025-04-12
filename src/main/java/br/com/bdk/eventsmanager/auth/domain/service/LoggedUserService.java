package br.com.bdk.eventsmanager.auth.domain.service;

import br.com.bdk.eventsmanager.auth.domain.model.UserDetailsDto;
import br.com.bdk.eventsmanager.auth.domain.model.exception.LoggedUserNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoggedUserService {

    public Long getUserId() {
        return this.getLoggedUserDetails().getId();
    }

    private UserDetailsDto getLoggedUserDetails() {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new LoggedUserNotFoundException();
        }

        final var principal = authentication.getPrincipal();
        if (!(principal instanceof UserDetailsDto)) {
            throw new LoggedUserNotFoundException();
        }

        return (UserDetailsDto) principal;
    }

    public boolean isAdmin() {
        return this.getLoggedUserDetails().isAdmin();
    }

    public String getUserCompanyExternalId() {
        return this.getLoggedUserDetails().getCompanyExternalId();
    }

}
