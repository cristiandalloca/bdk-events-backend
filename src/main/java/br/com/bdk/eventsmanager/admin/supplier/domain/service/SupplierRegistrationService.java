package br.com.bdk.eventsmanager.admin.supplier.domain.service;

import br.com.bdk.eventsmanager.admin.city.domain.service.CityRegistrationService;
import br.com.bdk.eventsmanager.admin.company.domain.service.CompanyRegistrationService;
import br.com.bdk.eventsmanager.admin.supplier.domain.model.Supplier;
import br.com.bdk.eventsmanager.admin.supplier.domain.model.exception.SupplierNotFoundException;
import br.com.bdk.eventsmanager.admin.supplier.domain.model.filter.SupplierFilter;
import br.com.bdk.eventsmanager.admin.supplier.domain.repository.SupplierRepository;
import br.com.bdk.eventsmanager.admin.supplier.domain.validator.SupplierRegistrationValidator;
import br.com.bdk.eventsmanager.core.exception.ResourceInUseException;
import br.com.bdk.eventsmanager.core.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupplierRegistrationService implements RegistrationService<Supplier> {

    private static final String EXCEPTION_MESSAGE_SUPPLIER_IN_USE
            = "Fornecedor de código '%s' não pode ser removido, pois está em uso";
    private final SupplierRepository repository;
    private final SupplierRegistrationValidator validator;
    private final CompanyRegistrationService companyRegistrationService;
    private final CityRegistrationService cityRegisterService;

    @NonNull
    @Override
    @Transactional
    public Supplier validateAndSave(@NonNull Supplier supplier) {
        validator.validate(supplier);
        supplier.setCompany(companyRegistrationService.findByExternalId(supplier.getCompany().getExternalId()));
        supplier.getAddress().setCity(cityRegisterService.findByExternalId(supplier.getAddress().getCity().getExternalId()));
        return repository.save(supplier);
    }

    @NonNull
    @Override
    @Transactional(readOnly = true)
    public Supplier findByExternalId(@NonNull String externalId) {
        return this.findByExternalIdOrFail(externalId);
    }

    private Supplier findByExternalIdOrFail(@NonNull String externalId) {
        return repository.findByExternalId(externalId)
                .orElseThrow(() -> new SupplierNotFoundException(externalId));
    }

    @Override
    @Transactional
    public void removeByExternalId(@NonNull String externalId) {
        try {
            repository.delete(this.findByExternalIdOrFail(externalId));
            repository.flush();
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage(), e);
            throw new ResourceInUseException(EXCEPTION_MESSAGE_SUPPLIER_IN_USE.formatted(externalId));
        }
    }

    @NonNull
    @Transactional(readOnly = true)
    public Page<Supplier> findAll(@Valid SupplierFilter filter, @NonNull Pageable pageable) {
        return repository.findAllByFilter(filter, pageable);
    }
}
