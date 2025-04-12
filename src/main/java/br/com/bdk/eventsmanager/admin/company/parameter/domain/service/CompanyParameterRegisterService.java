package br.com.bdk.eventsmanager.admin.company.parameter.domain.service;

import br.com.bdk.eventsmanager.admin.company.domain.service.CompanyRegistrationService;
import br.com.bdk.eventsmanager.admin.company.parameter.domain.model.CompanyParameter;
import br.com.bdk.eventsmanager.admin.company.parameter.domain.model.exception.CompanyParameterNotFoundException;
import br.com.bdk.eventsmanager.admin.company.parameter.domain.repository.CompanyParameterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyParameterRegisterService {

    private final CompanyRegistrationService companyRegistrationService;
    private final CompanyParameterRepository companyParameterRepository;

    @Transactional
    public CompanyParameter findByCompanyExternalIdOrCreate(@NonNull String companyExternalId) {
        var company = companyRegistrationService.findByExternalId(companyExternalId);
        var companyParameter = companyParameterRepository.findByCompanyId(company.getId())
                .orElse(null);

        if (companyParameter == null) {
            companyParameter = new CompanyParameter();
            companyParameter.setCompany(company);
        }

        return companyParameter;
    }

    @Transactional(readOnly = true)
    public CompanyParameter findByCompanyExternalIdOrFail(@NonNull String companyExternalId) {
        var company = companyRegistrationService.findByExternalId(companyExternalId);
        return companyParameterRepository.findByCompanyId(company.getId()).orElseThrow(() ->
                new CompanyParameterNotFoundException(companyExternalId));
    }

    @Transactional(readOnly = true)
    public Optional<CompanyParameter> findByCompanyExternalId(@NonNull String companyExternalId) {
        var company = companyRegistrationService.findByExternalId(companyExternalId);
        return companyParameterRepository.findByCompanyId(company.getId());
    }

    @Transactional
    public CompanyParameter save(CompanyParameter companyParameter) {
        return companyParameterRepository.save(companyParameter);
    }
}
