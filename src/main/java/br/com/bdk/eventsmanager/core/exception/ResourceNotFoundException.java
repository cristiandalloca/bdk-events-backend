package br.com.bdk.eventsmanager.core.exception;

public abstract class ResourceNotFoundException extends BusinessException {

    protected ResourceNotFoundException(String message) {
        super(message);
    }

}
