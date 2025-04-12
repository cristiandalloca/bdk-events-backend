package br.com.bdk.eventsmanager.admin.supplier.domain.service;

import br.com.bdk.eventsmanager.admin.supplier.domain.model.Supplier;
import br.com.bdk.eventsmanager.admin.supplier.domain.model.SupplierPlace;
import br.com.bdk.eventsmanager.admin.supplier.domain.model.exception.SupplierPlaceNotFoundException;
import br.com.bdk.eventsmanager.admin.supplier.domain.repository.SupplierPlaceRepository;
import br.com.bdk.eventsmanager.admin.supplier.domain.validator.SupplierPlaceRegistrationValidator;
import br.com.bdk.eventsmanager.core.exception.ResourceInUseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.trimToNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupplierPlaceRegisterService {

    private static final String EXCEPTION_MESSAGE_SUPPLIER_PLACE_IN_USE =
            "Local do fornecedor de código '%s' não pode ser removido, pois está em uso";
    private final SupplierRegistrationService supplierRegisterService;
    private final SupplierPlaceRepository repository;
    private final SupplierPlaceRegistrationValidator validator;

    @NonNull
    @Transactional
    public SupplierPlace saveAndValidate(@NonNull String supplierExternalId, @NonNull SupplierPlace supplierPlace) {
        supplierPlace.setSupplier(new Supplier(supplierExternalId));
        validator.validate(supplierPlace);
        supplierPlace.setSupplier(supplierRegisterService.findByExternalId(supplierExternalId));
        return repository.save(supplierPlace);
    }

    @NonNull
    @Transactional(readOnly = true)
    public SupplierPlace findByExternalId(@NonNull String supplierExternalId, @NonNull String externalId) {
        return this.findByExternalIdOrFail(supplierExternalId, externalId);
    }

    private SupplierPlace findByExternalIdOrFail(@NonNull String supplierExternalId, @NonNull String externalId) {
        return repository.findByExternalIdAndSupplierExternalId(externalId, supplierExternalId)
                .orElseThrow(() -> new SupplierPlaceNotFoundException(externalId));
    }

    @Transactional
    public void removeByExternalId(@NonNull String supplierExternalId, @NonNull String externalId) {
        try {
            repository.delete(this.findByExternalIdOrFail(supplierExternalId, externalId));
            repository.flush();
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage(), e);
            throw new ResourceInUseException(EXCEPTION_MESSAGE_SUPPLIER_PLACE_IN_USE.formatted(externalId));
        }
    }

    @Transactional(readOnly = true)
    public List<SupplierPlace> findAll(@NonNull String supplierExternalId, @Nullable String name, @Nullable Integer peopleCapacity, @Nullable Boolean active) {
        return repository.findBySupplierExternalId(supplierExternalId, trimToNull(name), peopleCapacity, active);
    }
}
