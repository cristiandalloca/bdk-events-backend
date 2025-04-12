package br.com.bdk.eventsmanager.admin.service.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.ResourceNotFoundException;

public class ServiceSupplierNotFoundException extends ResourceNotFoundException {

    public ServiceSupplierNotFoundException(String externalId) {
        super("Não foi encontrado um fornecedor do serviço de código '%s'".formatted(externalId));
    }
}
