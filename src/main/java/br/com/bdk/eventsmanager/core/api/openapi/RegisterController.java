package br.com.bdk.eventsmanager.core.api.openapi;

public interface RegisterController<I, M> {

    M create(I input);
    M findByExternalId(String externalId);
    M updateByExternalId(String externalId, I input);
    void removeByExternalId(String externalId);
}
