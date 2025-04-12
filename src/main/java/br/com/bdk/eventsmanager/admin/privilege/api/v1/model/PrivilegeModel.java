package br.com.bdk.eventsmanager.admin.privilege.api.v1.model;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PrivilegeModel {

    @EqualsAndHashCode.Include
    @Schema(example = Example.PRIVILEGE_NAME, description = Description.PRIVILEGE_NAME)
    private String name;

    @EqualsAndHashCode.Include
    @Schema(example = Example.PRIVILEGE_DESCRIPTION, description = Description.PRIVILEGE_DESCRIPTION)
    private String description;

    @Builder.Default
    private List<PrivilegeGrantModel> grants = new ArrayList<>();

    @Getter
    @Builder
    public static class PrivilegeGrantModel {

        @Schema(example = Example.IDENTIFIER, description = Description.PRIVILEGE_IDENTIFIER)
        private String id;

        @Schema(example = Example.PRIVILEGE_NAME, description = Description.PRIVILEGE_NAME)
        private String name;

        @Schema(example = Example.PRIVILEGE_DESCRIPTION, description = Description.PRIVILEGE_DESCRIPTION)
        private String description;

    }

    public void addGrant(PrivilegeGrantModel grant) {
        if (this.getGrants() == null) {
            this.grants = new ArrayList<>();
        }
        this.getGrants().add(grant);
    }

}


