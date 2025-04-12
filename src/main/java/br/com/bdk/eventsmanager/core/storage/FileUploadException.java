package br.com.bdk.eventsmanager.core.storage;

import br.com.bdk.eventsmanager.core.exception.BusinessException;

public class FileUploadException extends BusinessException {

    public FileUploadException(Throwable cause) {
        super("Falha ao realizar upload do arquivo", cause);
    }
}
