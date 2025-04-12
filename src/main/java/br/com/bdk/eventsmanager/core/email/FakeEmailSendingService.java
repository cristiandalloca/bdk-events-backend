package br.com.bdk.eventsmanager.core.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FakeEmailSendingService implements EmailSendingService {

    @Override
    public EmailImplementation getImplementation() {
        return EmailImplementation.FAKE;
    }

    @Override
    public void send(Message message) {
        String body = message.getBody();
        log.info("[FAKE E-MAIL] Para {}\n{}", message.getRecipients(), body);
    }
}
