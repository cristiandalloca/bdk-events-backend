package br.com.bdk.eventsmanager.admin.service.domain.service;

import br.com.bdk.eventsmanager.admin.service.domain.model.ServicePhoto;
import br.com.bdk.eventsmanager.admin.service.domain.model.exception.ServicePhotoNotFoundException;
import br.com.bdk.eventsmanager.admin.service.domain.repository.ServicePhotoRepository;
import br.com.bdk.eventsmanager.core.exception.ResourceInUseException;
import br.com.bdk.eventsmanager.core.storage.StorageDirectory;
import br.com.bdk.eventsmanager.core.storage.StorageFile;
import br.com.bdk.eventsmanager.core.storage.StorageFileService;
import br.com.bdk.eventsmanager.core.util.MultipartFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServicePhotoRegisterService {

    private final ServiceRegistrationService serviceRegisterService;
    private final ServicePhotoRepository repository;
    private final StorageFileService storageFileService;
    private static final String EXCEPTION_SERVICE_PHOTO_IN_USE
            = "Foto do serviço de código '%s' não pode ser removida, pois está em uso";

    @NonNull
    @Transactional(readOnly = true)
    public List<ServicePhoto> findAllByServiceExternalId(@NonNull String serviceExternalId) {
        return repository.findAllByServiceExternalId(serviceExternalId);
    }

    @Nullable
    @Transactional(readOnly = true)
    public ServicePhoto findByServiceExternalIdAndNumber(@NonNull String serviceExternalId, @NonNull Integer number) {
        return repository.findByServiceExternalIdAndNumber(serviceExternalId, number).orElse(null);
    }

    @NonNull
    @Transactional(readOnly = true)
    public ServicePhoto findByServiceExternalIdAndExternalId(@NonNull String serviceExternalId, @NonNull String externalId) {
        return this.findByServiceExternalIdAndExternalIdOrFail(serviceExternalId, externalId);
    }

    @NonNull
    @Transactional
    public ServicePhoto saveAndValidate(@NonNull ServicePhoto servicePhoto, @NonNull MultipartFile photo) {
        servicePhoto.setService(serviceRegisterService.findByExternalId(servicePhoto.getService().getExternalId()));
        servicePhoto.setUri(this.upload(StorageDirectory.SERVICE_PHOTO, photo));
        return repository.save(servicePhoto);
    }

    @Transactional
    public void removeByServiceExternalIdAndExternalId(@NonNull String serviceExternalId, @NonNull String externalId) {
        try {
            repository.delete(this.findByServiceExternalIdAndExternalIdOrFail(serviceExternalId, externalId));
            repository.flush();
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage(), e);
            throw new ResourceInUseException(EXCEPTION_SERVICE_PHOTO_IN_USE.formatted(externalId));

        }
    }

    private ServicePhoto findByServiceExternalIdAndExternalIdOrFail(@NonNull String serviceExternalId, @NonNull String externalId) {
        return repository.findByServiceExternalIdAndExternalId(serviceExternalId, externalId).orElseThrow(() -> new ServicePhotoNotFoundException(externalId));
    }

    private String upload(StorageDirectory directory, MultipartFile file) {
        return storageFileService.upload(StorageFile.builder()
                .directory(directory)
                .fileNameWithExtension(file.getOriginalFilename())
                .file(MultipartFileUtil.toByte(file))
                .build());
    }
}
