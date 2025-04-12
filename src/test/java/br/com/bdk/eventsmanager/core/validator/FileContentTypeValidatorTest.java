package br.com.bdk.eventsmanager.core.validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class FileContentTypeValidatorTest {

    @Test
    void shouldReturnTrueWhenFileIsNull() {
        var fileContentTypeValidator = new FileContentTypeValidator();
        Assertions.assertThat(fileContentTypeValidator.isValid(null, null)).isTrue();
    }

    @Test
    void shouldReturnTrueWhenFileTypeIsAllowed() {
        var annotation = Mockito.mock(FileContentType.class);
        when(annotation.allowed()).thenReturn(new String[]{MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE});
        var fileContentTypeValidator = new FileContentTypeValidator();
        var multipartFile = new MockMultipartFile("teste", "teste.pdf", MediaType.IMAGE_PNG_VALUE, (byte[]) null);
        assertDoesNotThrow(() -> fileContentTypeValidator.initialize(annotation));
        assertTrue(fileContentTypeValidator.isValid(multipartFile, null));
    }

    @Test
    void shouldReturnFalseWhenFileTypeIsNotAllowed() {
        var annotation = Mockito.mock(FileContentType.class);
        when(annotation.allowed()).thenReturn(new String[]{MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE});
        var fileContentTypeValidator = new FileContentTypeValidator();
        var multipartFile = new MockMultipartFile("teste", "teste.pdf", MediaType.APPLICATION_JSON_VALUE, (byte[]) null);
        assertDoesNotThrow(() -> fileContentTypeValidator.initialize(annotation));
        assertFalse(fileContentTypeValidator.isValid(multipartFile, null));
    }

    @Test
    void shouldReturnFalseWhenParamAllowedTypeIsEmpty() {
        var annotation = Mockito.mock(FileContentType.class);
        when(annotation.allowed()).thenReturn(new String[]{});
        var fileContentTypeValidator = new FileContentTypeValidator();
        var multipartFile = new MockMultipartFile("teste", "teste.pdf", MediaType.APPLICATION_JSON_VALUE, (byte[]) null);
        assertDoesNotThrow(() -> fileContentTypeValidator.initialize(annotation));
        assertFalse(fileContentTypeValidator.isValid(multipartFile, null));
    }

    @Test
    void shouldReturnFalseWhenParamAllowedTypeIsNull() {
        var annotation = Mockito.mock(FileContentType.class);
        when(annotation.allowed()).thenReturn(null);
        var fileContentTypeValidator = new FileContentTypeValidator();
        var multipartFile = new MockMultipartFile("teste", "teste.pdf", MediaType.APPLICATION_JSON_VALUE, (byte[]) null);
        assertDoesNotThrow(() -> fileContentTypeValidator.initialize(annotation));
        assertFalse(fileContentTypeValidator.isValid(multipartFile, null));
    }
}