package br.com.bdk.eventsmanager.admin.supplier.domain.validator;

import br.com.bdk.eventsmanager.admin.supplier.domain.model.SupplierPlace;
import br.com.bdk.eventsmanager.admin.supplier.domain.model.exception.DuplicateNameSupplierPlaceException;
import br.com.bdk.eventsmanager.admin.supplier.domain.repository.SupplierPlaceRepository;
import br.com.bdk.eventsmanager.core.exception.BusinessException;
import br.com.bdk.eventsmanager.core.validator.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SupplierPlaceRegistrationValidator implements RegistrationValidator<SupplierPlace> {

    private final SupplierPlaceRepository repository;

    @Override
    public void validate(SupplierPlace supplierPlace) throws BusinessException {
        final var existingSupplierPlace
                = repository.findByNameIgnoreCaseAndSupplierExternalId(supplierPlace.getName(), supplierPlace.getSupplier().getExternalId());
        if (existingSupplierPlace.isPresent() && isNotSame(supplierPlace, existingSupplierPlace.get())) {
            throw new DuplicateNameSupplierPlaceException(supplierPlace.getName());
        }
    }
}
