package br.com.bdk.eventsmanager.core.storage;

import com.google.common.io.Files;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class StorageFile {

    @NotNull
    private byte[] file;

    @NotNull
    private String fileNameWithExtension;

    @NotNull
    private StorageDirectory directory;

    public String getStoragePath() {
        return "%s/%s".formatted(directory.getDirectory(), this.generateUniqueFileName());
    }

    private String generateUniqueFileName() {
        return UUID.randomUUID().toString().concat(".").concat(this.getFileExtension());
    }

    private String getFileExtension() {
        return Files.getFileExtension(this.getFileNameWithExtension());
    }
}
