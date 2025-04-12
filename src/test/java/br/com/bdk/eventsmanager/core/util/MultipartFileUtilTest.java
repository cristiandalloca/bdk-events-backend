package br.com.bdk.eventsmanager.core.util;

import br.com.bdk.eventsmanager.core.storage.FileDownloadException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

class MultipartFileUtilTest {

    @Test
    void shouldTransformMultiPartFileToByte() {
        byte[] contentFileInBytes = "Hello World".getBytes();
        var mockMultiPartFile
                = new MockMultipartFile("file", "test.txt", "text/plain", contentFileInBytes);
        var result = MultipartFileUtil.toByte(mockMultiPartFile);
        assertThat(result).isEqualTo(contentFileInBytes);
    }

    @Test
    @SneakyThrows
    void shouldThrowExceptionWhenMultiPartFileNotExists() {
        var mockMultiPartFile = Mockito.mock(MultipartFile.class);
        doThrow(IOException.class).when(mockMultiPartFile).getBytes();
        var exception = assertThrows(FileDownloadException.class, () -> MultipartFileUtil.toByte(mockMultiPartFile));
        assertThat(exception.getMessage()).isEqualTo("Falha ao realizar download do arquivo");
    }

}