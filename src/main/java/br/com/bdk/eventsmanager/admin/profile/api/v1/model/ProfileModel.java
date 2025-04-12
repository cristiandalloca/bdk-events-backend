package br.com.bdk.eventsmanager.admin.profile.api.v1.model;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileModel {

    @Schema(example = Example.IDENTIFIER, description = Description.PROFILE_IDENTIFIER)
    private String id;

    @Schema(example = Example.PROFILE_NAME, description = Description.PROFILE_NAME)
    private String name;

    private ProfileCompanyModel company;
}
