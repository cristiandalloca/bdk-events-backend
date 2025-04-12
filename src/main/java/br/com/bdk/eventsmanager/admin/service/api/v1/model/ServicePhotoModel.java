package br.com.bdk.eventsmanager.admin.service.api.v1.model;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ServicePhotoModel {

    @Schema(example = Example.IDENTIFIER, description = Description.SERVICE_PHOTO_IDENTIFIER)
    private String id;

    @Schema(example = Example.INTEGER, description = Description.SERVICE_PHOTO_NUMBER)
    private Integer number;

    @Schema(example = Example.ARCHIVE_URL, description = Description.SERVICE_PHOTO_URL)
    private String url;

}
