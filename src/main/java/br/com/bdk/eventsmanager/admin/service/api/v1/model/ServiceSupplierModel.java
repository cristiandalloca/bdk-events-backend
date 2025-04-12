package br.com.bdk.eventsmanager.admin.service.api.v1.model;

import br.com.bdk.eventsmanager.admin.supplier.api.v1.model.SupplierModel;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ServiceSupplierModel {

    @Schema(example = Example.IDENTIFIER, description = Description.SERVICE_SUPPLIER_IDENTIFIER)
    private String id;

    private SupplierModel supplier;

}
