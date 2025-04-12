package br.com.bdk.eventsmanager.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class EmailSendingFactory {

    private final EmailProperties emailProperties;
    private final Map<EmailImplementation, EmailSendingService> emailSendingServices;

    @Autowired
    public EmailSendingFactory(EmailProperties emailProperties, List<EmailSendingService> emailSendingServices) {
        this.emailProperties = emailProperties;
        this.emailSendingServices = emailSendingServices.stream()
                .collect(Collectors.toUnmodifiableMap(EmailSendingService::getImplementation, Function.identity()));
    }

    public EmailSendingService getService() {
        return Optional.ofNullable(emailSendingServices.get(emailProperties.getImplementation()))
                .orElseThrow(IllegalArgumentException::new);
    }
}
