package br.com.bdk.eventsmanager.admin.service.api.v1.disassembler;

import br.com.bdk.eventsmanager.admin.service.api.v1.model.input.ServicePhotoInput;
import br.com.bdk.eventsmanager.admin.service.domain.model.Service;
import br.com.bdk.eventsmanager.admin.service.domain.model.ServicePhoto;
import br.com.bdk.eventsmanager.core.api.disassembler.InputDisassembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ServicePhotoInputDisassembler implements InputDisassembler<ServicePhotoInput, ServicePhoto> {

    @NonNull
    @Override
    public ServicePhoto toEntity(@NonNull ServicePhotoInput input) {
        final var servicePhoto = new ServicePhoto();
        this.copyToEntity(input, servicePhoto);
        return servicePhoto;
    }

    @Override
    public void copyToEntity(@NonNull ServicePhotoInput input, @NonNull ServicePhoto servicePhoto) {
        servicePhoto.setNumber(input.getNumber());
        servicePhoto.setService(new Service(input.getServiceExternalId()));
    }
}
