package br.com.bdk.eventsmanager.admin.profile.api.v1.model;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class ProfileCompleteModel {

    @Schema(example = Example.IDENTIFIER, description = Description.PROFILE_IDENTIFIER)
    private String id;

    @Schema(example = Example.PROFILE_NAME, description = Description.PROFILE_NAME)
    private String name;

    private ProfileCompanyModel company;

    @Builder.Default
    private List<ProfilePrivilegeModel> privileges = new ArrayList<>();

    @Getter
    @Builder
    public static class ProfilePrivilegeModel {

        @Schema(example = Example.IDENTIFIER, description = Description.PRIVILEGE_IDENTIFIER)
        private String id;

        @Schema(example = Example.PRIVILEGE_NAME, description = Description.PRIVILEGE_NAME)
        private String name;

        @Schema(example = Example.PRIVILEGE_DESCRIPTION, description = Description.PRIVILEGE_DESCRIPTION)
        private String description;

    }
}
