package neo.dmcs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.repository.AppPropertyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author Mateusz Wieczorek on 10/18/16.
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ContactService {

    private final EmailService emailService;
    private final AppPropertyRepository repository;

    public void sendEmail(String from, String subject, String content) {
        String recipient = repository.findByName("email.from.adress").getValue();
        log.info("Sending email to application..");
        log.info("recipient: " + recipient);
        log.info("subject: " + subject);
        log.info("content: " + content);
        emailService.sendEmail(recipient, subject, content);

        String responseContent = repository.findByName("contact.email.content").getValue();
        String responseSubject = repository.findByName("contact.email.subject").getValue();
        log.info("Sending response email..");
        log.info("recipient: " + from);
        log.info("subject: " + responseSubject);
        log.info("content: " + responseContent);
        emailService.sendEmail(from, responseSubject, responseContent);
    }
}
