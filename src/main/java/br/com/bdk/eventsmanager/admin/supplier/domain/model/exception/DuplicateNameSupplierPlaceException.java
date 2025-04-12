package br.com.bdk.eventsmanager.admin.supplier.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.BusinessException;

public class DuplicateNameSupplierPlaceException extends BusinessException {
    public DuplicateNameSupplierPlaceException(String name) {
        super("Já existe um local para o fornecedor com nome '%s'".formatted(name));
    }
}