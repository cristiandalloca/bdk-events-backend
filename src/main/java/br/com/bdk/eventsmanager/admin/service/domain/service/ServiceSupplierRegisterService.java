package br.com.bdk.eventsmanager.admin.service.domain.service;

import br.com.bdk.eventsmanager.admin.service.domain.model.ServiceSupplier;
import br.com.bdk.eventsmanager.admin.service.domain.model.exception.ServiceSupplierNotFoundException;
import br.com.bdk.eventsmanager.admin.service.domain.repository.ServiceSupplierRepository;
import br.com.bdk.eventsmanager.admin.supplier.domain.model.Supplier;
import br.com.bdk.eventsmanager.admin.supplier.domain.service.SupplierRegistrationService;
import br.com.bdk.eventsmanager.core.exception.ResourceInUseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceSupplierRegisterService {

    private static final String EXCEPTION_SERVICE_SUPPLIER_IN_USE
            = "Fornecedor do serviço de código '%s' não pode ser removido, pois está em uso";
    private final ServiceSupplierRepository serviceSupplierRepository;
    private final SupplierRegistrationService supplierRegisterService;
    private final ServiceRegistrationService serviceRegisterService;

    @Transactional
    public void addSupliersToService(String serviceExternalId, List<Supplier> suppliers) {
        final var service = serviceRegisterService.findByExternalId(serviceExternalId);
        final var serviceSuppliers = serviceSupplierRepository.findByServiceExternalId(service.getExternalId());
        suppliers.forEach(supplier -> {
            final var serviceSupplier = new ServiceSupplier();
            serviceSupplier.setService(service);
            serviceSupplier.setSupplier(supplierRegisterService.findByExternalId(supplier.getExternalId()));
            serviceSuppliers.add(serviceSupplier);
        });

        serviceSupplierRepository.saveAll(serviceSuppliers);
    }

    @Transactional(readOnly = true)
    public Set<ServiceSupplier> findAllSuppliersByServiceExternalId(@NonNull String serviceExternalId) {
        final var service = serviceRegisterService.findByExternalId(serviceExternalId);
        return serviceSupplierRepository.findByServiceExternalId(service.getExternalId());
    }

    private ServiceSupplier findByExternalIdOrFail(@NonNull String externalId) {
        return serviceSupplierRepository.findByExternalId(externalId)
                .orElseThrow(() -> new ServiceSupplierNotFoundException(externalId));
    }

    @Transactional
    public void removeServiceSupplierByExternalId(@NonNull String externalId) {
        try {
            serviceSupplierRepository.delete(this.findByExternalIdOrFail(externalId));
            serviceSupplierRepository.flush();
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage(), e);
            throw new ResourceInUseException(EXCEPTION_SERVICE_SUPPLIER_IN_USE.formatted(externalId));
        }
    }
}
