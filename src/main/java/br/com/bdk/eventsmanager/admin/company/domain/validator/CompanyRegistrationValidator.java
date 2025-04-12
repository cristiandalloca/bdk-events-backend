package br.com.bdk.eventsmanager.admin.company.domain.validator;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.admin.company.domain.model.exception.DuplicateDocumentCompanyException;
import br.com.bdk.eventsmanager.admin.company.domain.repository.CompanyRepository;
import br.com.bdk.eventsmanager.core.exception.BusinessException;
import br.com.bdk.eventsmanager.core.validator.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CompanyRegistrationValidator implements RegistrationValidator<Company> {

    private final CompanyRepository companyRepository;

    @Override
    @Transactional(readOnly = true)
    public void validate(@NonNull Company company) throws BusinessException {
        var existingCompanyByDocument = companyRepository.findByDocument(company.getDocument());
        if (existingCompanyByDocument.isPresent() && this.isNotSame(company, existingCompanyByDocument.get())) {
            throw new DuplicateDocumentCompanyException(company.getDocument());
        }
    }

}
