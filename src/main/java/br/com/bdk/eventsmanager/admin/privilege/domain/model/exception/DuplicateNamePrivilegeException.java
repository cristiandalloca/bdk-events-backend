package br.com.bdk.eventsmanager.admin.privilege.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.BusinessException;

public class DuplicateNamePrivilegeException extends BusinessException {

    public DuplicateNamePrivilegeException(String name) {
        super("Já existe privilégio com nome '%s'".formatted(name));
    }

}
