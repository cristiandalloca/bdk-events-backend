package br.com.bdk.eventsmanager.admin.supplier.domain.validator;

import br.com.bdk.eventsmanager.admin.supplier.domain.model.Supplier;
import br.com.bdk.eventsmanager.admin.supplier.domain.model.exception.DuplicateNameSupplierException;
import br.com.bdk.eventsmanager.admin.supplier.domain.repository.SupplierRepository;
import br.com.bdk.eventsmanager.core.exception.BusinessException;
import br.com.bdk.eventsmanager.core.validator.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SupplierRegistrationValidator implements RegistrationValidator<Supplier> {

    private final SupplierRepository supplierRepository;

    @Override
    @Transactional(readOnly = true)
    public void validate(@NonNull Supplier supplier) throws BusinessException {
        final var existingSupplier = supplierRepository.findByNameIgnoreCaseAndCompanyExternalId(supplier.getName(), supplier.getCompany().getExternalId());
        if (existingSupplier.isPresent() && this.isNotSame(supplier, existingSupplier.get())) {
            throw new DuplicateNameSupplierException(supplier.getName());
        }
    }
}
