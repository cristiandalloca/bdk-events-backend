package br.com.bdk.eventsmanager.admin.service.api.v1.disassembler;

import br.com.bdk.eventsmanager.admin.service.api.v1.model.input.ServiceSupplierInput;
import br.com.bdk.eventsmanager.admin.supplier.domain.model.Supplier;
import br.com.bdk.eventsmanager.core.api.disassembler.InputDisassembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceSupplierInputDisassembler implements InputDisassembler<ServiceSupplierInput, List<Supplier>> {

    @Override
    @NonNull
    public List<Supplier> toEntity(@NonNull ServiceSupplierInput input) {
        final var suppliers = new ArrayList<Supplier>();
        this.copyToEntity(input, suppliers);
        return suppliers;
    }

    @Override
    @NonNull
    public void copyToEntity(@NonNull ServiceSupplierInput input, @NonNull List<Supplier> suppliers) {
        input.getSupliersId().forEach(supplierId -> suppliers.add(new Supplier(supplierId)));
    }
}
