package br.com.bdk.eventsmanager.admin.supplier.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.ResourceNotFoundException;

public class SupplierNotFoundException extends ResourceNotFoundException {

    public SupplierNotFoundException(String id) {
        super("Não foi encontrado um fornecedor com código '%s'".formatted(id));
    }

}
