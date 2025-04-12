package br.com.bdk.eventsmanager.core.service;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface RegistrationService<E> {

    E validateAndSave(@NotNull @Valid E entity);

    E findByExternalId(@NotBlank String externalId);

    void removeByExternalId(@NotBlank String externalId);

}
