package br.com.bdk.eventsmanager.admin.service.api.v1.controller.openapi;

import br.com.bdk.eventsmanager.admin.service.api.v1.model.ServicePhotoModel;
import br.com.bdk.eventsmanager.admin.service.api.v1.model.input.ServicePhotoInput;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;

import java.util.Collection;

@Tag(name = "Services")
@SecurityRequirement(name = "bearerAuth")
public interface ServicePhotoRegisterControllerOpenApi {

    @Operation(summary = "Find all photos by service id")
    Collection<ServicePhotoModel> findAllPhotosByServiceExternalId(@Parameter(description = Description.SERVICE_IDENTIFIER, example = Example.IDENTIFIER) String serviceExternalId);

    @Operation(summary = "Insert a service photo", requestBody = @RequestBody(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)))
    ServicePhotoModel insertOrUpdatePhotoByServiceExternalId(@Parameter(description = Description.SERVICE_IDENTIFIER, example = Example.IDENTIFIER) String serviceExternalId,
                                                             ServicePhotoInput input);

    @Operation(summary = "Find a photo by id")
    ServicePhotoModel findById(@Parameter(description = Description.SERVICE_IDENTIFIER) String serviceExternalId,
                               @Parameter(description = Description.SERVICE_PHOTO_IDENTIFIER) String servicePhotoExternalId);

    @Operation(summary = "Remove a service photo")
    void removePhotoByServiceExternalId(@Parameter(description = Description.SERVICE_IDENTIFIER, example = Example.IDENTIFIER) String serviceExternalId,
                                        @Parameter(description = Description.SERVICE_PHOTO_IDENTIFIER, example = Example.IDENTIFIER) String servicePhotoExternalId);
}
