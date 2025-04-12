package br.com.bdk.eventsmanager.admin.company.parameter.api.v1.model;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CompanyLogoModel {

    @Schema(example = Example.ARCHIVE_URL, description = Description.COMPANY_LOGO)
    private String logoURL;

}
