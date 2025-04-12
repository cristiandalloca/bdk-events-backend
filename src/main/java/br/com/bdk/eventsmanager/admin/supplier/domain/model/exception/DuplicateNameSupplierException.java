package br.com.bdk.eventsmanager.admin.supplier.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.BusinessException;

public class DuplicateNameSupplierException extends BusinessException {

    public DuplicateNameSupplierException(String name) {
        super("Já existe um fornecedor com nome '%s'".formatted(name));
    }
}
