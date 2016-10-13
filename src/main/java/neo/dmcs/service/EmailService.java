package neo.dmcs.service;

import neo.dmcs.repository.AppPropertyRepository;
import neo.dmcs.repository.EmailTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @Author by Mateusz Wieczorek on 9/27/16.
 */
@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Autowired
    private AppPropertyRepository appPropertyRepository;

    public void sendEmail(String recipient, String subject, String content) {

        String from = appPropertyRepository.findByName("email.from.adress").getValue();
        String username = appPropertyRepository.findByName("email.from.username").getValue();
        String password = appPropertyRepository.findByName("email.from.password").getValue();
        String port = appPropertyRepository.findByName("email.port").getValue();
        String host = appPropertyRepository.findByName("email.host").getValue();
        String auth = appPropertyRepository.findByName("email.smtp.auth").getValue();
        String isStarttls = appPropertyRepository.findByName("email.smtp.starttls.enable").getValue();

        Properties props = getProperties(host, port, auth, isStarttls);
        Session session = getSession(username, password, props);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(content);
            Transport.send(message);
            logger.info("Sent message successfully to " + recipient);
        } catch (MessagingException e) {
            logger.error("Message not sent");
            throw new RuntimeException(e);
        }
    }

    private Properties getProperties(String host, String port, String auth, String isStarttls) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", isStarttls);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        return props;
    }

    private Session getSession(String username, String password, Properties props) {
        return Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

}
