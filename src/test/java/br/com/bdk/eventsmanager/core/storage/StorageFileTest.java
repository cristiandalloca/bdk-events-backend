package br.com.bdk.eventsmanager.core.storage;

import br.com.bdk.eventsmanager.common.EnvironmentMock;
import br.com.bdk.eventsmanager.core.ValidatorTest;
import com.google.common.io.Files;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StorageFileTest extends ValidatorTest {

    @Nested
    class WhenGetStoragePath {

        @Test
        void shouldReturnSuccess() {
            final var storageFile = StorageFile.builder()
                    .file("Any".getBytes())
                    .directory(StorageDirectory.COMPANY_SIGNATURE)
                    .fileNameWithExtension("logo.png")
                    .build();

            assertThat(storageFile.getStoragePath())
                    .containsIgnoringCase(Files.getFileExtension(storageFile.getFileNameWithExtension()));
        }
    }

    @Test
    void shouldValidateIfFileIsNull() {
        super.validateFieldIsNull(EnvironmentMock.mock(StorageFile.class), "file");
    }

    @Test
    void shouldValidateIfFileNameWithExtensionIsNull() {
        super.validateFieldIsNull(EnvironmentMock.mock(StorageFile.class), "fileNameWithExtension");
    }

    @Test
    void shouldValidateIfDirectoryIsNull() {
        super.validateFieldIsNull(EnvironmentMock.mock(StorageFile.class), "directory");
    }

}