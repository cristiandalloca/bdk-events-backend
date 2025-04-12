package br.com.bdk.eventsmanager.admin.profile.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.ResourceNotFoundException;

public class ProfileNotFoundException extends ResourceNotFoundException {

    private static final String PROFILE_NOT_FOUND_MESSAGE_EXCEPTION =
            "Não foi encontrado um perfil com código '%s'";

    public ProfileNotFoundException(String id) {
        super(PROFILE_NOT_FOUND_MESSAGE_EXCEPTION.formatted(id));
    }

    public ProfileNotFoundException(String id, String companyId) {
        super(PROFILE_NOT_FOUND_MESSAGE_EXCEPTION.formatted(id)
                .concat(" para empresa de código %s".formatted(companyId)));
    }
}
