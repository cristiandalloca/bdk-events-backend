package br.com.bdk.eventsmanager.core.validator;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class FileSizeValidatorTest {

    private static final String SIZE_IN_KB = "500KB";
    private static final DataSize DATA_SIZE_500KB = DataSize.parse(SIZE_IN_KB);
    private final FileSizeValidator fileSizeValidator = new FileSizeValidator();
    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(fileSizeValidator, "maxFileSize", DATA_SIZE_500KB);
        multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", RandomStringUtils.secure().nextAlphanumeric(512_000).getBytes());
    }

    @Test
    void shouldReturnTrueWhenFileSizeAccepted() {
        assertThat(fileSizeValidator.isValid(multipartFile, null)).isTrue();
    }

    @Test
    void shouldReturnTrueWhenFileIsNull() {
        assertThat(fileSizeValidator.isValid(null, null)).isTrue();
    }


    @Test
    void shouldReturnFalseWhenFileSizeMax() {
        multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", RandomStringUtils.secure().nextAlphanumeric(512_001).getBytes());
        assertThat(fileSizeValidator.isValid(multipartFile, null)).isFalse();
    }

    @Test
    void shouldInitializeValidator() {
        var mockFileSize = Mockito.mock(FileSize.class);
        when(mockFileSize.max()).thenReturn(SIZE_IN_KB);
        fileSizeValidator.initialize(mockFileSize);
        var maxFileSize = (DataSize) ReflectionTestUtils.getField(fileSizeValidator, "maxFileSize");
        assertThat(maxFileSize).isEqualTo(DATA_SIZE_500KB);
    }
}