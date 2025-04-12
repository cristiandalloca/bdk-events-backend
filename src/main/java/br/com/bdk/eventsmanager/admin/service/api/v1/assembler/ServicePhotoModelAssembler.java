package br.com.bdk.eventsmanager.admin.service.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.service.api.v1.model.ServicePhotoModel;
import br.com.bdk.eventsmanager.admin.service.domain.model.ServicePhoto;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import br.com.bdk.eventsmanager.core.storage.StorageFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServicePhotoModelAssembler implements ModelAssembler<ServicePhoto, ServicePhotoModel> {

    private final StorageFileService storageFileService;

    @NonNull
    @Override
    public ServicePhotoModel toModel(@NonNull ServicePhoto servicePhoto) {
        final var signedUrl = storageFileService.getTemporaryUrl(servicePhoto.getUri());
        return ServicePhotoModel.builder()
                .id(servicePhoto.getExternalId())
                .url(signedUrl.toString())
                .number(servicePhoto.getNumber())
                .build();
    }
}
