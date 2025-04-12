package br.com.bdk.eventsmanager.admin.address.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.ResourceNotFoundException;

public class PostalCodeNotFoundException extends ResourceNotFoundException {

    public PostalCodeNotFoundException(String postalCode) {
        super("Não foi encontrado um endereço para o CEP '%s'".formatted(postalCode));
    }
}
