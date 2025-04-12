package br.com.bdk.eventsmanager.auth.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "bdk.auth.jwt")
public class AuthJwtProperties {

    @NotBlank
    private String signingKey;

    @NotNull
    @Positive
    private Long expirationInSeconds;

}
