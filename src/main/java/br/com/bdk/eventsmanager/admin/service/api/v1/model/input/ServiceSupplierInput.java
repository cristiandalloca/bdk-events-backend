package br.com.bdk.eventsmanager.admin.service.api.v1.model.input;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class ServiceSupplierInput {

    @NotEmpty
    @Schema(example = Example.IDENTIFIER_ARRAY, description = Description.SUPPLIER_IDENTIFIER)
    private List<@NotBlank String> supliersId;

}
