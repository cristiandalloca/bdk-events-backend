package br.com.bdk.eventsmanager.core.storage;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class StorageFileService {

    private final Storage storage;
    private final StorageProperties properties;

    public String upload(@Valid StorageFile storageFile) throws FileUploadException {
        try {
            final var blobInfo = BlobInfo
                    .newBuilder(BlobId.of(properties.getBucketName(), storageFile.getStoragePath()))
                    .setContentType(Files.probeContentType(new File(storageFile.getFileNameWithExtension()).toPath()))
                    .build();

            return storage.create(blobInfo, storageFile.getFile()).getBlobId().toGsUtilUri();
        } catch (Exception e) {
            log.error("Falha ao realizar upload do arquivo '%s' no bucket".formatted(storageFile.getFileNameWithExtension()), e);
            throw new FileUploadException(e);
        }
    }

    public Blob get(@NonNull String googleStorageUri) throws FileDownloadException {
        try {
            final var file = storage.get(BlobId.fromGsUtilUri(googleStorageUri));
            if (file == null || !file.exists()) {
                throw new FileNotFoundException(googleStorageUri);
            }
            return file;
        } catch (Exception e) {
            log.error("Falha ao recuperar arquivo '%s' do bucket".formatted(googleStorageUri), e);
            throw new FileDownloadException(e);
        }
    }

    public void delete(@NonNull String googleStorageUri) throws FileDeleteException {
        try {
            if (!storage.delete(BlobId.fromGsUtilUri(googleStorageUri))) {
                log.warn("Aquivo %s não foi deletado pois não foi encontrado".formatted(googleStorageUri));
            }
        } catch (Exception e) {
            log.error("Falha ao deletar arquivo '%s' do bucket".formatted(googleStorageUri), e);
            throw new FileDeleteException(e);
        }
    }

    public URL getTemporaryUrl(@NonNull String googleStorageUri) throws FileTemporaryUrlException {
        try {
            final var blob = this.get(googleStorageUri);
            return blob.signUrl(properties.getExpirationTimeInSecondsSignedUrl(), TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Falha ao gerar url temporária para arquivo '%s' do bucket".formatted(googleStorageUri), e);
            throw new FileTemporaryUrlException(e);
        }
    }

}
