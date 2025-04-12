package br.com.bdk.eventsmanager.admin.company.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.BusinessException;

public class DuplicateDocumentCompanyException extends BusinessException {

    public DuplicateDocumentCompanyException(String document) {
        super("Já existe uma empresa com CPF/CNPJ '%s'".formatted(document));
    }
}
