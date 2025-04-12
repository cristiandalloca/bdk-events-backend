package br.com.bdk.eventsmanager.core.storage;

import br.com.bdk.eventsmanager.common.EnvironmentMock;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StorageFileServiceTest {

    @InjectMocks
    private StorageFileService storageFileService;

    @Mock
    private Storage storage;

    @Mock
    private StorageProperties properties;

    @Nested
    class WhenUploadFile {

        @Captor
        private ArgumentCaptor<BlobInfo> blobInfoCaptor;

        @Test
        @SneakyThrows
        void shouldOk() {
            final var bucketName = "ABC";
            when(properties.getBucketName()).thenReturn(bucketName);

            final var storageFile = EnvironmentMock.mock(StorageFile.class);
            final var blob = Mockito.mock(Blob.class);
            when(storage.create(blobInfoCaptor.capture(), eq(storageFile.getFile())))
                    .thenReturn(blob);

            final var blobId = Mockito.mock(BlobId.class);
            when(blob.getBlobId()).thenReturn(blobId);

            final var uri = "s/foo/bar";
            when(blobId.toGsUtilUri()).thenReturn(uri);

            assertDoesNotThrow(() -> storageFileService.upload(storageFile));

            final var blobInfo = blobInfoCaptor.getValue();
            assertEquals(bucketName, blobInfo.getBucket());
            assertEquals(Files.probeContentType(new File(storageFile.getFileNameWithExtension()).toPath()), blobInfo.getContentType());
        }

        @Test
        void shouldThrowFileUploadException() {
            final var bucketName = "ABC";
            when(properties.getBucketName()).thenReturn(bucketName);

            doThrow(StorageException.class)
                    .when(storage).create(any(), any(byte[].class));

            final var storageFile = EnvironmentMock.mock(StorageFile.class);
            final var exception = assertThrowsExactly(FileUploadException.class, () -> storageFileService.upload(storageFile));
            assertEquals("Falha ao realizar upload do arquivo", exception.getMessage());
        }
    }

    @Nested
    class WhenGetFile {

        @Test
        void shouldReturnFile() {
            final var googleStorageUri = "gs://bucket/blob.txt";
            final var blob = Mockito.mock(Blob.class);
            when(storage.get(any(BlobId.class))).thenReturn(blob);
            when(blob.exists()).thenReturn(true);

            final var file = storageFileService.get(googleStorageUri);
            assertEquals(blob, file);
        }

        @Test
        void shouldThrowExceptionFileNotFound() {
            final var googleStorageUri = "gs://bucket/blob.txt";
            final var blob = Mockito.mock(Blob.class);
            when(storage.get(any(BlobId.class))).thenReturn(blob);
            when(blob.exists()).thenReturn(Boolean.FALSE);

            assertThatThrownBy(() -> storageFileService.get(googleStorageUri))
                    .isInstanceOf(FileDownloadException.class)
                    .hasMessage("Falha ao realizar download do arquivo");
        }

        @Test
        void shouldThrowExceptionFileNotFoundWhenReturnIsNull() {
            final var googleStorageUri = "gs://bucket/blob.txt";
            when(storage.get(any(BlobId.class))).thenReturn(null);
            assertThatThrownBy(() -> storageFileService.get(googleStorageUri))
                    .isInstanceOf(FileDownloadException.class)
                    .hasMessage("Falha ao realizar download do arquivo");
        }
    }

    @Nested
    class WhenDeleteFile {

        @Test
        void shouldDeleteFile() {
            final var googleStorageUri = "gs://bucket/blob.txt";
            when(storage.delete(any(BlobId.class))).thenReturn(Boolean.TRUE);
            assertDoesNotThrow(() -> storageFileService.delete(googleStorageUri));
        }

        @Test
        void shouldDoesNotThrowExceptionWhenNotExistsFile() {
            final var googleStorageUri = "gs://bucket/blob.txt";
            when(storage.delete(any(BlobId.class))).thenReturn(Boolean.FALSE);
            assertDoesNotThrow(() -> storageFileService.delete(googleStorageUri));
        }

        @Test
        void shouldThrowFileDeleteException() {
            final var googleStorageUri = "gs://bucket/blob.txt";
            doThrow(RuntimeException.class).when(storage).delete(any(BlobId.class));
            assertThatThrownBy(() -> storageFileService.delete(googleStorageUri))
                    .isInstanceOf(FileDeleteException.class)
                    .hasMessage("Falha ao remover arquivo");
        }
    }

    @Nested
    class WhenGetTemporaryUrl {

        @Test
        void shouldGetTemporaryUrl() {
            final var blob = Mockito.mock(Blob.class);
            when(storage.get(any(BlobId.class))).thenReturn(blob);
            when(blob.exists()).thenReturn(Boolean.TRUE);

            final var url = EnvironmentMock.mock(URL.class);
            when(properties.getExpirationTimeInSecondsSignedUrl()).thenReturn(200L);
            when(blob.signUrl(200L, TimeUnit.SECONDS))
                    .thenReturn(url);

            final var googleStorageUri = "gs://bucket/blob.txt";
            final var signedUrl = storageFileService.getTemporaryUrl(googleStorageUri);
            assertThat(signedUrl).isEqualTo(url);
        }

        @Test
        void shouldThrowFileTemporaryUrlException() {
            final var blob = Mockito.mock(Blob.class);
            when(storage.get(any(BlobId.class))).thenReturn(blob);
            when(blob.exists()).thenReturn(Boolean.FALSE);

            final var googleStorageUri = "gs://bucket/blob.txt";
            assertThatThrownBy(() -> storageFileService.getTemporaryUrl(googleStorageUri))
                    .isInstanceOf(FileTemporaryUrlException.class)
                    .hasMessage("Falha ao gerar url temporária para arquivo");
        }
    }
}