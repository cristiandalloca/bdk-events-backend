package br.com.bdk.eventsmanager.admin.company.domain.validator;

import br.com.bdk.eventsmanager.auth.domain.service.LoggedUserService;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@RequiredArgsConstructor
public class CompanyAccessService {

    private final LoggedUserService loggedUserService;

    public void validate(@NotEmpty String companyExternalId) {
        final var loggedCompanyExternalIdOrNullIfIsAdmin = this.getLoggedCompanyExternalIdOrNullIfIsAdmin();
        if (loggedCompanyExternalIdOrNullIfIsAdmin == null) {
            return;
        }

        if (!companyExternalId.equals(loggedCompanyExternalIdOrNullIfIsAdmin)) {
            throw new AccessDeniedException("Usuário logado não possui acesso a empresa");
        }
    }

    @Nullable
    public String getLoggedCompanyExternalIdOrNullIfIsAdmin() {
        if (loggedUserService.isAdmin()) {
            return null;
        }

        return loggedUserService.getUserCompanyExternalId();
    }
}
