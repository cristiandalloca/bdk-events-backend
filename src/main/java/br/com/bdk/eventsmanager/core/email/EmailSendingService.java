package br.com.bdk.eventsmanager.core.email;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import org.springframework.lang.NonNull;

import java.util.Map;
import java.util.Set;

public interface EmailSendingService {

    EmailImplementation getImplementation();

    void send(Message message);

    @Getter
    @Builder
    class Message {

        @Singular
        private Set<@Email String> recipients;

        @NonNull
        private String subject;

        @NonNull
        private String body;

        @Singular("params")
        private Map<String, Object> params;
    }
}
