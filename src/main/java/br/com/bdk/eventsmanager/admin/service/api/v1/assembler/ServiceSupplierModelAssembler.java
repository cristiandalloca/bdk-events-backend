package br.com.bdk.eventsmanager.admin.service.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.service.api.v1.model.ServiceSupplierModel;
import br.com.bdk.eventsmanager.admin.service.domain.model.ServiceSupplier;
import br.com.bdk.eventsmanager.admin.supplier.api.v1.assembler.SupplierModelAssembler;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceSupplierModelAssembler implements ModelAssembler<ServiceSupplier, ServiceSupplierModel> {

    private final SupplierModelAssembler supplierModelAssembler;

    @NonNull
    @Override
    public ServiceSupplierModel toModel(@NonNull ServiceSupplier serviceSupplier) {
        return ServiceSupplierModel.builder()
                .id(serviceSupplier.getExternalId())
                .supplier(supplierModelAssembler.toModel(serviceSupplier.getSupplier()))
                .build();
    }
}
