package br.com.bdk.eventsmanager.core.storage;

import br.com.bdk.eventsmanager.common.EnvironmentMock;
import br.com.bdk.eventsmanager.core.ValidatorTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class StoragePropertiesTest extends ValidatorTest {

    private final StorageProperties storageProperties = EnvironmentMock.mock(StorageProperties.class);

    @Test
    void shouldValidateIfBucketNameIsNull() {
        validateFieldIsNull(storageProperties, "bucketName");
    }

    @Test
    void shouldValidateIfExpirationTimeInSecondsSignedUrlIsNull() {
        validateFieldIsNull(storageProperties, "expirationTimeInSecondsSignedUrl");
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "", "  "})
    void shouldValidateIfBucketNameIsBlank(final String bucketNameBlank) {
        validateField(storageProperties, "bucketName", bucketNameBlank);
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L, -2L})
    void shouldValidateIfExpirationTimeInSecondsSignedUrlIsNegativeOrZero(final long expirationTimeInSecondsNegative) {
        validateField(storageProperties, "expirationTimeInSecondsSignedUrl", expirationTimeInSecondsNegative);
    }
}