package br.com.bdk.eventsmanager.core.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmtpEmailSendingService implements EmailSendingService {

    private final JavaMailSender javaMailSender;
    private final EmailProperties emailProperties;

    @Override
    public EmailImplementation getImplementation() {
        return EmailImplementation.SMTP;
    }

    @Override
    @Async
    public void send(Message message) {
        try {
            var mimeMessage = this.createMimeMessage(message);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Não foi possível realizar o envio de e-mail", e);
        }
    }

    protected MimeMessage createMimeMessage(Message message) throws MessagingException {
        var mimeMessage = javaMailSender.createMimeMessage();

        var helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setFrom(emailProperties.getSender());
        helper.setTo(message.getRecipients().toArray(new String[0]));
        helper.setSubject(message.getSubject());
        helper.setText(message.getBody(), true);

        return mimeMessage;
    }
}
