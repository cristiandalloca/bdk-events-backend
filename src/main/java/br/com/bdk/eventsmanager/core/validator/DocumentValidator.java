package br.com.bdk.eventsmanager.core.validator;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class DocumentValidator implements ConstraintValidator<Document, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isBlank(value) || isCpf(value) || isCnpj(value);
    }

    private boolean isCpf(String cpf) {
        cpf = cpf.replace(".", "");
        cpf = cpf.replace("-", "");

        var cpfValidator = new CPFValidator();
        return cpfValidator.invalidMessagesFor(cpf).isEmpty();
    }

    private boolean isCnpj(String cnpj) {
        cnpj = cnpj.replace(".", "");
        cnpj = cnpj.replace("-", "");
        cnpj = cnpj.replace("/", "");

        var cnpjValidator = new CNPJValidator();
        return cnpjValidator.invalidMessagesFor(cnpj).isEmpty();
    }

}
