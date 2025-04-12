package br.com.bdk.eventsmanager.core.storage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties(prefix = "bdk.storage")
public class StorageProperties {

    private static final long DEFAULT_EXPIRATION_TIME_IN_SECONDS_SIGNED_URL = 60L;

    @NotBlank
    private String bucketName;

    @Positive
    @NotNull
    private Long expirationTimeInSecondsSignedUrl = DEFAULT_EXPIRATION_TIME_IN_SECONDS_SIGNED_URL;

}
