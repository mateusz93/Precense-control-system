package neo.dmcs.service;

import neo.dmcs.repository.AppPropertyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Mateusz Wieczorek on 10/18/16.
 */
@Service
public class ContactService {

    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private AppPropertyRepository repository;

    public void sendEmail(String from, String subject, String content) {
        String recipient = repository.findByName("email.from.adress").getValue();
        logger.info("Sending email to application..");
        logger.info("recipient: " + recipient);
        logger.info("subject: " + subject);
        logger.info("content: " + content);
        emailService.sendEmail(recipient, subject, content);

        String responseContent = repository.findByName("contact.email.content").getValue();
        String responseSubject = repository.findByName("contact.email.subject").getValue();
        logger.info("Sending response email..");
        logger.info("recipient: " + from);
        logger.info("subject: " + responseSubject);
        logger.info("content: " + responseContent);
        emailService.sendEmail(from, responseSubject, responseContent);
    }
}
