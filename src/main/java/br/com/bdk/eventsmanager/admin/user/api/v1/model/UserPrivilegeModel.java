package br.com.bdk.eventsmanager.admin.user.api.v1.model;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPrivilegeModel {

    @Schema(example =  Example.IDENTIFIER, description = Description.PRIVILEGE_IDENTIFIER)
    private String id;

    @Schema(example = Example.PRIVILEGE_NAME, description = Description.PRIVILEGE_NAME)
    private String name;

    @Schema(example = Example.PRIVILEGE_DESCRIPTION, description = Description.PRIVILEGE_DESCRIPTION)
    private String description;

}
