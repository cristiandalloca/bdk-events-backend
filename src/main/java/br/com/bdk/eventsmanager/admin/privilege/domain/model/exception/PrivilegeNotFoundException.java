package br.com.bdk.eventsmanager.admin.privilege.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.ResourceNotFoundException;

public class PrivilegeNotFoundException extends ResourceNotFoundException {

    public PrivilegeNotFoundException(String id) {
        super("Não foi encontrado um privilégio com código '%s'".formatted(id));
    }
}
