package br.com.bdk.eventsmanager.admin.supplier.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.ResourceNotFoundException;

public class SupplierPlaceNotFoundException extends ResourceNotFoundException {
    public SupplierPlaceNotFoundException(String id) {
        super("Não foi encontrado um local do fornecedor com código '%s'".formatted(id));
    }
}