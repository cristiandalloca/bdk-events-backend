package br.com.bdk.eventsmanager.core.storage;

import br.com.bdk.eventsmanager.core.exception.BusinessException;

public class FileDeleteException extends BusinessException {

    public FileDeleteException(Throwable cause) {
        super("Falha ao remover arquivo", cause);
    }
}
