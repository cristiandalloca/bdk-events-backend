package br.com.bdk.eventsmanager.core.storage;

import br.com.bdk.eventsmanager.core.exception.BusinessException;

public class FileTemporaryUrlException extends BusinessException {

    public FileTemporaryUrlException(Throwable cause) {
        super("Falha ao gerar url temporária para arquivo", cause);
    }
}
