package br.com.bdk.eventsmanager.core.storage;

import br.com.bdk.eventsmanager.core.exception.BusinessException;

public class FileDownloadException extends BusinessException {

    public FileDownloadException(Throwable cause) {
        super("Falha ao realizar download do arquivo", cause);
    }
}
