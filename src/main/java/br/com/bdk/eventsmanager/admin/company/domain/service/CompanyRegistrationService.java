package br.com.bdk.eventsmanager.admin.company.domain.service;

import br.com.bdk.eventsmanager.admin.city.domain.service.CityRegistrationService;
import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.admin.company.domain.model.exception.CompanyInUseException;
import br.com.bdk.eventsmanager.admin.company.domain.model.exception.CompanyNotFoundException;
import br.com.bdk.eventsmanager.admin.company.domain.repository.CompanyRepository;
import br.com.bdk.eventsmanager.admin.company.domain.validator.CompanyAccessService;
import br.com.bdk.eventsmanager.admin.company.domain.validator.CompanyRegistrationValidator;
import br.com.bdk.eventsmanager.core.service.RegistrationService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyRegistrationService implements RegistrationService<Company> {

    private final CompanyAccessService companyAccessService;
    private final CompanyRegistrationValidator companyRegistrationValidator;
    private final CityRegistrationService cityRegisterService;
    private final CompanyRepository companyRepository;

    @NonNull
    @Override
    @Transactional
    public Company validateAndSave(Company company) {
        if (!company.isNew()) {
            companyAccessService.validate(company.getExternalId());
        }
        companyRegistrationValidator.validate(company);
        var address = company.getAddress();
        if (address != null && address.getCity() != null) {
            address.setCity(cityRegisterService.findByExternalId(address.getCity().getExternalId()));
        }

        return companyRepository.save(company);
    }

    @NonNull
    @Override
    @Transactional(readOnly = true)
    public Company findByExternalId(String externalId) {
        return this.findByExternalIdOrFail(externalId);
    }

    private Company findByExternalIdOrFail(String externalId) {
        companyAccessService.validate(externalId);
        return companyRepository.findByExternalId(externalId)
                .orElseThrow(() -> new CompanyNotFoundException(externalId));
    }

    @NonNull
    @Transactional(readOnly = true)
    public Page<Company> findAll(@Nullable String name, @NotNull Pageable pageable) {
        final var loggedCompanyExternalIdOrNull = companyAccessService.getLoggedCompanyExternalIdOrNullIfIsAdmin();
        return companyRepository.findAllByNameOrBusinessName(loggedCompanyExternalIdOrNull, StringUtils.trimToNull(name), pageable);
    }

    @Override
    @Transactional
    public void removeByExternalId(String externalId) {
        try {
            companyRepository.delete(this.findByExternalIdOrFail(externalId));
            companyRepository.flush();
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage(), e);
            throw new CompanyInUseException(externalId);
        }
    }
}
