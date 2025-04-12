package br.com.bdk.eventsmanager.admin.service.domain.service;

import br.com.bdk.eventsmanager.admin.company.domain.service.CompanyRegistrationService;
import br.com.bdk.eventsmanager.admin.service.domain.model.Service;
import br.com.bdk.eventsmanager.admin.service.domain.model.dto.ServiceFilter;
import br.com.bdk.eventsmanager.admin.service.domain.model.exception.ServiceNotFoundException;
import br.com.bdk.eventsmanager.admin.service.domain.repository.ServiceRepository;
import br.com.bdk.eventsmanager.admin.service.domain.validator.ServiceRegistrationValidator;
import br.com.bdk.eventsmanager.core.exception.ResourceInUseException;
import br.com.bdk.eventsmanager.core.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceRegistrationService implements RegistrationService<Service> {

    private final CompanyRegistrationService companyRegistrationService;
    private final ServiceRepository serviceRepository;
    private final ServiceRegistrationValidator serviceRegisterValidator;
    private static final String EXCEPTION_MESSAGE_SERVICE_IN_USE
            = "Serviço de código '%s' não pode ser removido, pois está em uso";

    @NonNull
    @Transactional
    @Override
    public Service validateAndSave(@NonNull @Valid Service service) {
        serviceRegisterValidator.validate(service);
        service.setCompany(companyRegistrationService.findByExternalId(service.getCompany().getExternalId()));
        return serviceRepository.save(service);
    }

    @NonNull
    @Transactional(readOnly = true)
    @Override
    public Service findByExternalId(@NonNull String externalId) {
        return this.findByExternalIdOrFail(externalId);
    }

    private Service findByExternalIdOrFail(String externalId) {
        return serviceRepository.findByExternalId(externalId)
                .orElseThrow(() -> new ServiceNotFoundException(externalId));
    }

    @Transactional
    @Override
    public void removeByExternalId(@NonNull String externalId) {
        try {
            serviceRepository.delete(this.findByExternalIdOrFail(externalId));
            serviceRepository.flush();
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage(), e);
            throw new ResourceInUseException(EXCEPTION_MESSAGE_SERVICE_IN_USE.formatted(externalId));
        }
    }

    @NonNull
    @Transactional(readOnly = true)
    public Page<Service> findAllByFilters(@NonNull ServiceFilter filter, @NonNull Pageable pageable) {
        return serviceRepository.findAllByFilter(filter, pageable);
    }
}
