package br.com.bdk.eventsmanager.core.email;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("bdk.email")
public class EmailProperties {

    private EmailImplementation implementation = EmailImplementation.FAKE;

    @NotBlank
    private String sender;

}
